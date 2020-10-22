package com.ruoyi.dxapi.controller.user;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ruoyi.common.constant.InfoConstants;
import com.ruoyi.common.constant.MsgConstants;
import com.ruoyi.common.dto.PageDTO;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.common.utils.*;
import com.ruoyi.common.utils.iparea.IPSeekers;
import com.ruoyi.common.utils.redis.RedisService;
import com.ruoyi.dx.domain.PlatformBase;
import com.ruoyi.dx.domain.User;
import com.ruoyi.dx.domain.UserLoginRec;
import com.ruoyi.dxapi.common.Constants;
import com.ruoyi.dxapi.common.Result;
import com.ruoyi.dxapi.common.SystemUtil;
import com.ruoyi.dxapi.controller.BaseController;
import com.ruoyi.dxservice.service.PlatformBaseService;
import com.ruoyi.dxservice.service.SysPlatformInfoService;
import com.ruoyi.dxservice.service.UserRankRecService;
import com.ruoyi.dxservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 用户信息接口层
 */
@RestController("dxUserController")
public class UserController extends BaseController {

    /**
     * 系统日志
     */
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRankRecService userRankRecService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private SysPlatformInfoService sysPlatformInfoService;
    @Autowired
    private PlatformBaseService platformBaseService;
    @Resource
    private SystemUtil systemUtil;

    /**
     * 会员登录
     *
     * @param request
     * @return
     */
    @RequestMapping("/dx-api/base/login")
    public Result login(HttpServletRequest request) {
        //域名解析，获取平台号
        String originName = super.getOrigin(request);
        PlatformBase platformBase = sysPlatformInfoService.getPlatformBaseByDomain(MsgConstants.IS_NO, MsgConstants.IS_NO, originName);
        if (null == platformBase) {
            log.info("域名查找平台号出错，查找的域名为："+originName);
            return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
        }
        //检测系统是否维护中
        JSONObject sysConf = sysPlatformInfoService.getSysConf(platformBase.getId(), InfoConstants.REDIS_PLATFORM_SYS_CONF, InfoConstants.PLATFORM_SYS_CONF);
        if (sysConf.containsKey("maintain")){
            if (MsgConstants.IS_YES == sysConf.getInt("maintain")){
                return Result.isFail().msg("系统维护中");
            }
        }
        String account = request.getParameter("account");
        String pass = request.getParameter("pass");
        //先去user表查找会员，如果没有会员，就去旧的表old_user查找，等旧的表也找不到会员，再返回账号密码不匹配
        User user = userService.login(account, pass, platformBase.getId());
        //当前时间
        Date nowDate = DateUtils.getNowDate();
        if (null == user){
            //插入登录失败日志
            Map<String, Object> extractPublicParam = super.extractPublicParam(request);
            //获取请求的ip地址
            String ip = (String) extractPublicParam.get("extra_ip");
            UserLoginRec userLoginRec = new UserLoginRec(SnowflakeIdWorker.genIdStr(),account,ip,nowDate,MsgConstants.IS_YES,"账号密码不匹配",platformBase.getId());
            userService.insertUserLoginRec(userLoginRec);
            return Result.isFail().msg("账号密码不匹配");
        }
        if (MsgConstants.IS_YES == user.getIsDel()){
            return Result.isFail().msg("该账号已注销");
        }
        if (MsgConstants.IS_NO == user.getIsUse()){
            return Result.isFail().msg("该账号已被封");
        }
        boolean isFirstLogin = false;
        //旧用户第一次登陆，密码要设置过来
        if (null == user.getPass()){
            if (null == user.getShowId()){
                return Result.isFail().msg("账号异常！");
            }
            String password = bCryptPasswordEncoder.encode(user.getShowId() + pass);
            user.setLoginTime(nowDate);
            user.setPass(password);
            int i = userService.updateUser(user);
            isFirstLogin = true;
            if (i == 0){
                return Result.isFail().msg("账号异常！");
            }
        }
        if (!isFirstLogin){
            //更新用户登录时间
            user.setLoginTime(nowDate);
            userService.updateUser(user);
        }
        String token = JWTUtil.sign(user.getPlatformId() + account,user.getShowId());
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
        try {
            addr = IPSeekers.getInstance().getAddress(ip);
            String strh = addr.substring(addr.length() -2);
            String strm = addr.substring(0,addr.length()-2);
            if("电信".equals(strh) || "移动".equals(strh)||"联通".equals(strh)){
                addr = strm;
            }
        } catch (Exception e) {
            addr = "-";
        }
        UserLoginRec userLoginRec = new UserLoginRec(SnowflakeIdWorker.genIdStr(),account,ip,nowDate,MsgConstants.IS_NO,"登录成功",platformBase.getId(),addr);
        userService.insertUserLoginRec(userLoginRec);
        return Result.isOk().data(data).msg(MsgConstants.USER_LOGIN_OK);
    }


