package com.ruoyi.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.SystemUtil;
import com.ruoyi.common.constant.MsgConstants;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.common.push.PushService;
import com.ruoyi.common.utils.JWTUtil;
import com.ruoyi.common.utils.OrderNumUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.redis.RedisService;
import com.ruoyi.controller.BaseController;
import com.ruoyi.common.Constants;
import com.ruoyi.common.Result;
import com.ruoyi.user.domain.RcAttention;
import com.ruoyi.user.domain.RcUser;
import com.ruoyi.user.mapper.RcUserMapper;
import com.ruoyi.user.service.IRcAttentionService;
import com.ruoyi.user.service.IRcUserService;
import com.ruoyi.user.service.IUserMoneyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * @author xiaoxia
 */
@Api("登陆注册修改密码接口")
@RestController
@RequestMapping("/rc-api/user")
public class UserApi extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(UserApi.class);

    @Autowired
    IRcUserService iRcUserService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private IRcUserService rcUserService;

    @Resource
    private SystemUtil systemUtil;

    @Autowired
    private PushService pushService;

    @Autowired
    private IUserMoneyService userMoneyService;

    @Autowired
    private RcUserMapper rcUserMapper;

    @Autowired
    private IRcAttentionService rcAttentionService;

    @Lazy
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 注册账号
     *
     * @param account          用户名
     * @param passWord         密码
     * @param mobile           手机号
     * @param referralcode     注册推荐码
     * @param verificationCode 验证码
     * @param language         选择的语言/区分区域
     * @param request
     * @return
     * @throws ParseException
     */
    @ApiOperation("注册接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "String", name = "account", value = "用户名", required = true),
                    @ApiImplicitParam(dataType = "String", name = "password", value = "密码", required = true),
                    @ApiImplicitParam(dataType = "String", name = "mobile", value = "手机号", required = true),
                    @ApiImplicitParam(dataType = "String", name = "referralcode", value = "邀请码(非必填)", required = false),
                    @ApiImplicitParam(dataType = "String", name = "verificationCode", value = "验证码(该参数暂时没用 暂时先不传)", required = false),
                    @ApiImplicitParam(dataType = "String", name = "language", value = "地区(该参数暂时没用 暂时先不传)", required = false)
            })
    @PostMapping("/register")
    public Result register(@RequestParam("account") String account,
                           @RequestParam("password") String passWord,
                           @RequestParam("mobile") String mobile,
                           @RequestParam(value = "referralcode", required = false) String referralcode,
                           @RequestParam(value = "verificationCode", required = false) String verificationCode,
                           @RequestParam(value = "language", required = false) String language,
                           HttpServletRequest request) throws ParseException {
        //检测用户名是否重复
        RcUser user = rcUserMapper.selectaccount(account);
        if (user != null) {
            return Result.isFail().msg("用户名重复");
        }
        //产生邀请码
        String showId = OrderNumUtil.getRandomNum(8);
        //检测产生的邀请码是否被使用了
        JSONObject selectinvitation = rcUserService.selectinvitation(showId);
        if (selectinvitation != null) {
            return Result.isFail().msg("请重试");
        }
        //推荐码非空时检测推荐码是否有效
        int parentid = 0;
        if (!StringUtils.isEmpty(referralcode)) {
            JSONObject selectreferralcode = rcUserService.selectreferralcode(referralcode);
            if (selectreferralcode == null) {
                return Result.isFail().msg("推荐码无效");
            }
            parentid = (int) selectreferralcode.get("id");
        }
        //检测手机号是否存在正在使用
//        QueryWrapper queryWrapper1=new QueryWrapper();
//        queryWrapper1.eq("mobile",mobile);
        JSONObject selectmobile = rcUserService.selectmobile(mobile);
        if (selectmobile != null) {
            return Result.isFail().msg(MsgConstants.USER_ALLREADY_EXITS);
        }
        //加密
        String password = bCryptPasswordEncoder.encode(showId + passWord);
//        Map<String, Object> extractPublicParam = super.extractPublicParam(request);
//        //获取请求的ip地址
//        String ip = (String) extractPublicParam.get("extra_ip");
        //刚注册的用户，默认过期时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        RcUser u = new RcUser(null, mobile, 100, new BigDecimal(0), account, password, null, null, "中文", 0, df.parse(df.format(new Date())), 0, parentid, "0", showId, referralcode, null, "1");
        int insert = rcUserService.insertRcUser(u);
        if (insert == 0) {
            return Result.isFail().msg(MsgConstants.OPERATOR_FAIL);
        }
        String token = JWTUtil.sign(u.getPlatformId() + u.getAccount(), showId);
        JSONObject data = new JSONObject();
        data.put("X_Token", token);
        data.put("account", u.getAccount());
        data.put("mobile", u.getMobile());
        // 保存用户信息（account为key）
        String userKey = Constants.DB_USER + u.getPlatformId() + u.getAccount();
        redisService.set(userKey, u, Constants.DB_USER);
        // 保存登录token信息（userID为key）
        String tokenKey = Constants.DB_TOKEN + u.getPlatformId() + u.getId();
        redisService.set(tokenKey, token, Constants.LOGIN_TIMEOUT, Constants.DB_USER);
        return Result.isOk().data(data).msg(MsgConstants.USER_LOGIN_OK);
    }

    /**
     * 登陆
     *
     * @param account 账号
     * @param pass    密码
     * @param request
     * @return
     */
    @ApiOperation("登陆接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "String", name = "account", value = "账号", required = true),
                    @ApiImplicitParam(dataType = "String", name = "password", value = "密码", required = true)
            })
    @PostMapping("/login")
    public Result login(@RequestParam("account") String account,
                        @RequestParam("password") String pass,
                        HttpServletRequest request) {
        //登陆
        RcUser user = rcUserService.selectaccount(account, pass);
        if (null == user) {
            return Result.isFail().msg("账号密码不匹配");
        }
        String token = JWTUtil.sign(user.getPlatformId() + account, user.getInvitation());
        JSONObject data = new JSONObject();
        data.put("X_Token", token);
        data.put("account", user.getAccount());
        data.put("mobile", user.getMobile());
        // 保存用户信息（account为key）
        String userKey = Constants.DB_USER + user.getPlatformId() + account;
        redisService.set(userKey, user, Constants.DB_USER);
        // 保存登录token信息（userID为key）
        String tokenKey = Constants.DB_TOKEN + user.getPlatformId() + user.getId();
        redisService.set(tokenKey, token, Constants.LOGIN_TIMEOUT, Constants.DB_USER);
        //保存用户法币开关及币币开关默认值为false
        String switchFbKey = Constants.DB_LEGALCURRENCY + user.getPlatformId() + user.getId();
        String switchBbKey = Constants.DB_CURRENCY + user.getPlatformId() + user.getId();
        redisService.set(switchFbKey, false, Constants.DB_SWITCH);
        redisService.set(switchBbKey, false, Constants.DB_SWITCH);

        return Result.isOk().data(data).msg(MsgConstants.USER_LOGIN_OK);
    }

    /**
     * 根据旧密码修改密码
     * //     * @param oldPassword 旧密码
     *
     * @param newPassword 新密码
     * @param request
     * @return
     */
    @ApiOperation("修改密码接口")
    @ApiImplicitParams(
            {
//                    @ApiImplicitParam(dataType = "String", name = "oldPassword", value = "旧密码", required = true),
                    @ApiImplicitParam(dataType = "String", name = "newPassword", value = "新密码", required = true)
            })
    @PostMapping("/updatePassword")
    public Result editUserPass(
//            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword,
            HttpServletRequest request) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
