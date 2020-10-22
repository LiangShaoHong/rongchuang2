package com.ruoyi.dxapi.controller.money;

import com.github.pagehelper.PageHelper;
import com.ruoyi.common.constant.InfoConstants;
import com.ruoyi.common.constant.MsgConstants;
import com.ruoyi.common.dto.PageDTO;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.common.push.PushService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.OrderNumUtil;
import com.ruoyi.common.utils.SnowflakeIdWorker;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.redis.RedisService;
import com.ruoyi.dx.domain.*;
import com.ruoyi.dx.dto.PayTypeDTO;
import com.ruoyi.dx.service.IPayPlatformTypeService;
import com.ruoyi.dx.service.ISysBankTypeService;
import com.ruoyi.dxapi.common.Constants;
import com.ruoyi.dxapi.common.Result;
import com.ruoyi.dxapi.common.SystemUtil;
import com.ruoyi.dxservice.service.*;
import com.ruoyi.system.dto.DictDataListDTO;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysDictTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 金钱控制层
 */
@RestController("dxMoneyController")
public class MoneyController {

    private static final Logger log = LoggerFactory.getLogger(MoneyController.class);


    @Autowired
    private MoneyService moneyService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private SysPlatformInfoService sysPlatformInfoService;
    @Autowired
    private UserBankRecService userBankRecService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRankRecService userRankRecService;
    @Resource
    private SystemUtil systemUtil;

    @Autowired
    private PushService pushService;

    @Autowired
    private IPayPlatformTypeService iPayPlatformTypeService;
    @Autowired
    private ISysDictDataService dictDataService;
    @Autowired
    private ISysBankTypeService iSysBankTypeService;


    /**
     * 获取资金明细
     * @param pageNumber:页码
     * @param limit:每页数量
     * @return
     */
    @RequestMapping("/dx-api/money/getMoneyRecordList")
    public Result getMoneyRecordList(@RequestParam("pageNumber")Integer pageNumber,
                                     @RequestParam("limit")Integer limit,
                                     @RequestParam("type")Integer type,
                                     HttpServletRequest request){
        User user = systemUtil.getPlatformIdAndUserId(request);
        PageDTO dto = new PageDTO(pageNumber, limit);
        List<JSONObject> moneyRecordList = moneyService.getMoneyRecordList(dto,user.getId(), type);
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(moneyRecordList);
    }

    /**
     * 获取提现记录
     * @param pageNumber:页码
     * @param limit:每页数量
     * @return
     */
    @RequestMapping("/dx-api/money/getCashList")
    public Result getCashList(@RequestParam("pageNumber")Integer pageNumber, @RequestParam("limit")Integer limit,
                                     HttpServletRequest request){
        User user = systemUtil.getPlatformIdAndUserId(request);
        PageDTO dto = new PageDTO(pageNumber, limit);
        List<JSONObject> moneyRecordList = moneyService.getCashList(dto,user.getId());
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(moneyRecordList);
    }

    /**
     * 获取转账记录
     * @param pageNumber:页码
     * @param limit:每页数量
     * @return
     */
    @RequestMapping("/dx-api/money/getMoneyTransferList")
    public Result getMoneyTransferList(@RequestParam("pageNumber")Integer pageNumber, @RequestParam("limit")Integer limit,
                                       HttpServletRequest request){
        User user = systemUtil.getPlatformIdAndUserId(request);
        PageDTO dto = new PageDTO(pageNumber, limit);
        List<JSONObject> moneyRecordList = moneyService.getMoneyTransferList(dto,user.getId());
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(moneyRecordList);
    }

    /**
     * 获取充值记录
     * @param pageNumber:页码
     * @param limit:每页数量
     * @return
     */
    @RequestMapping("/dx-api/money/getRechargeCodeList")
    public Result getRechargeCodeList(@RequestParam("pageNumber")Integer pageNumber, @RequestParam("limit")Integer limit,
                                       HttpServletRequest request){
        User user = systemUtil.getPlatformIdAndUserId(request);
        PageDTO dto = new PageDTO(pageNumber, limit);
        List<JSONObject> moneyUserRechargeList = moneyService.getMoneyUserRechangeList(dto,user.getId());
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(moneyUserRechargeList);
    }