    /**
     * 会员注册
     *
     * @param tel:手机号
     * @param passWord：密码
     * @param name：昵称
     * @param invitationCode：邀请码
     * @param verificationCode：验证码
     * @return
     */
    @RequestMapping("/dx-api/base/register")
    public Result register(@RequestParam("tel") String tel, @RequestParam("passWord") String passWord,
                           @RequestParam("name") String name, @RequestParam(value = "invitationCode", required = false) String invitationCode,
                           @RequestParam("verificationCode") String verificationCode, HttpServletRequest request) throws ParseException {
        //域名解析，获取平台号。然后获取该tel是否在该平台注册过了(被删除的可以重新注册，封号的不行)。
        String originName = super.getOrigin(request);
        PlatformBase platformBase = sysPlatformInfoService.getPlatformBaseByDomain(MsgConstants.IS_NO, MsgConstants.IS_NO, originName);
        if (null == platformBase) {
            log.info("域名查找平台号出错，查找的域名为："+originName);
            return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
        }
        //检测系统是否维护中
        JSONObject sysConf = sysPlatformInfoService.getSysConf(platformBase.getId(), InfoConstants.REDIS_PLATFORM_SYS_CONF, InfoConstants.PLATFORM_SYS_CONF);
        if (sysConf.containsKey("maintain")){
            if (MsgConstants.IS_YES == sysConf.getInt("maintain")){
                return Result.isFail().msg("系统维护中");
            }
        }
        //检测是否需要邀请码才能注册
        if (sysConf.containsKey("invitation")){
            if (MsgConstants.IS_YES == sysConf.getInt("invitation")){
                if (StringUtils.isEmpty(invitationCode)){
                    return Result.isFail().msg("需要邀请码才能注册");
                }
            }
        }
        //产生邀请码
        String showId = OrderNumUtil.getRandomNum(8);
        //检测产生的邀请码是否被使用了
        JSONObject userByShowId = userService.getUserByShowId(showId, MsgConstants.IS_NO, null);
        if (null != userByShowId){
            return Result.isFail().msg("请重试");
        }
        //检查验证码是否过期
        Object o = redisService.get(InfoConstants.VERIFICATION_PHONE_MAP + tel, InfoConstants.DB_MESSAGE);
        if (null == o) {
            return Result.isFail().msg(MsgConstants.PLEASE_SEND_AGAIN);
        }
        //验证输入的验证码是否正确
        if (!String.valueOf(o).equals(verificationCode)) {
            return Result.isFail().msg(MsgConstants.VERIFICATION_ERROR);
        }
        //检测是否存在正在使用
        User checkUser = userService.getUserByTel(tel, MsgConstants.IS_NO, null, platformBase.getId());
        if (null != checkUser) {
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
        JSONObject rankUserRec = userRankRecService.getRankUserRecById(null, 0, platformBase.getId(), MsgConstants.IS_NO);
        if (null == rankUserRec) {
            log.info("普通会员信息缺失，查找的平台id为："+platformBase.getId());
            return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
        }
        //刚注册的用户，默认过期时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date expireTime = sdf.parse("2099-12-12 12:12:00");
        User user = new User(SnowflakeIdWorker.genIdStr(), platformBase.getId(), showId, name, tel, tel, password, null, null, null, 0, rankUserRec.getStr("id"), null, MsgConstants.IS_NO, MsgConstants.IS_YES, MsgConstants.IS_NO, null, ip, DateUtils.getNowDate(), null, expireTime, null);
        // 注册
        User u = userService.register(platformBase.getId(), user, invitationCode);
        if (null == u) {
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
     * 获取用户“我的”信息
     */
    @RequestMapping("/dx-api/user/getUserCenter")
    public Result getUserCenter(HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        JSONObject data = userService.getUserCenter(user.getPlatformId(), user.getId());
        if (null == data) return Result.isFail().msg(MsgConstants.OPERATOR_FAIL).data(MsgConstants.DATA_ERROR);
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(data);
    }

    /**
     * 根据旧密码修改密码
     *
     * @param oldPass 旧密码
     * @param newPass 新密码
     * @param request
     * @return
     */
    @RequestMapping("dx-api/user/editUserPass")
    public Result editUserPass(@RequestParam("oldPass") String oldPass, @RequestParam("newPass") String newPass, HttpServletRequest request) {
        User user2 = systemUtil.getPlatformIdAndUserId(request);
        User user = userService.getUserById(user2.getId());
        //检验输入的旧密码
        boolean isSame = bCryptPasswordEncoder.matches(user.getShowId() + oldPass, user.getPass());
        if (!isSame) {
            return Result.isFail(MsgConstants.OLD_PASSWORD_ERROR);
        }
        user.setPass(bCryptPasswordEncoder.encode(user.getShowId() + newPass));
        int isUpdate = userService.updateUser(user);
        if (isUpdate > 0) {
            //更新redis的user信息
            String userKey = Constants.DB_USER + user.getPlatformId() + user.getAccount();
            redisService.set(userKey, user, Constants.DB_USER);

            String token = JWTUtil.sign(user2.getPlatformId() + user2.getAccount(), user2.getShowId());
            JSONObject data = new JSONObject();
            data.put("X_Token", token);
            // 保存登录token信息（userID为key）
            String tokenKey = Constants.DB_TOKEN + user2.getPlatformId() + user2.getId();
            redisService.set(tokenKey, token, Constants.LOGIN_TIMEOUT, Constants.DB_USER);
            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(data);
        }
        return Result.isFail().msg(MsgConstants.OPERATOR_FAIL);
    }

    /**
     * 获取徒弟列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/dx-api/user/getMyApprenticeList")
    public Result getMyApprenticeList(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("limit") Integer limit, HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        PageDTO dto = new PageDTO(pageNumber, limit);
        List data = userService.getMyApprenticeList(dto,user.getId());
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(data);
    }

    /**
     * 获取会员各级徒弟人数
     *
     * @param request
     * @return
     */
    @RequestMapping("/dx-api/user/getUserSubLevelCount")
    public Result getMyApprenticeList(HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        JSONObject data = userService.getUserSubLevelCount(user.getId());
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(data);
    }

    /**
     * 获取会员当前的等级以及有效天数
     *
     * @param request
     * @return
     */
    @RequestMapping("/dx-api/user/getCurrentRank")
    public Result getCurrentRank(HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        JSONObject rankUserRec = userRankRecService.getRankUserRecById(user.getRankId(), null, user.getPlatformId(), MsgConstants.IS_NO);
         if (null == rankUserRec || null == rankUserRec.get("rank")){
             log.info("用户等级数据缺失，查询的等级id为:"+user.getRankId());
             return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
         }
        //        JSONObject data = userService.getCurrentRank(user.getId());
        JSONObject jsonObject = new JSONObject();
        //如果当前只是普通会员
        if (0 == rankUserRec.getInt("rank")) {
            jsonObject.put("name", MsgConstants.USER_COMMON_RANK);
            jsonObject.put("times", MsgConstants.RANK_TIMES_NEVER);
            jsonObject.put("expireTime", "");
            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(jsonObject);
        }
        Date nowDate =  DateUtils.getNowDate();
        //如果是已经过期的会员,尽管有过期时间，也要判断是否已经过期，过期的就还是显示普通会员
        if (nowDate.compareTo(user.getExpireTime()) > 0){
            jsonObject.put("name", MsgConstants.USER_COMMON_RANK);
            jsonObject.put("times", MsgConstants.RANK_TIMES_NEVER);
            jsonObject.put("expireTime", "");
            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(jsonObject);
        }
        Date times = user.getExpireTime();
        Long betweenDays = (times.getTime() - nowDate.getTime()) / (1000L*3600L*24L);
        jsonObject.put("name", rankUserRec.get("name"));
        jsonObject.put("times", user.getTimes());
        jsonObject.put("expireTime",betweenDays);
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(jsonObject);
    }

    /**
     * 获取会员部分信息，用户设置按钮
     *
     * @param request
     * @return
     */
    @RequestMapping("/dx-api/user/getUserInfo")
    public Result getUserInfo(HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        JSONObject data = new JSONObject();
        data.put("id", user.getId());
        data.put("name", user.getName());
        data.put("gender", user.getGender());
        data.put("tel", user.getTel());
        data.put("headImg", user.getHeadImg());
        data.put("address", user.getAddress());
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(data);
    }

    /**
     * 修改昵称
     *
     * @param headImg：图片路径
     * @param type：操作类别，4是修改头像
     * @param request
     * @return
     */
    @RequestMapping("dx-api/user/editUserHeadImg")
    public Result editUserHeadImg(@RequestParam("type") Integer type, @RequestParam("headImg") String headImg, HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        //修改数据
        User user1 = userService.updateUserInfo(type, user, headImg);
        if (user1 != null) {
            //更新redis的user信息
            String userKey = Constants.DB_USER + user.getPlatformId() + user.getAccount();
            redisService.set(userKey, user1, Constants.DB_USER);
            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
        }
        return Result.isFail().msg(MsgConstants.OPERATOR_FAIL);
    }

    /**
     * 修改昵称
     *
     * @param name：新昵称
     * @param type：操作类别，1是修改昵称
     * @param request
     * @return
     */
    @RequestMapping("dx-api/user/editUserName")
    public Result editUserName(@RequestParam("type") Integer type, @RequestParam("name") String name, HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        //修改数据
        User user1 = userService.updateUserInfo(type, user, name);
        if (user1 != null) {
            //更新redis的user信息
            String userKey = Constants.DB_USER + user.getPlatformId() + user.getAccount();
            redisService.set(userKey, user1, Constants.DB_USER);
            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
        }
        return Result.isFail().msg(MsgConstants.OPERATOR_FAIL);
    }

    /**
     * 修改性别
     *
     * @param gender：新性别
     * @param type：操作类别，2是修改性别
     * @param request
     * @return
     */
    @RequestMapping("dx-api/user/editUserGender")
    public Result editUserGender(@RequestParam("type") Integer type, @RequestParam("gender") String gender, HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        //修改数据
        User user1 = userService.updateUserInfo(type, user, gender);
        if (user1 != null) {
            //更新redis的user信息
            String userKey = Constants.DB_USER + user.getPlatformId() + user.getAccount();
            redisService.set(userKey, user1, Constants.DB_USER);
            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
        }
        return Result.isFail().msg(MsgConstants.OPERATOR_FAIL);
    }

    /**
     * 修改地址
     *
     * @param address：新地址
     * @param type：操作类别，3是修改地址
     * @param request
     * @return
     */
    @RequestMapping("dx-api/user/editUserAddress")
    public Result editUserAdderss(@RequestParam("type") Integer type, @RequestParam("address") String address, HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        //修改数据
        User user1 = userService.updateUserInfo(type, user, address);
        if (user1 != null) {
            //更新redis的user信息
            String userKey = Constants.DB_USER + user.getPlatformId() + user.getAccount();
            redisService.set(userKey, user1, Constants.DB_USER);
            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
        }
        return Result.isFail().msg(MsgConstants.OPERATOR_FAIL);
    }

    /**
     * 通过手机号码检测用户名
     *
     * @param request
     * @return
     */
    @RequestMapping("dx-api/user/getUserName")
    public Result getUserName(@RequestParam("tel") String tel, HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        //被检测的用户
        User checkUser = userService.getUserByTel(tel, MsgConstants.IS_NO, MsgConstants.IS_YES, user.getPlatformId());
        if (null == checkUser) {
            return Result.isFail().msg(MsgConstants.NO_USER_EXITS);
        }
        JSONObject jsonObject = new JSONObject();
        if (null == checkUser.getName()) {
            jsonObject.put("name", " ");
        } else {
            jsonObject.put("name", checkUser.getName());
        }

        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(jsonObject);
    }

    /**
     * 获取我的邀请码
     *
     * @param request
     * @return
     */
    @RequestMapping("dx-api/user/getMyInvitationCode")
    public Result getMyInvitationCode(HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        PlatformBase platformBase = platformBaseService.selectPlatformBaseById(user.getPlatformId());
        if (null == platformBase){
            return Result.isFail("数据未初始化");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("invitationCode", user.getShowId());
        // 邀请链接(让前端自己去拼接)
        String invitationUrl = "/share/" + user.getPlatformId() + "/" + user.getShowId();
        jsonObject.put("invitationUrl", invitationUrl);
        jsonObject.put("shareUrl", platformBase.getShareUrl());
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(jsonObject);
    }


    /**
     * 获取图片二维码
     *
     * @param request
     * @param response
     */
    @RequestMapping("dx-api/base/getQrCode")
    public void getQrCode(HttpServletRequest request, HttpServletResponse response) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        // 内容所使用编码 ,生成二维码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        String text = request.getParameter("text");//要放进去的url
        int width = CheckTypeUtil.getInt(request.getParameter("width"), 300);
        int height = CheckTypeUtil.getInt(request.getParameter("height"), 300);

        try {
            text = new String(text.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            BitMatrix matrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);

            String download = request.getParameter("download");
            if (download != null && download.equals("yes")) {
                response.setContentType("image/png");
                response.setHeader("Content-Disposition", "attachment;filename=QRCode.png");
            }
            ServletOutputStream outpush = response.getOutputStream();
            ImageIO.write(image, "png", outpush);//将内存中的图片通过流动形式输出到客户端
            response.flushBuffer();
            outpush.flush();
            outpush.close();
        } catch (Exception e) {

        }
    }

    /**
     * 获取验证码
     *
     * @param tel:手机号
     * @return
     */
    @RequestMapping("/dx-api/base/getVerificationCode")
    public Result getVerificationCode(@RequestParam("tel") String tel, HttpServletRequest request) {
        String originName = super.getOrigin(request);
        PlatformBase platformBase = sysPlatformInfoService.getPlatformBaseByDomain(MsgConstants.IS_NO, MsgConstants.IS_NO, originName);
        if (null == platformBase) {
            log.info("域名查找平台号出错，查找的域名为："+originName);
            return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
        }
        String verificationCode = "";
        //检测短信发送是否开启
        JSONObject smsbaoConf = sysPlatformInfoService.getSysConf(platformBase.getId(), InfoConstants.REDIS_SMS_CONF, InfoConstants.SMS_CONF);
        if (smsbaoConf.containsKey("open")){
            if (MsgConstants.IS_NO == smsbaoConf.getInt("open")){
                redisService.set(InfoConstants.VERIFICATION_PHONE_MAP + tel, "168888", Constants.MESSAGE_VERIFICATIONCODE_TIMEOUT, InfoConstants.DB_MESSAGE);
            }else {
                verificationCode = sysPlatformInfoService.sendSmsMessage(platformBase.getId(), tel);
                if (StringUtils.isEmpty(verificationCode)) {
                    return Result.isFail().msg(MsgConstants.OPERATOR_FAIL);
                }
                //1分钟内只能发送一次
//                if (redisService.exists(InfoConstants.VERIFICATION_PHONE_MAP + tel, InfoConstants.DB_MESSAGE)) {
//                    return Result.isFail().msg(MsgConstants.NO_SEND_IN_SECOND);
//                }
                //设置过期时间为10分钟
                redisService.set(InfoConstants.VERIFICATION_PHONE_MAP + tel, verificationCode, Constants.MESSAGE_VERIFICATIONCODE_TIMEOUT, InfoConstants.DB_MESSAGE);
            }
        }else {
            redisService.set(InfoConstants.VERIFICATION_PHONE_MAP + tel, "168888", Constants.MESSAGE_VERIFICATIONCODE_TIMEOUT, InfoConstants.DB_MESSAGE);
        }
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
    }

    /**
     * 发送验证码重置密码(也就是：忘记密码)
     *
     * @param tel              手机号码
     * @param newPass          新密码
     * @param verificationCode 验证码
     * @param request
     * @return
     */
    @RequestMapping("dx-api/base/reSetUserPass")
    public Result reSetUserPass(@RequestParam("tel") String tel, @RequestParam("newPass") String newPass, @RequestParam("verificationCode") String verificationCode,
                                HttpServletRequest request) {
        //域名解析获取平台号
        String originName = super.getOrigin(request);
        PlatformBase platformBase = sysPlatformInfoService.getPlatformBaseByDomain(MsgConstants.IS_NO, MsgConstants.IS_NO, originName);
        if (null == platformBase) {
            log.info("域名查找平台号出错，查找的域名为："+originName);
            return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
        }
        String platformId = platformBase.getId();
        //检测系统是否维护中
        JSONObject sysConf = sysPlatformInfoService.getSysConf(platformId, InfoConstants.REDIS_PLATFORM_SYS_CONF, InfoConstants.PLATFORM_SYS_CONF);
        if (sysConf.containsKey("maintain")){
            if (MsgConstants.IS_YES == sysConf.getInt("maintain")){
                return Result.isFail().msg("系统维护中");
            }
        }
        //检查验证码是否过期
        Object o = redisService.get(InfoConstants.VERIFICATION_PHONE_MAP + tel, InfoConstants.DB_MESSAGE);
        if (null == o) {
            return Result.isFail().msg(MsgConstants.PLEASE_SEND_AGAIN);
        }
        //验证输入的验证码是否正确
        if (!String.valueOf(o).equals(verificationCode)) {
            return Result.isFail().msg(MsgConstants.VERIFICATION_ERROR);
        }
        //找到用户信息
        User user = userService.getUserByTel(tel, MsgConstants.IS_NO, MsgConstants.IS_YES, platformId);
        if (null == user) {
            return Result.isFail().msg(MsgConstants.NO_USER_EXITS);
        }
        user.setPass(bCryptPasswordEncoder.encode(user.getShowId() + newPass));
        int isUpdate = userService.updateUser(user);
        if (isUpdate > 0) {
            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
        }
        return Result.isFail().msg(MsgConstants.OPERATOR_FAIL);
    }

    @RequestMapping("/dx-api/user/logout")
    public Result logout(HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        //移除用户信息
        String userKey = Constants.DB_USER + user.getPlatformId() + user.getAccount();
        redisService.remove(userKey, Constants.DB_USER);
        //移除token
        String tokenKey = Constants.DB_TOKEN + user.getPlatformId() + user.getId();
        redisService.remove(tokenKey, Constants.DB_USER);
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
    }


    /**
     * 修改手机号
     *
     * @param tel:手机号
     * @param verificationCode:验证码
     * @return
     */
    @RequestMapping("/dx-api/user/editUserTel")
    public Result editUserTel(@RequestParam("tel") String tel, @RequestParam("verificationCode") String verificationCode, HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        String oldAccount = user.getAccount();
        //检查验证码是否过期
        Object o = redisService.get(InfoConstants.VERIFICATION_PHONE_MAP + tel, InfoConstants.DB_MESSAGE);
        if (null == o) {
            return Result.isFail().msg(MsgConstants.PLEASE_SEND_AGAIN);
        }
        //验证输入的验证码是否正确
        if (!String.valueOf(o).equals(verificationCode)) {
            return Result.isFail().msg(MsgConstants.VERIFICATION_ERROR);
        }
        //检验新手机号码是否被注册过了
        User userExist = userService.getUserByTel(tel, MsgConstants.IS_NO, null, user.getPlatformId());
        if (null != userExist) {
            return Result.isFail().msg(MsgConstants.NO_USER_EXITS);
        }
        user.setTel(tel);
        user.setAccount(tel);
        int isUpdate = userService.updateUser(user);
        if (isUpdate > 0) {
            //生成新的token
            String token = JWTUtil.sign(user.getPlatformId() + user.getAccount(),user.getShowId());
            JSONObject data = new JSONObject();
            data.put("X_Token", token);
            //移除旧用户信息
            String userKey = Constants.DB_USER + user.getPlatformId() + oldAccount;
            redisService.remove(userKey, Constants.DB_USER);
            //移除旧token
            String tokenKey = Constants.DB_TOKEN + user.getPlatformId() + user.getId();
            redisService.remove(tokenKey, Constants.DB_USER);

            // 保存新用户信息（account为key）
            String newUserKey = Constants.DB_USER + user.getPlatformId() + user.getAccount();
            redisService.set(newUserKey, user, Constants.DB_USER);
            // 保存登录新token信息（userID为key）
            String newTokenKey = Constants.DB_TOKEN + user.getPlatformId() + user.getId();
            redisService.set(newTokenKey, token, Constants.LOGIN_TIMEOUT, Constants.DB_USER);
            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(data);
        }
        return Result.isFail().msg(MsgConstants.OPERATOR_FAIL);
    }

    /**
     * 检验旧手机验证码
     *
     * @param tel:手机号
     * @param verificationCode:验证码
     * @return
     */
    @RequestMapping("/dx-api/user/verifyOldTel")
    public Result verifyOldTel(@RequestParam("tel") String tel, @RequestParam("verificationCode") String verificationCode, HttpServletRequest request) {
        //域名解析获取平台号
        String originName = super.getOrigin(request);
        PlatformBase platformBase = sysPlatformInfoService.getPlatformBaseByDomain(MsgConstants.IS_NO, MsgConstants.IS_NO, originName);
        if (null == platformBase) {
            log.info("域名查找平台号出错，查找的域名为："+originName);
            return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
        }
        //检查验证码是否过期
        Object o = redisService.get(InfoConstants.VERIFICATION_PHONE_MAP + tel, InfoConstants.DB_MESSAGE);
        if (null == o) {
            return Result.isFail().msg(MsgConstants.PLEASE_SEND_AGAIN);
        }
        //验证输入的验证码是否正确
        if (!String.valueOf(o).equals(verificationCode)) {
            return Result.isFail().msg(MsgConstants.VERIFICATION_ERROR);
        }
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
    }


}
