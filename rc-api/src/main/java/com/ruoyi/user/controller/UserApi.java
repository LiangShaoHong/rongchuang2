package com.ruoyi.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.SystemUtil;
import com.ruoyi.common.constant.MsgConstants;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.common.push.PushService;
import com.ruoyi.common.utils.JWTUtil;
import com.ruoyi.common.utils.OrderNumUtil;
import com.ruoyi.common.utils.redis.RedisService;
import com.ruoyi.controller.BaseController;
import com.ruoyi.common.Constants;
import com.ruoyi.common.Result;
import com.ruoyi.user.domain.RcUser;
import com.ruoyi.user.service.IRcUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
@RestController
@RequestMapping("/rc-api/user")
public class UserApi extends BaseController {

    @Autowired
    IRcUserService iRcUserService;
    private static final Logger log = LoggerFactory.getLogger(UserApi.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private IRcUserService rcUserService;

    @Resource
    private SystemUtil systemUtil;

    @Autowired
    private PushService pushService;

    @Lazy
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 注册账号
     * @param account           用户名
     * @param passWord          密码
     * @param mobile            手机号
     * @param invitationCode    邀请码
     * @param verificationCode  验证码
     * @param language          选择的语言/区分区域
     * @param request
     * @return
     * @throws ParseException
     */
    @RequestMapping("/register")
    public Result addBank(@RequestParam("account") String account,
                          @RequestParam("password") String passWord,
                          @RequestParam("mobile") String mobile,
                          @RequestParam(value = "invitationCode", required = false) String invitationCode,
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
        RcUser u = new RcUser(null,mobile,100,new BigDecimal(0),account,password,null,null,"中文",0,df.parse(df.format(new Date())),0,0,"0",showId,null,"1");
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
    @RequestMapping("/login")
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

        return Result.isOk().data(data).msg(MsgConstants.USER_LOGIN_OK);
    }

}