    /**
     * 会员充值
     * @param money:充值金额
     * @param sysBankInfo：系统收款信息
     * @param mark:充值说明
     * @param imgs：截图
     * @param type：充值类型： 1直接升级，2充值到余额
     * @param mealId：套餐id，充值升级的时候才传
     * @param request
     * @return
     */
    @RequestMapping("dx-api/money/charge")
    public Result charge (@RequestParam("money") BigDecimal money, @RequestParam("sysBankInfo")String sysBankInfo,@RequestParam("type") int type,
                          @RequestParam(value = "mark",required = false)String mark, @RequestParam("imgs")String imgs,@RequestParam(value = "mealId",required = false)String mealId,
                          HttpServletRequest request) {
        if (money.compareTo(BigDecimal.valueOf(0)) < 0){
            return Result.isFail().msg(MsgConstants.DATA_ERROR);
        }
        User user = systemUtil.getPlatformIdAndUserId(request);
        //找出充值的信息
        JSONObject rechargeInfo = sysPlatformInfoService.getSystemInfo(user.getPlatformId(),InfoConstants.REDIS_RECHARGE_CONF,InfoConstants.RECHARGE_CONF);
        com.alibaba.fastjson.JSONObject date = com.alibaba.fastjson.JSONObject.parseObject(rechargeInfo.getStr("feeDate"));
        //检测是否有开启充值的功能
        if (MsgConstants.IS_NO == date.getInteger("rechargeall")){
            return Result.isFail().msg("充值功能暂时关闭");
        }

        //如果存在还未审核的充值单，就不让充。
        List<JSONObject> rechargeRecList = moneyService.getRechargeRecList(user.getId(),MsgConstants.CHECK_STATUS_CHECKING, user.getPlatformId());
        if (0 != rechargeRecList.size()){
            return Result.isFail().msg("您还有未审核的订单");
        }
        //充值分两种情况，1是直接升级2直接冲到余额
        Map map = null;
        //直接冲到余额
        if (MsgConstants.RECHARGE_TYPE_TO_WALLET == type){
            //随机金额
            Random rd = new Random();
            String suffix = String.format("%.2f", (rd.nextDouble() * 100) / 100);
            map = moneyService.rechargeToCash(user.getId(), user.getPlatformId(), money.add(new BigDecimal(suffix)), MsgConstants.MONEY_RECHARGE_REC, MsgConstants.OPERATOR_RECHARGE_TO_WALLET, sysBankInfo, imgs,mark);
            //直接升级
        }else {
            Map checkCanBuyRes = moneyService.checkCanBuy(user.getId(), user.getPlatformId(), mealId);
            if (checkCanBuyRes.containsKey("0")){
                return Result.isFail().msg(checkCanBuyRes.get("0").toString());
            }
            map = moneyService.rechargeToLevelUp(user.getId(), user.getPlatformId(), money.negate(), MsgConstants.MONEY_RECHARGE_REC, MsgConstants.OPERATOR_RECHARGE_TO_LEVEL, sysBankInfo, imgs,mealId,mark);
        }
        if (map.containsKey(1)){

            // 通知后台
            JSONObject msg = new JSONObject();
            msg.put("code", "1");
            msg.put("data", "recharge");
            pushService.sendToGroup(user.getPlatformId(), msg.toString());
            Map<String, String> data = new HashMap<>();
            data.put("id", map.get(2).toString());
            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(data);
        }
        return Result.isFail().msg(map.get(0).toString());
    }

