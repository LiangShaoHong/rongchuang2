package com.ruoyi.common.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回信息常量
 *
 * @author csm
 */
public class MsgConstants {

    public static final String LOGIN_FAIL = "登录失败";

    public static final String USER_NOT_FOUND = "用户不存在";

    public static final String USER_ALLREADY_EXITS = "账号已存在";

    public static final String USER_ALLREADY_FREEZE = "账号已被封号";

    public static final String SYSTEM_UP_RANK = "系统修改会员等级套餐";

    /**
     * 资金变化类型(资金记录表里面的type)：0转账1提现2提现手续费3充值4佣金5升级6佣金分销7升级分销8总后台操作11余额兑换usdt
     */
    public static final String MONEY_RECORD_DICT_TYPE = "cash_recod_type";
    public static final int MONEY_TRANSFER_REC = 0;
    public static final int MONEY_CASH_REC = 1;
    public static final int MONEY_CASH_FEE_REC = 2;
    public static final int MONEY_RECHARGE_REC = 3;
    public static final int MONEY_COMMISSION_REC = 4;
    public static final int MONEY_LEVEL_UP_REC = 5;
    public static final int MONEY_COMMISSION_PROFIT_REC = 6;
    public static final int MONEY_LEVEL_UP_PROFIT_REC = 7;
    public static final int MONEY_ADMIN_OPERATOR_REC = 8;
    public static final int MONEY_AGENT_COMMISSION_PROFIT_REC = 9;
    public static final int MONEY_AGENT_LEVEL_UP_PROFIT_REC = 10;
    public static final int MONEY_USDT_CONVERT = 11;

    /**
     * 玩家任务完成状态:0未开始，1待审核，2已完成，3已取消
     */
    public static final int NO_START = 0;
    public static final int WAIT_CHECK = 1;
    public static final int IS_FINISHED = 2;
    public static final int IS_CANCEL = 3;

    /**
     * 任务状态:0上架中，1下架中，2已过期，3已完成
     */
    public static final int TASK_ON_SHELF = 0;
    public static final int TASK_DOWN_SHELF = 1;
    public static final int TASK_OUT_DATE = 2;
    public static final int TASK_FINISH = 3;

    /**
     * 任务类别 0是普通任务  1是火币任务
     */
    public static final int TASK_COMMON= 0;
    public static final int TASK_FIRE = 1;

    /**
     * 登录成功信息
     */
    public static final String USER_LOGIN_OK = "登录成功！";

    /**
     * 登录失败信息
     */
    public static final String USER_LOGIN_FAIL = "账户或者密码错误！";

    public static final String INVITATION_UNUSER = "推荐人不存在";

    public static final String OPERATOR_SUCCESS ="操作成功！";
    public static final String OPERATOR_FAIL ="操作失败！";

    public static final String DATE_NEVER_INIT ="数据未初始化！";

    public static final String DATA_ERROR ="数据有误";

    public static final String OLD_PASSWORD_ERROR ="旧密码输入有误！";

    public static final String NO_BANK_CAR ="无收款账号！";

    public static final String NO_USER_EXITS ="账号输入有误！";

    public static final String NO_SEND_IN_SECOND ="10分钟内只能发送一次！";

    public static final String PLEASE_SEND_AGAIN ="请重新获取验证码！";

    public static final String VERIFICATION_ERROR ="验证码有误";
    /**
     * 默认字段字典值"否"
     */
    public static final int IS_NO = 0;
    /**
     * 默认字段字典值"是"
     */
    public static final int IS_YES = 1;

    /**
     * 登录成功信息
     */
    public static final String TOKEN_INVALID= "token失效！";

    public static final String USER_COMMON_RANK="普通会员";

    /**
     * 会员套餐持续时间 -1无限期 0过期
     */
    public static final int RANK_TIMES_NEVER= -1;
    public static final int RANK_TIMES_OUTDATE = 0;

    /**
     * 审核状态：0审核中1已同意2拒绝3已取消4otc充值
     */
    public static final int CHECK_STATUS_UNPAID = -1;
    public static final int CHECK_STATUS_CHECKING = 0;
    public static final int CHECK_STATUS_AGREE = 1;
    public static final int CHECK_STATUS_REFUSE = 2;
    public static final int CHECK_STATUS_CANCEL = 3;
    public static final int CHECK_STATUS_OTC = 4;