//        //检验输入的旧密码
//        boolean isSame = bCryptPasswordEncoder.matches(user.getInvitation() + oldPassword, user.getPassword());
//        if (!isSame) {
//            return Result.isFail(MsgConstants.OLD_PASSWORD_ERROR);
//        }
        //设置用户新密码
        RcUser updateUser = new RcUser();
        updateUser.setId(user.getId());
        updateUser.setPassword(bCryptPasswordEncoder.encode(user.getInvitation() + newPassword));
        int isUpdate = rcUserService.updateRcUser(updateUser);
        if (isUpdate > 0) {
            //更新redis的user信息
            String token = JWTUtil.sign(user.getPlatformId() + user.getAccount(), user.getInvitation());
            JSONObject data = new JSONObject();
            data.put("X_Token", token);
            // 保存用户信息（account为key）
            String userKey = Constants.DB_USER + user.getPlatformId() + user.getAccount();
            redisService.set(userKey, user, Constants.DB_USER);
            // 保存登录token信息（userID为key）
            String tokenKey = Constants.DB_TOKEN + user.getPlatformId() + user.getId();
            redisService.set(tokenKey, token, Constants.LOGIN_TIMEOUT, Constants.DB_USER);
            return Result.isOk().data(data).msg(MsgConstants.USER_LOGIN_OK);
        }
        return Result.isFail().msg(MsgConstants.OPERATOR_FAIL);
    }

    /**
     * 退出登陆
     *
     * @param request
     * @return
     */
    @ApiOperation("退出登陆接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true)
            })
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        if (user == null) {
            return Result.unauthorized();
        }
        //移除用户信息
        String userKey = Constants.DB_USER + user.getPlatformId() + user.getAccount();
        redisService.remove(userKey, Constants.DB_USER);
        //移除token
        String tokenKey = Constants.DB_TOKEN + user.getPlatformId() + user.getId();
        redisService.remove(tokenKey, Constants.DB_USER);
        //保存用户法币开关及币币开关默认值为false
        String switchFbKey = Constants.DB_LEGALCURRENCY + user.getPlatformId() + user.getId();
        String switchBbKey = Constants.DB_CURRENCY + user.getPlatformId() + user.getId();
        redisService.set(switchFbKey, false, Constants.DB_SWITCH);
        redisService.set(switchBbKey, false, Constants.DB_SWITCH);
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
    }

    /**
     * 设置安全密码
     *
     * @param safeword 安全密码
     * @param request
     * @return
     */
    @ApiOperation("设置安全密码")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "String", name = "safeword", value = "安全密码", required = true)
            })
    @PostMapping("/addSafeWord")
    public Result addSafeWord( @RequestParam("safeword") String safeword, HttpServletRequest request ) {
        RcUser user =  systemUtil.getPlatformIdAndUserId(request);
        RcUser updateUser = new RcUser();
        updateUser.setId(user.getId());
        updateUser.setSafeword(bCryptPasswordEncoder.encode(user.getAccount() + safeword));
        int isUpdate = rcUserService.updateRcUser(updateUser);
        if (isUpdate > 0) {
            //更新redis的user信息
            String token = JWTUtil.sign(user.getPlatformId() + user.getAccount(),user.getInvitation());
            JSONObject data = new JSONObject();
            data.put("X_Token", token);
            // 保存用户信息（account为key）
            String userKey = Constants.DB_USER + user.getPlatformId() + user.getAccount();
            redisService.set(userKey, user, Constants.DB_USER);
            // 保存登录token信息（userID为key）
            String tokenKey = Constants.DB_TOKEN + user.getPlatformId() + user.getId();
            redisService.set(tokenKey, token, Constants.LOGIN_TIMEOUT, Constants.DB_USER);
            return Result.isOk().data(data).msg(MsgConstants.OPERATOR_SUCCESS);
        }
        return Result.isFail().msg(MsgConstants.OPERATOR_FAIL);
    }

    @ApiOperation("修改手机号接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "String", name = "mobile", value = "手机号", required = true)
            })
    @PostMapping("/updateMobile")
    public Result updateMobile( @RequestParam("mobile") String mobile, HttpServletRequest request){
        RcUser user =  systemUtil.getPlatformIdAndUserId(request);
        //检测修改的手机号是否重复
        JSONObject selectmobile = rcUserService.selectmobile(mobile);
        if (selectmobile !=null) {
            return Result.isFail().msg(MsgConstants.USER_ALLREADY_EXITS);
        }
        RcUser updateUser = new RcUser();
        updateUser.setId(user.getId());
        updateUser.setMobile(mobile);
        int isUpdate = rcUserService.updateRcUser(updateUser);
        if (isUpdate > 0) {
            //更新redis的user信息
            String token = JWTUtil.sign(user.getPlatformId() + user.getAccount(),user.getInvitation());
            JSONObject data = new JSONObject();
            data.put("X_Token", token);
            // 保存用户信息（account为key）
            String userKey = Constants.DB_USER + user.getPlatformId() + user.getAccount();
            redisService.set(userKey, user, Constants.DB_USER);
            // 保存登录token信息（userID为key）
            String tokenKey = Constants.DB_TOKEN + user.getPlatformId() + user.getId();
            redisService.set(tokenKey, token, Constants.LOGIN_TIMEOUT, Constants.DB_USER);
            return Result.isOk().data(data).msg(MsgConstants.OPERATOR_SUCCESS);
        }
        return Result.isFail().msg(MsgConstants.OPERATOR_FAIL);
    }

    @ApiOperation("添加/取消关注接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "String", name = "byCode", value = "币种code码", required = true),
                    @ApiImplicitParam(dataType = "Integer", name = "byType", value = "添加关注0 取消关注1", required = true, defaultValue = "0")
            })
    @PostMapping("/updateAttention")
    public Result updateAttention(
            @RequestParam("byCode") String byCode,
            @RequestParam("byType") Integer byType,
            HttpServletRequest request){
        RcUser user =  systemUtil.getPlatformIdAndUserId(request);
        //检测关注是否存在
        RcAttention attention = rcAttentionService.selectByUser(byCode, user.getId());
        RcAttention updateUser = new RcAttention();
        if(attention != null){
            updateUser.setId(attention.getId());
            updateUser.setStatus(byType);
            updateUser.setLastTime(new Date());
            int isUpdate = rcAttentionService.updateRcAttention(updateUser);
            if (isUpdate > 0) {
                return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
            }
        }else{
            updateUser.setUserId(user.getId());
            updateUser.setCoinType(byCode);
            updateUser.setStatus(byType);
            updateUser.setLastTime(new Date());
            int isUpdate = rcAttentionService.insertRcAttention(updateUser);
            if (isUpdate > 0) {
                return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
            }
        }
        return Result.isFail().msg(MsgConstants.OPERATOR_FAIL);
    }

    @ApiOperation("查询用户关注接口")
    @ApiImplicitParams(
            {
            })
    @PostMapping("/selectAttention")
    public Result selectAttention(
            HttpServletRequest request){
        RcUser user =  systemUtil.getPlatformIdAndUserId(request);
        JSONObject obj = rcUserMapper.selectAttention(user.getId());
        return Result.isOk().data(obj);
    }

}