    /**
     * 会员充值
     * @param id：充值订单ID
     * @param imgs：截图
     * @param request
     * @return
     */
    @RequestMapping("/dx-api/money/chargePay")
    public Result chargePay (HttpServletRequest request, @RequestParam("id") String id,
                             @RequestParam("imgs") String imgs, @RequestParam(value = "mark",required = false) String mark) {
        if (null == imgs || "".equals(imgs)){
            return Result.isFail("请上传支付截图");
        }
        User user = systemUtil.getPlatformIdAndUserId(request);
        moneyService.chargePay(user.getPlatformId(), user.getId(), id, imgs, mark);
        return Result.isOk().msg("提交支付成功");
    }

    /**
     * 取消会员充值
     * @param id：充值订单ID
     * @param request
     * @return
     */
    @RequestMapping("/dx-api/money/cancelCharge")
    public Result cancelCharge (@RequestParam("id") String id, HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        moneyService.cancelRechargeOrder(user.getPlatformId(), user.getId(), id);
        return Result.isOk().msg("取消成功");
    }

    /**
     * 获取会员未付款的充值订单
     * @param request
     * @return
     */
    @RequestMapping("/dx-api/money/selectUnpaidCharge")
    public Result selectUnpaidCharge (@RequestParam("id") String id, HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if (null == id || "".equals(id)){
            return Result.isFail("参数错误");
        }
        MoneyUserRechange moneyUserRechange = moneyService.selectUnPayRechargeOrder(user.getPlatformId(), user.getId(), id);
        return Result.isOk().data(moneyUserRechange);
    }

