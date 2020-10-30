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
import com.ruoyi.user.domain.RcUser;
import com.ruoyi.user.mapper.RcUserMapper;
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

    @Lazy
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 注册账号
     * @param account           用户名
     * @param passWord          密码
     * @param mobile            手机号
     * @param referralcode      注册推荐码
     * @param verificationCode  验证码
     * @param language          选择的语言/区分区域
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
                    @ApiImplicitParam(dataType = "String", name = "verificationCode", value = "验证码(该参数暂时没用 不传)", required = false),
                    @ApiImplicitParam(dataType = "String", name = "language", value = "地区(该参数暂时没用 不传)", required = false)
            })
    @PostMapping("/register")
    public Result register(@RequestParam("account") String account,
                          @RequestParam("password") String passWord,
                          @RequestParam("mobile") String mobile,
                          @RequestParam(value = "referralcode", required = false) String referralcode,
                          @RequestParam(value = "verificationCode", required = false) String verificationCode,
                          @RequestParam("language") String language,
                          HttpServletRequest request) throws ParseException {
        //产生邀请码
        String showId = OrderNumUtil.getRandomNum(8);
        //检测产生的邀请码是否被使用了
        JSONObject selectinvitation = rcUserService.selectinvitation(showId);
        if (selectinvitation!=null){
            return Result.isFail().msg("请重试");
        }
        //推荐码非空时检测推荐码是否有效
        int parentid = 0;
        if(!StringUtils.isEmpty(referralcode)){
            JSONObject selectreferralcode = rcUserService.selectreferralcode(referralcode);
            if (selectreferralcode==null){
                return Result.isFail().msg("推荐码无效");
            }
            parentid = (int) selectreferralcode.get("id");
        }
        //检测用户名是否重复
        RcUser user = rcUserMapper.selectaccount(account);
        if (user != null){
            return Result.isFail().msg("用户名重复");
        }
        //检测手机号是否存在正在使用
        QueryWrapper queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("mobile",mobile);
        JSONObject selectmobile = rcUserService.selectmobile(mobile);
        if (selectmobile !=null) {
            return Result.isFail().msg(MsgConstants.USER_ALLREADY_EXITS);
        }
        //加密
        String password = bCryptPasswordEncoder.encode(showId + passWord);
        Map<String, Object> extractPublicParam = super.extractPublicParam(request);
        //获取请求的ip地址
        String ip = (String) extractPublicParam.get("extra_ip");
        //刚注册的用户，默认过期时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        RcUser u = new RcUser(null,mobile,100,new BigDecimal(0),account,password,null,null,"中文",0,df.parse(df.format(new Date())),0,parentid,"0", showId, referralcode,null,"1");
        int insert = rcUserService.insertRcUser(u);
        if (insert==0) {
            return Result.isFail().msg(MsgConstants.INVITATION_UNUSER);
        }
        String token = JWTUtil.sign(u.getPlatformId() + u.getAccount(), showId);
        JSONObject data = new JSONObject();
        data.put("X_Token", token);
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
     * @param account   账号
     * @param pass      密码
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
        RcUser user = rcUserService.selectaccount(account,pass);
        if (null == user){
            return Result.isFail().msg("账号密码不匹配");
        }
        String token = JWTUtil.sign(user.getPlatformId() + account,user.getInvitation());
        JSONObject data = new JSONObject();
        data.put("X_Token", token);
        // 保存用户信息（account为key）
        String userKey = Constants.DB_USER + user.getPlatformId() + account;
        redisService.set(userKey, user, Constants.DB_USER);
        // 保存登录token信息（userID为key）
        String tokenKey = Constants.DB_TOKEN + user.getPlatformId() + user.getId();
        redisService.set(tokenKey, token, Constants.LOGIN_TIMEOUT, Constants.DB_USER);

        // websocket 通知后台 有用户登陆 首页页面输出用户名和音乐
        JSONObject msg = new JSONObject();
        msg.put("code", "1");
        msg.put("account", user.getAccount());
        msg.put("data", "withdraw");
        pushService.sendToGroup(user.getPlatformId(), msg.toString());

        JSONObject msg1 = new JSONObject();
        msg1.put("xx", "我来了");
        pushService.sendToUser("2", msg1.toString());

        return Result.isOk().data(data).msg(MsgConstants.USER_LOGIN_OK);
    }

    /**
     * 根据旧密码修改密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param request
     * @return
     */
    @ApiOperation("修改密码接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "String", name = "oldPassword", value = "旧密码", required = true),
                    @ApiImplicitParam(dataType = "String", name = "newPassword", value = "新密码", required = true)
            })
    @PostMapping("/updatePassword")
    public Result editUserPass(
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword,
            HttpServletRequest request) {
        RcUser user =  systemUtil.getPlatformIdAndUserId(request);
        //检验输入的旧密码
        boolean isSame = bCryptPasswordEncoder.matches(user.getInvitation() + oldPassword, user.getPassword());
        if (!isSame) {
            return Result.isFail(MsgConstants.OLD_PASSWORD_ERROR);
        }
        //设置用户新密码
        RcUser updateUser = new RcUser();
        updateUser.setId(user.getId());
        updateUser.setPassword(bCryptPasswordEncoder.encode(user.getInvitation() + newPassword));
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
            return Result.isOk().data(data).msg(MsgConstants.USER_LOGIN_OK);
        }
        return Result.isFail().msg(MsgConstants.OPERATOR_FAIL);
    }

    @ApiOperation("加减币测试接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "String", name = "userId", value = "交易会员id", required = true),
                    @ApiImplicitParam(dataType = "String", name = "userName", value = "交易会员名称", required = true),
                    @ApiImplicitParam(dataType = "String", name = "fromUserId", value = "交易对象id(订单ID)", required = true),
                    @ApiImplicitParam(dataType = "String", name = "money", value = "金额变化值", required = true),
                    @ApiImplicitParam(dataType = "String", name = "cashHandFee", value = "手续费(平台佣金)", required = true),
                    @ApiImplicitParam(dataType = "String", name = "recordType", value = "资金变化类型 0转账 1提现 2充值 3后台人员操作", required = true),
                    @ApiImplicitParam(dataType = "String", name = "mark", value = "备注说明", required = true)
            })
    @PostMapping("/updateMoney")
    public Result userMoneyService(
            @RequestParam("userId") String userId,
            @RequestParam("userName") String userName,
            @RequestParam("fromUserId") String fromUserId,
            @RequestParam("money") String money,
            @RequestParam("cashHandFee") String cashHandFee,
            @RequestParam("recordType") String recordType,
            @RequestParam("mark") String mark,
            HttpServletRequest request) {
        boolean isSame = userMoneyService.moneyDoCenter(userId,userName,fromUserId,new BigDecimal(money),new BigDecimal(cashHandFee),recordType,mark);
        return Result.isOk().data(isSame);
    }

}