    /**
     * 充值类型 (1直接升级，2充值到余额）
     */
    public static final int RECHARGE_TYPE_LEVEL_UP = 1;
    public static final int RECHARGE_TYPE_TO_WALLET = 2;

    /**
     * 套餐购买类型 1余额购买2银行卡充值购买
     */
    public static final int MEAL_WALLET_BUY = 1;
    public static final int MEAL_RECHARGE_BUY = 2;

    /**
     * 金钱标识 1人民币
     */
    public static final int MONEY_CODE_RMB = 1;

    /**
     * 代理等级分销层数
     */
    public static final int USER_AGENT_FLOOR = 3;

    /**
     * 三级分销的类型:1任务2升级
     */
    public static final int THREE_PROFIT_TASK = 1;
    public static final int THREE_PROFIT_RANK = 2;

    /**
     * 套餐迭代操作类型：0不让买，用完为止；1折合成时间；2直接替换
     */
    public static final int NO_OPERATOR = 0;
    public static final int TRANSFER_TO_TIME = 1;
    public static final int TO_OVERRIDE = 2;

    /**
     * 升级的支付类型 1线下2线上
     */
    public static final int OFFLINE_PAY = 1;
    public static final int ONLINE_PAY = 2;

    /**
     * 用户资金操作类型  1转账2提现3充值余额4充值升级5任务三级分销6升级三级分销7余额购买升级8提现审核不通过9任务收入10usdt兑换
     */
    public static final int OPERATOR_TRANSFER = 1;
    public static final int OPERATOR_CASH = 2;
    public static final int OPERATOR_RECHARGE_TO_WALLET = 3;
    public static final int OPERATOR_RECHARGE_TO_LEVEL = 4;
    public static final int OPERATOR_THREE_PROFIT_TASK = 5;
    public static final int OPERATOR_THREE_PROFIT_LEVEL = 6;
    public static final int OPERATOR_WALLET_BUY_PROFIT_LEVEL = 7;
    public static final int OPERATOR_CASH_REFUSE = 8;
    public static final int OPERATOR_IMCOME_TASK = 9;
    public static final int OPERATOR_USDT = 10;

    /**
     * 用户充值的支付类型  1手工2客服3支付宝4微信
     */
    public static final int PAY_BY_HAND = 1;
    public static final int PAY_BY_CUSTOMER = 2;
    public static final int PAY_BY_ALIPAY = 3;
    public static final int PAY_BY_WECAHT = 4;

    /**
     * 队列消息延迟发送时间
     */
    public static final int QUEUE_DELAY_TIME = 5;

    /**
     *  银行卡类型
     */
    public static final int BANK_TYPE_BANKCARD = 1;
    public static final int BANK_TYPE_ALIPAY = 2;
    public static final int BANK_TYPE_WECHATPAY = 3;

    /**
     * 提现状态提现状态（0审核中，1已打款，2已完成，3已拒绝，4已取消，5(走otc时，客户端显示已打款)
     */
    public static final int WITHDRAWAL_STATUS_LOAD = 0;
    public static final int WITHDRAWAL_STATUS_REMIT = 1;
    public static final int WITHDRAWAL_STATUS_SUCCEED = 2;
    public static final int WITHDRAWAL_STATUS_REFUSE = 3;
    public static final int WITHDRAWAL_STATUS_CANCEL = 4;
    public static final int WITHDRAWAL_STATUS_IS_PAY = 5;

    /**
     * 提现金额类别 0 固定金额提现，1，整数倍提现
     */
    public static final int CASH_TYPE_MULTIPLE = 1;
    public static final int CASH_TYPE_IMMOBILIZATION = 0;

    /**
     * 支付平台支付类型
     */
    public static final String PAY_PLATFORM_ALIPAU = "alipay";
    public static final String PAY_PLATFORM_BANK = "bank";
    public static final String PAY_PLATFORM_WECHATPAY = "wechatpay";

    /**
     * 禁用启用状态  0 禁用，1启用
     */
    public static final int STATUS_YES = 1;
    public static final int STATUS_NO = 0;


    /**
     * usdt 充值订单的状态  （0待确认，1已完成，2已取消）
     */
    public static final int USDT_RECHARGE_MAKE_SURE = 0;
    public static final int USDT_RECHARGE_COMPLETE = 1;
    public static final int USDT_RECHARGE_CANCEL = 2;