    /**
     * 会员提现
     * @param money:提现金额
     * @param userBankInfo：收款信息
     * @param request
     * @return
     */
    @RequestMapping("dx-api/money/cash")
    public Result cash (@RequestParam("money") BigDecimal money, @RequestParam("userBankInfo")String userBankInfo,
                        HttpServletRequest request) {
        //管理后台审核成功后，用户资金记录表插入数据，用户统计表更新。若审核不成功，把钱退回到余额
        //用户端的操作是，用户钱包更新，往用户提现记录表插入数据，
        User user = systemUtil.getPlatformIdAndUserId(request);
        //拿到系统信息配置，包含提现提示信息，转账手续费率
        UserCashRule cashConf = sysPlatformInfoService.getCashConf(user.getPlatformId(), user.getRankId(), InfoConstants.REDIS_CASH_CONF, InfoConstants.CASH_CONF);
        if (null == cashConf){
            log.info("提现配置缺失，查找的域名为："+user.getPlatformId());
            return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
        }
        //检测是否有开启提现个的功能
        if (null == cashConf.getCashall() || !StringUtils.isNumeric(cashConf.getCashall()) || MsgConstants.IS_NO == Integer.valueOf(cashConf.getCashall())){
            return Result.isFail().msg("提现功能暂时关闭");
        }
        if (null == cashConf.getCashType()){
            log.info("提现配置类型为空");
            return Result.isFail().msg("提现功能暂时关闭");
        }
        if (MsgConstants.CASH_TYPE_MULTIPLE == cashConf.getCashType()){
            if (!StringUtils.isNumeric(cashConf.getCashstart()) || !StringUtils.isNumeric(cashConf.getCashend()) || !StringUtils.isNumeric(cashConf.getInteger())){
                log.info("提现配置存在非法字符，cashstart|cashend|integer");
                return Result.isFail().msg("提现功能暂时关闭");
            }
            //最小提现金额
            BigDecimal moneyMin = new BigDecimal(cashConf.getCashstart());
            //最大提现金额
            BigDecimal moneyMax = new BigDecimal(cashConf.getCashend());
            //提现金额得在最大和最小之间
            if (money.compareTo(moneyMin) < 0 || money.compareTo(moneyMax) > 0){
                return Result.isFail().msg("提现金额在["+moneyMin+"至"+moneyMax+"]之间");
            }

            BigDecimal moneyLimit = new BigDecimal(cashConf.getInteger());
            //看能否整除
            if (money.remainder(moneyLimit).compareTo(BigDecimal.ZERO) != 0){
                return Result.isFail().msg("提现金额必须为"+moneyLimit+"的整数倍");
            }
        } else {
            if(null == cashConf.getMoneyList() || cashConf.getMoneyList().size() == 0){
                log.info("提现配置错误，金额列表不存在");
                return Result.isFail().msg("提现功能暂时关闭");
            }
            if (!cashConf.getMoneyList().contains(money.intValue())){
                return Result.isFail().msg("金额不合法，请重新选择");
            }
        }
        //拿到今日提交提现的数据
        JSONObject todayCashMessage = moneyService.getTodayCashTimes(user.getId());
        int todayCashTimes = todayCashMessage.getInt("cashTimes");
        //系统设置的提现次数
        if (!StringUtils.isNumeric(cashConf.getCounttop())){
            log.info("提现配置存在非法字符，counttop");
            return Result.isFail().msg("提现功能暂时关闭");
        }
        int cashTimesLimit = Integer.valueOf(cashConf.getCounttop());
        if (todayCashTimes >= cashTimesLimit){
            return Result.isFail().msg("每天提现次数为："+cashTimesLimit+"次");
        }
        //拿到会员的钱，进行校验，提现金额不可超过钱包金额
        JSONObject userMoney = moneyService.getUserMoney(user.getId());
        //将钱转成BigDecimal
        BigDecimal myMoney = new BigDecimal(userMoney.getStr("money"));
        if ((money.compareTo(myMoney)) > 0){
            return Result.isFail().msg(MsgConstants.DATA_ERROR);
        }
        //校验是否有收款银行卡
        JSONObject morenBank = userBankRecService.getBankInfo(user.getId(),user.getPlatformId());
        if (null == morenBank){
            return Result.isFail().msg(MsgConstants.NO_BANK_CAR);
        }
        //手续费率 , %形式
        String fee = cashConf.getRate();
        if (!StringUtils.isNumeric(fee)){
            log.info("提现配置存在非法字符，rate");
            return Result.isFail().msg("提现功能暂时关闭");
        }
        //手续费率，除以100后
        BigDecimal handFee = BigDecimal.valueOf(Double.valueOf(fee)).divide(BigDecimal.valueOf(100));
        //手续费
        BigDecimal feeMoney = (money.multiply(handFee)).setScale(0, BigDecimal.ROUND_UP);
        //到账金额方式： 0实际到账金额=转账金额-手续费 1实际到账金额=转账金额
        if (!StringUtils.isNumeric(cashConf.getMethod())){
            log.info("提现配置存在非法字符，method");
            return Result.isFail().msg("提现功能暂时关闭");
        }
        int moneyMethod = Integer.valueOf(cashConf.getMethod());
        if (1 == moneyMethod){
            if (((money.add(feeMoney)).compareTo(myMoney)) > 0){
                return Result.isFail().msg("余额不足,手续费为"+feeMoney);
            }
        }
        //输入的金额
        BigDecimal transferMoney = money;
        //选择0的话，实际到账金额先算出来
        if (0 == moneyMethod){
            money = money.subtract(feeMoney);
        }
        //系统设置的每日提现金额上限
        if (!StringUtils.isNumeric(cashConf.getMoneytop())){
            log.info("提现配置存在非法字符，moneytop");
            return Result.isFail().msg("提现功能暂时关闭");
        }
        BigDecimal cashTop = new BigDecimal(cashConf.getMoneytop());
        //今日提现总额
        BigDecimal todayCashTotal = new BigDecimal(todayCashMessage.getStr("moneyTotal"));
        //今日已提现的加上现在要提现的
        BigDecimal lastTotal = todayCashTotal.add(money);
        if (cashTop.compareTo(lastTotal) < 0){
            return Result.isFail().msg("每天提现总额上限为："+cashTop);
        }

        MoneyUserCash moneyUserCash = new MoneyUserCash(SnowflakeIdWorker.genIdStr(),user.getPlatformId(),user.getId(),transferMoney,money,feeMoney,userBankInfo,MsgConstants.CHECK_STATUS_CHECKING,null,null,null,null);
        Map map = moneyService.cash(user.getId(),user.getPlatformId(),money,MsgConstants.MONEY_CASH_REC,MsgConstants.OPERATOR_CASH,moneyUserCash);
        if (map.containsKey(1)){

            // 通知后台
            JSONObject msg = new JSONObject();
            msg.put("code", "1");
            msg.put("data", "withdraw");
            pushService.sendToGroup(user.getPlatformId(), msg.toString());

            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
        }
        return Result.isFail().msg(map.get(0).toString());
    }


