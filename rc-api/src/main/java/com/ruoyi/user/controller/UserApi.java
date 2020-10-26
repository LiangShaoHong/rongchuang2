package com.ruoyi.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.constant.InfoConstants;
import com.ruoyi.common.constant.MsgConstants;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.JWTUtil;
import com.ruoyi.common.utils.OrderNumUtil;
import com.ruoyi.common.utils.redis.RedisService;
import com.ruoyi.controller.BaseController;
import com.ruoyi.common.Constants;
import com.ruoyi.common.Result;
import com.ruoyi.user.domain.RcUser;
import com.ruoyi.user.mapper.RcUserMapper;
import com.ruoyi.user.mapperplus.RcUserMapperPlus;
import com.ruoyi.user.mapperplus.RcUserSmsMapper;
import com.ruoyi.user.service.IRcUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController("/rc-api/user")
public class UserApi extends BaseController {
    @Autowired
    IRcUserService iRcUserService;
    private static final Logger log = LoggerFactory.getLogger(UserApi.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private RcUserMapperPlus rcUserMapperPlus;
    @Autowired
    private RcUserSmsMapper rcUserSmsMapper;
    @Autowired
    private RcUserMapper rcUserMapper;
    @Lazy
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    /**
     * 会员注册
     *

     * @return
     */
    @RequestMapping("/ceshi")
    public String ceshi(String invi){
        JSONObject selectinvitation = rcUserMapper.selectinvitation(invi);


        return selectinvitation.toString();

    }
    @RequestMapping("/register")
    public Result addBank(@RequestParam("account") String account,
                          @RequestParam("password") String passWord,
                          @RequestParam("mobile") String mobile,
                          @RequestParam("verificationCode") String verificationCode,
                          @RequestParam("language") String language,

                          HttpServletRequest request) throws ParseException {

        //产生邀请码
        String showId = OrderNumUtil.getRandomNum(8);
        //检测产生的邀请码是否被使用了

        //JSONObject userByShowId = userService.getUserByShowId(showId, MsgConstants.IS_NO, null);


        JSONObject selectinvitation = rcUserMapper.selectinvitation(showId);
        Integer integer =0;

        if (selectinvitation!=null){
            return Result.isFail().msg("请重试");
        }
        //检查验证码是否过期
//        Object o = redisService.get(InfoConstants.VERIFICATION_PHONE_MAP + mobile, InfoConstants.DB_MESSAGE);
//        if (null == o) {
//            return Result.isFail().msg(MsgConstants.PLEASE_SEND_AGAIN);
//        }
//        //验证输入的验证码是否正确
//        if (!String.valueOf(o).equals(verificationCode)) {
//            return Result.isFail().msg(MsgConstants.VERIFICATION_ERROR);
//        }
        //检测手机号是否存在正在使用
        //User checkUser = userService.getUserByTel(mobile, MsgConstants.IS_NO, null, platformBase.getId());
        QueryWrapper queryWrapper1=new QueryWrapper();

        queryWrapper1.eq("mobile",mobile);
        JSONObject selectmobile = rcUserMapper.selectmobile(mobile);


        if (selectmobile !=null) {
            return Result.isFail().msg(MsgConstants.USER_ALLREADY_EXITS);
        }
//        //检测是否存在被封的号
//        User unWorkUser = userService.getUserByTel(tel, null, MsgConstants.IS_YES, platformBase.getId());
//        if (null != unWorkUser) {
//            return Result.isFail().msg(MsgConstants.USER_ALLREADY_FREEZE);
//        }

        String password = bCryptPasswordEncoder.encode(showId + passWord);
        Map<String, Object> extractPublicParam = super.extractPublicParam(request);
        //获取请求的ip地址
        String ip = (String) extractPublicParam.get("extra_ip");
        //拿到平台的普通会员信息,注册的会员默认为普通会员

//        JSONObject rankUserRec = userRankRecService.getRankUserRecById(null, 0, platformBase.getId(), MsgConstants.IS_NO);
//
//        if (null == rankUserRec) {
//            log.info("普通会员信息缺失，查找的平台id为："+platformBase.getId());
//            return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
//        }
        //刚注册的用户，默认过期时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date expireTime = sdf.parse("2099-12-12 12:12:00");
//        User user = new User(SnowflakeIdWorker.genIdStr(), platformBase.getId(), showId, name, tel, tel, password, null, null, null, 0, rankUserRec.getStr("id"), null, MsgConstants.IS_NO, MsgConstants.IS_YES, MsgConstants.IS_NO, null, ip, DateUtils.getNowDate(), null, expireTime, null);
//        // 注册
//        User u = userService.register(platformBase.getId(), user, invitationCode);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        String registertime=df.format(new Date());
        RcUser u = new RcUser(0l,
                mobile,
                100l,
                new BigDecimal(0),
                account,
                password,
                "",
                0l,
                language,
                0,
                df.parse(registertime) ,
                0,
                0l,
                "12",
                "",
                "",
                "0");

        int insert = rcUserMapper.insertRcUser(u);
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
     * 会员登录
     *
     * @param request
     * @return
     */
    @RequestMapping("/mobilelogin")
    public Result login(HttpServletRequest request) {
        //域名解析，获取平台号
        String originName = super.getOrigin(request);
//        PlatformBase platformBase = sysPlatformInfoService.getPlatformBaseByDomain(MsgConstants.IS_NO, MsgConstants.IS_NO, originName);
//        if (null == platformBase) {
//            log.info("域名查找平台号出错，查找的域名为："+originName);
//            return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
//        }
        //检测系统是否维护中
//        JSONObject sysConf = sysPlatformInfoService.getSysConf(platformBase.getId(), InfoConstants.REDIS_PLATFORM_SYS_CONF, InfoConstants.PLATFORM_SYS_CONF);
//        if (sysConf.containsKey("maintain")){
//            if (MsgConstants.IS_YES == sysConf.getInt("maintain")){
//                return Result.isFail().msg("系统维护中");
//            }
//        }
        String account = request.getParameter("account");
        String pass = request.getParameter("pass");
        //先去user表查找会员，如果没有会员，就去旧的表old_user查找，等旧的表也找不到会员，再返回账号密码不匹配
        //User user = userService.login(account, pass, platformBase.getId());
        //当前时间
        Date nowDate = DateUtils.getNowDate();
        QueryWrapper queryWrapperlogin=new QueryWrapper();
        queryWrapperlogin.eq("account",account);

        RcUser rcUser = rcUserMapper.selectaccount(account);
        if(rcUser==null){

            return Result.isFail().msg("查无此账号");
        }
        //RcUser rcUser = rcUserMapper.selectaccount(queryWrapperlogin);

        String password = bCryptPasswordEncoder.encode(rcUser.getInvitation() + pass);
        QueryWrapper verify=new QueryWrapper();
        verify.eq("password",password);
        verify.eq("account",account);
        RcUser selectverify = rcUserMapper.selectverify(account, password);
        if(selectverify==null){
            return Result.isFail().msg("账号密码不匹配");
        }

//        if (MsgConstants.IS_YES == user.getIsDel()){
//            return Result.isFail().msg("该账号已注销");
//        }
//        if (MsgConstants.IS_NO == user.getIsUse()){
//            return Result.isFail().msg("该账号已被封");
//        }
        boolean isFirstLogin = false;
        //旧用户第一次登陆，密码要设置过来
//        if (null == user.getPass()){
//            if (null == user.getShowId()){
//                return Result.isFail().msg("账号异常！");
//            }
//            String password = bCryptPasswordEncoder.encode(user.getShowId() + pass);
//            user.setLoginTime(nowDate);
//            user.setPass(password);
//            int i = userService.updateUser(user);
//            isFirstLogin = true;
//            if (i == 0){
//                return Result.isFail().msg("账号异常！");
//            }
//        }
//        if (!isFirstLogin){
//            //更新用户登录时间
//            user.setLoginTime(nowDate);
//            userService.updateUser(user);
//        }

        RcUser user = rcUser;
        String token = JWTUtil.sign(user.getPlatformId() + account,user.getInvitation());
        JSONObject data = new JSONObject();
        data.put("X_Token", token);
        // 保存用户信息（account为key）
        String userKey = Constants.DB_USER + user.getPlatformId() + account;
        redisService.set(userKey, user, Constants.DB_USER);
        // 保存登录token信息（userID为key）
        String tokenKey = Constants.DB_TOKEN + user.getPlatformId() + user.getId();
        redisService.set(tokenKey, token, Constants.LOGIN_TIMEOUT, Constants.DB_USER);
        Map<String, Object> extractPublicParam = super.extractPublicParam(request);
        //插入登录日志
        String ip = (String) extractPublicParam.get("extra_ip");
        String addr = null;

        return Result.isOk().data(data).msg(MsgConstants.USER_LOGIN_OK);
    }

}