    /**
     * 资金变动类型
     */
    public enum UsdtVariation{
        RECHARGE(0,"平台审核用户成功充值"),
        BUY(1,"会员提现增加"),
        CONVERT(3,"用户使用余额兑换USDT"),
        USERRECHARGE(4,"用户充值余额，客商扣除USDT"),
        CONSIGNMENT(5,"客商委托出售"),
        SENDWITHDRAW(6,"客商提现USDT"),
        CANCELWITHDRAW(7,"平台拒绝客商提现USDT"),
        CANCELSALEORDER(8,"客商取消委托订单"),
        PLATFORM(9,"平台变动资金");
        private Integer key;
        private String val;

        UsdtVariation(Integer key, String val) {
            this.key = key;
            this.val = val;
        }

        public Integer getKey() {
            return key;
        }

        public String getVal() {
            return val;
        }
    }

    public static List usdtVariationToList() {
        List<Object> list = new ArrayList<>();
        for (UsdtVariation usdtVariation : UsdtVariation.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("val", usdtVariation.getVal());
            map.put("key", usdtVariation.getKey());
            list.add(map);
        }
        return list;

    }

    /**
     * usdt提现状态
     */
    public enum UsdtWithdraw{
        AWAIT(0,"待打款"),
        SUCCEED(1,"已完成"),
        CANCEL(2,"已取消");
        private Integer key;
        private String val;

        UsdtWithdraw(Integer key, String val) {
            this.key = key;
            this.val = val;
        }

        public Integer getKey() {
            return key;
        }

        public String getVal() {
            return val;
        }
    }

    /**
     * 币种标志
     */
    public enum MoneyCode{
        USDT(0,"USDT");
        private Integer key;
        private String val;

        MoneyCode(Integer key, String val) {
            this.key = key;
            this.val = val;
        }

        public Integer getKey() {
            return key;
        }

        public String getVal() {
            return val;
        }
    }

    /**
     * USDT类型
     */
    public enum UsdtType{
        ERC20(1,"ERC20"),
        TRC20(2,"TRC20"),
        OMNI(3,"OMNI");
        private Integer key;
        private String val;

        UsdtType(Integer key, String val) {
            this.key = key;
            this.val = val;
        }

        public Integer getKey() {
            return key;
        }

        public String getVal() {
            return val;
        }
    }

    public enum AgentCommissionType {
        TASK(1, "代理等级任务分销"),
        UPGRADE(2, "代理等级推荐会员升级分销");
        private Integer key;
        private String value;
        AgentCommissionType(Integer key, String value){
            this.key = key;
            this.value = value;
        }

        public Integer getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum OtcOrderType {
        BUY(1, "购买寄售"),
        SALE(2, "寄售委托");

        private Integer key;

        private String value;

        OtcOrderType(Integer key, String value){
            this.key = key;
            this.value = value;
        }

        public Integer getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum OtcOrderStatus {

        PROCESSING(1, "进行中"),
        OVER(2, "已结束");

        private Integer key;

        private String value;

        OtcOrderStatus(Integer key, String value){
            this.key = key;
            this.value = value;
        }

        public Integer getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum OtcOrderDetailStatus {

        PENDING_PAY(0, "待付款"),
        PAID(1, "已付款"),
        CANCELLED(2, "已取消"),
        COMPLETED(3, "已完成"),
        APPEALING(4, "申诉中"),
        APPEALING_COMPLETED(5, "申诉已完成");

        private Integer key;

        private String value;

        OtcOrderDetailStatus(Integer key, String value){
            this.key = key;
            this.value = value;
        }

        public Integer getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum UserTravellingTrader {

        COMMON(0, "普通用户"),
        TRAVELLING(1, "客商用户");

        private Integer key;

        private String value;

        UserTravellingTrader(Integer key, String value){
            this.key = key;
            this.value = value;
        }

        public Integer getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum OrderDetailsBizType {
        WITHDRAW(1, "提现"),
        RECHARGE(2, "充值");

        private Integer key;

        private String value;

        OrderDetailsBizType(Integer key, String value){
            this.key = key;
            this.value = value;
        }

        public Integer getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

}