    /**
     * 会员转账
     * @param money:转账金额
     * @param tel：收款方手机号
     * @param request
     * @return
     */
    @RequestMapping("dx-api/money/transfer")
    public Result transfer (@RequestParam("money") BigDecimal money, @RequestParam("tel")String tel, HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        //拿到系统信息配置，包含提现提示信息，是否有转账功能
        JSONObject transferInfo = sysPlatformInfoService.getSystemInfo(user.getPlatformId(),InfoConstants.REDIS_TRASFER_CONF,InfoConstants.TRASFER_CONF);
        com.alibaba.fastjson.JSONObject date = com.alibaba.fastjson.JSONObject.parseObject(transferInfo.getStr("feeDate"));
        //检测是否有开启提现个的功能
        if (MsgConstants.IS_NO == date.getInteger("transferall")){
            return Result.isFail().msg("转账功能暂时关闭");
        }
        //普通会员就要看general开关是否开启  0关1开
        JSONObject rankUserRec = userRankRecService.getRankUserRecById(user.getRankId(), null, user.getPlatformId(), MsgConstants.IS_NO);
        Date expireTime = user.getExpireTime();
        if (0 == rankUserRec.getInt("rank") || DateUtils.getNowDate().compareTo(expireTime) > 0){
            if (MsgConstants.IS_NO == date.getInteger("general")){
                return Result.isFail().msg("转账功能暂时关闭");
            }
        }
        //会员提现成功次数
        int finishCount = moneyService.getCountByStatusAndUserId(user.getId(), MsgConstants.WITHDRAWAL_STATUS_SUCCEED);
        if (finishCount < date.getInteger("cashTimes")){
            return Result.isFail().msg("提现成功次数达"+date.getInteger("cashTimes")+"次才可以转账");
        }
        //最小转账金额
        BigDecimal moneyMin = BigDecimal.valueOf(date.getDoubleValue("transferstart"));
        //最大转账金额
        BigDecimal moneyMax = BigDecimal.valueOf(date.getDoubleValue("transferend"));
        //转账金额得在最大和最小之间
        if (money.compareTo(moneyMin) < 0 || money.compareTo(moneyMax) > 0){
            return Result.isFail().msg("转账金额在["+moneyMin+"至"+moneyMax+"]之间");
        }

        //金额校验，转账金额不可超过钱包金额
        JSONObject userMoney = moneyService.getUserMoney(user.getId());
        //将钱转成BigDecimal
        BigDecimal myMoney = new BigDecimal(userMoney.getStr("money"));
        if ((money.compareTo(myMoney)) > 0){
            return Result.isFail().msg("余额不足");
        }
        //不能转负数和等于0的金额
        if (money.compareTo(BigDecimal.valueOf(0)) <= 0){
            return Result.isFail().msg("金额输入有误");
        }
        //检验收款账号是否存在
        User inUser = userService.getUserByTel(tel,MsgConstants.IS_NO,MsgConstants.IS_YES,user.getPlatformId());
        if (null == inUser){
            return Result.isFail().msg(MsgConstants.NO_USER_EXITS);
        }
        //不能转给自己
        if (user.getId().equals(inUser.getId())){
            return Result.isFail().msg(MsgConstants.NO_USER_EXITS);
        }
        //获取今日转账信息
        JSONObject todayTransferMessage = moneyService.getTodayTransferTimes(user.getId());
        //今日转账次数
        int todayTransferTimes = todayTransferMessage.getInt("transferTimes");
        //系统设置的转账次数
        int transferTimesLimit = date.getInteger("transferTimesLimit");
        if (todayTransferTimes >= transferTimesLimit){
            return Result.isFail().msg("每天转账次数为："+transferTimesLimit+"次");
        }
        //系统设置的每日转账金额上限
        BigDecimal transferTop = date.getBigDecimal("transferTop");
        //今日转账总额
        BigDecimal todayTransferTotal = new BigDecimal(todayTransferMessage.getStr("moneyTotal"));
        //今日已转的加上现在要转的
        BigDecimal lastTotal = todayTransferTotal.add(money);
        if (transferTop.compareTo(lastTotal) < 0){
            return Result.isFail().msg("每天转账总额上限为："+transferTop);
        }
        //手续费率 , %形式
        JSONObject feeDate = (JSONObject) transferInfo.get("feeDate");
        String fee = feeDate.getStr("handFee");
        //手续费率，除以100后
        BigDecimal handFee = BigDecimal.valueOf(Double.valueOf(fee)).divide(BigDecimal.valueOf(100));
        //手续费
        BigDecimal feeMoney = (money.multiply(handFee)).setScale(0, BigDecimal.ROUND_UP);
        //到账金额方式： 0实际到账金额=转账金额-手续费 1实际到账金额=转账金额
        int moneyMethod = date.getInteger("method");
        if (1 == moneyMethod){
            if (((money.add(feeMoney)).compareTo(myMoney)) > 0){
                return Result.isFail().msg("余额不够,手续费为"+feeMoney);
            }
        }
        boolean isAdd = moneyService.transfer(user.getId(),inUser.getId(),user.getPlatformId(),money,MsgConstants.MONEY_TRANSFER_REC,user.getTel(),tel,MsgConstants.OPERATOR_TRANSFER,feeMoney,moneyMethod);
        if (isAdd){
            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
        }
        return Result.isFail().msg("余额不足");
    }


    /**
     * 获取充值页面的提示信息以及系统收款账号
     * @param request
     * @return
     */
    @RequestMapping("dx-api/money/getRechargeInfo")
    public Result getRechargeInfo (HttpServletRequest request) {
        User user =systemUtil.getPlatformIdAndUserId(request);
        JSONObject data = new JSONObject();
        //找出充值的提示信息
        JSONObject rechargeInfo = sysPlatformInfoService.getSystemInfo(user.getPlatformId(),InfoConstants.REDIS_RECHARGE_CONF,InfoConstants.RECHARGE_CONF);
        com.alibaba.fastjson.JSONObject jsonDate = com.alibaba.fastjson.JSONObject.parseObject(rechargeInfo.getStr("feeDate"));
        //检测是否有开启提现个的功能
        if (MsgConstants.IS_NO == jsonDate.getInteger("rechargeall")){
            data.put("isRecharge",MsgConstants.IS_NO );
            return Result.isFail().msg("充值功能暂时关闭").data(data);
        }
        JSONObject bankData = new JSONObject();
        //查找系统收款银行卡的key
        String sysKey = InfoConstants.BANK_CONF+":"+user.getPlatformId();
        //当前用户对应的银行卡的key
        String myKey = InfoConstants.BANK_CONF+":"+user.getPlatformId()+":"+user.getId();
        //看下当前用户是否有对应的系统收款银行卡
        if (redisService.exists(myKey,InfoConstants.DB_PLATE)){
            JSONObject bankInfo = (JSONObject)redisService.get(myKey,InfoConstants.DB_PLATE);
            bankData.put("bankInfo",bankInfo);
        }else {
            List<SysBankRec> sysBankRecList;
            //去redis找系统收款账号列表
            if (redisService.exists(sysKey,InfoConstants.DB_PLATE)){
                sysBankRecList = com.alibaba.fastjson.JSONObject.parseArray(redisService.get(sysKey,InfoConstants.DB_PLATE).toString(), SysBankRec.class);
                //去数据库找系统收款账号列表
            }else {
                SysBankRec sysBankRec = new SysBankRec();
                sysBankRec.setPlatformId(user.getPlatformId());
                sysBankRec.setIsDel(MsgConstants.IS_NO);
                sysBankRecList = sysPlatformInfoService.selectSysBankRecList(sysBankRec);
            }
            if (sysBankRecList.size() == 0){
                bankData.put("bankInfo","");
            }else {
                //随机获取一张放入redis并返回
                int randomNum = OrderNumUtil.getOneRandomNum(sysBankRecList.size());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("bankName",sysBankRecList.get(randomNum-1).getBankName());
                jsonObject.put("bankNo",sysBankRecList.get(randomNum-1).getBankNo());
                jsonObject.put("openName",sysBankRecList.get(randomNum-1).getOpenName());
                redisService.set(myKey,jsonObject,Constants.SYS_BANK_TIMEOUT,InfoConstants.DB_PLATE);
                bankData.put("bankInfo",jsonObject);
            }
        }
        data.put("unpaidChargeId", "");
        MoneyUserRechange unpaidRecharge = moneyService.selectUnPayRechargeOrder(user.getPlatformId(), user.getId(), null);
        if (null != unpaidRecharge){
            data.put("unpaidChargeId", unpaidRecharge.getId());
        }
        data.put("msgData",rechargeInfo);
        data.put("bankInfo",bankData);
        data.put("isRecharge",MsgConstants.IS_YES );
        List<PayTypeDTO> list = iPayPlatformTypeService.searchPayTypeNames(user.getPlatformId());
        data.put("payType",list);
        //可支持付款的渠道列表
        List<JSONObject> sysBankTypeList = iSysBankTypeService.getSysBankTypeList(MsgConstants.IS_NO, MsgConstants.IS_NO, user.getPlatformId());
        data.put("bankTypeList",sysBankTypeList);
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(data);
    }


    /**
     * 获取提现页面的提示信息以及会员对应的收款账号
     * @param request
     * @return
     */
    @RequestMapping("dx-api/money/getCashInfo")
    public Result getCashInfo (HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        //找出提现的提示信息
        UserCashRule cashConf = sysPlatformInfoService.getCashConf(user.getPlatformId(), user.getRankId(), InfoConstants.REDIS_CASH_CONF, InfoConstants.CASH_CONF);
        JSONObject cashInfo = new JSONObject();
        //检测是否有开启提现个的功能
        if (null == cashConf.getCashall() || "".equals(cashConf.getCashall()) || !StringUtils.isNumeric(cashConf.getCashall())){
            cashInfo.put("isCash",MsgConstants.IS_NO );
            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(cashInfo);
        }
        if (MsgConstants.IS_NO  == Integer.valueOf(cashConf.getCashall())){
            cashInfo.put("isCash",MsgConstants.IS_NO );
            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(cashInfo);
        }
        Map<String, String> msgData = new HashMap<>();
        if (null != cashConf.getMessage()){
            String[] infoArray = cashConf.getMessage().split(",");
            for (int i = 0; i < infoArray.length; i++) {
                int num = i + 1;
                msgData.put("msg" + num, infoArray[i]);
            }
        }
        cashInfo.put("feeDate", cashConf);
        cashInfo.put("msgData", msgData);
        //有充值的会员，只需要看cashall是否开启
        cashInfo.put("isCash",MsgConstants.IS_YES );
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(cashInfo);
    }

    /**
     * 获取转账页面的提示信息
     * @param request
     * @return
     */
    @RequestMapping("dx-api/money/getTransferInfo")
    public Result getTransferInfo (HttpServletRequest request) {
        User user =systemUtil.getPlatformIdAndUserId(request);
        //找出转账的提示信息
        JSONObject transferInfo = sysPlatformInfoService.getSystemInfo(user.getPlatformId(),InfoConstants.REDIS_TRASFER_CONF,InfoConstants.TRASFER_CONF);
        if (transferInfo.containsKey("feeDate")){
            com.alibaba.fastjson.JSONObject feeDate = com.alibaba.fastjson.JSONObject.parseObject(transferInfo.getStr("feeDate"));
            //检测是否有开启转账的功能
            if (MsgConstants.IS_NO == feeDate.getInteger("transferall")){
                transferInfo.put("isTransfer",MsgConstants.IS_NO);
                return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(transferInfo);
            }
            //普通会员就要看general开关是否开启  0关1开
            JSONObject rankUserRec = userRankRecService.getRankUserRecById(user.getRankId(), null, user.getPlatformId(), MsgConstants.IS_NO);
            Date expireTime = user.getExpireTime();
            if (0 == rankUserRec.getInt("rank") || DateUtils.getNowDate().compareTo(expireTime) > 0){
                if (MsgConstants.IS_NO == feeDate.getInteger("general")){
                    transferInfo.put("isTransfer",MsgConstants.IS_NO);
                    return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(transferInfo);
                }
            }
        }
        //有充值的会员，只需要看transferall是否开启
        transferInfo.put("isTransfer",MsgConstants.IS_YES);
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(transferInfo);
    }

    /**
     * 获取支付页面的提示信息
     * @param request
     * @return
     */
    @RequestMapping("dx-api/money/getPayInfo")
    public Result getPayInfo (HttpServletRequest request) {
        User user =systemUtil.getPlatformIdAndUserId(request);
        //找出支付的提示信息
        JSONObject transferInfo = sysPlatformInfoService.getSystemInfo(user.getPlatformId(),InfoConstants.REDIS_PAY_CONF,InfoConstants.PAY_CONF);
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(transferInfo);
    }

    /**
     * 获取充值记录详情
     * @param id:记录id
     * @return
     */
    @RequestMapping("dx-api/money/getChargeDetail")
    public Result getChargeDetail (@RequestParam("id")String id) {
        JSONObject moneyUserRechangeDetail = moneyService.getMoneyUserRechangeDetail(id);
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(moneyUserRechangeDetail);
    }


    /**
     * 余额购买
     * @param mealId：套餐id
     * @param request
     * @return
     */
    @RequestMapping("dx-api/money/walletBuy")
    public Result walletBuy (@RequestParam("mealId") String mealId, HttpServletRequest request) {
        User user =systemUtil.getPlatformIdAndUserId(request);
        JSONObject userMoney = moneyService.getUserMoney(user.getId());
        //将钱转成BigDecimal
        BigDecimal myMoney = new BigDecimal(userMoney.getStr("money"));
        //拿到套餐
        UserRankBuyRec userRankBuyRec = userRankRecService.selectUserRankBuyRecById(mealId);
        //金额校验，钱包金额要大于等套餐金额才行
        if (userRankBuyRec.getMoney().compareTo(myMoney) > 0){
            return Result.isFail().msg("余额不足");
        }
        User newUser = moneyService.buyMeal(userRankBuyRec, user,MsgConstants.MEAL_WALLET_BUY);
        if (null != newUser){
            //更新redis的user信息
            String userKey = Constants.DB_USER + user.getPlatformId() + user.getAccount();
            redisService.set(userKey, newUser, Constants.DB_USER);
            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
        }
        return Result.isFail().msg(MsgConstants.OPERATOR_FAIL);
    }

    /**
     * 获取三个分销今日收益
     *
     * @return
     */
    @RequestMapping("dx-api/money/getTodayProfit")
    public Result getTodayProfit (HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        List<JSONObject> todayProfitList = moneyService.getTodayProfit(user.getId());
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(todayProfitList);
    }

    /**
     * 检测当前是否可以购买套餐
     *
     * @return
     */
    @RequestMapping("dx-api/money/checkCanBuy")
    public Result checkCanBuy (@RequestParam("mealId") String mealId,HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        Map map = moneyService.checkCanBuy(user.getId(), user.getPlatformId(), mealId);
        if (map.containsKey("0")){
            return Result.isFail().msg(map.get("0").toString());
        }
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);

    }

    /**
     * 资金明细记录查询参数列表
     * @return
     */
    @RequestMapping("dx-api/money/recordType")
    public Result getRecordType () {
        List<DictDataListDTO> list = dictDataService.getQueryParamList(MsgConstants.MONEY_RECORD_DICT_TYPE);
        return Result.isOk().data(list);
    }

}
