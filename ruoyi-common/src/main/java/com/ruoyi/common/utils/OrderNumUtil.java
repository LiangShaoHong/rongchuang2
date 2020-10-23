package com.ruoyi.common.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderNumUtil {

    /**
     * 充值订单类别头
     */
    private static final String RECHAGE_ORDER_CODE = "R";
    /**
     * 任务订单类别头
     */
    private static final String TASK_ORDER_CODE = "T";
    /**
     * 提现订单类别头
     */
    private static final String CASH_ORDER_CODE = "C";
    /**
     * usdt兑换订单类别头
     */
    private static final String USDT_CONVERT_CODE = "UC";

    /**
     * usdt订单编号
     */
    private static final String USDT_ORDER_CODE = "UO";

    /**
     * usdt订单明细编号
     */
    private static final String USDT_ORDER_DETAIL_CODE = "UOD";
    /**
     * usdt提现订单类别头
     */
    private static final String USDT_WITHDRAW_CODE = "UCW";

    /**
     * 随即编码
     */

    private static final int[] r = new int[]{7, 9, 6, 2, 8, 1, 3, 0, 5, 4};
    /**
     * 用户id和随机数总长度
     */

    private static final int maxLength = 13;

    /**
     * 根据id进行加密+加随机数组成固定长度编码
     */
    private static String toCode(String userId) {
        String idStr = userId;
        StringBuilder idsbs = new StringBuilder();
        for (int i = idStr.length() - 1; i >= 0; i--) {
            idsbs.append(r[idStr.charAt(i) - '0']);
        }
        return idsbs.append(getRandom(maxLength - idStr.length())).toString();
    }

    /**
     * 生成时间戳
     */
    private static String getDateTime() {
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }

    /**
     * 生成固定长度随机码
     *
     * @param n 长度
     */

    private static long getRandom(long n) {
        long min = 1, max = 9;
        for (int i = 1; i < n; i++) {
            min *= 10;
            max *= 10;
        }
        long rangeLong = (((long) (new Random().nextDouble() * (max - min)))) + min;
        return rangeLong;
    }


    /**
     * 生成不带类别标头的编码
     *
     * @param userId
     */
    private static synchronized String getCode(String userId) {
        userId = userId == null ? "10000" : userId;
        return getDateTime() + toCode(userId);
    }


    /**
     * 生成充值订单单号编码(调用方法)
     * @param userId  网站中该用户唯一ID 防止重复
     */

    public static String getOrderCode(String userId) {
        return RECHAGE_ORDER_CODE + SnowflakeIdWorker.genIdStr();
    }


    /**
     * 生成任务订单单号编码(调用方法)
     * @param userId  网站中该用户唯一ID 防止重复
     */

    public static String getTaskCode(String userId) {
        return TASK_ORDER_CODE + SnowflakeIdWorker.genIdStr();
    }

    /**
     * 生成提现订单单号编码(调用方法)
     * @param userId  网站中该用户唯一ID 防止重复
     */

    public static String getCashCode(String userId) {
        return CASH_ORDER_CODE + SnowflakeIdWorker.genIdStr();
    }


    //产生大写字母和数字组成的随机数
    public static String getRandomNum(int length) {
        char[] letters = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P','Q', 'R', 'S', 'T', 'U', 'V',
                'W', 'X', 'Y', 'Z','0','1','2','3','4','5','6','7','8','9'};
        boolean[] flags = new boolean[letters.length];
        char[] chs = new char[length];
        for (int i = 0; i < chs.length; i++) {
            int index;
            do {
                index = (int) (Math.random() * (letters.length));
            } while (flags[index]);// 判断生成的字符是否重复
            chs[i] = letters[index];
            flags[index] = true;
        }
        return new String(chs);
    }

    /**
     * 产生指定长度内的一个随机数字
     * @param length 长度
     * @return
     */
    public static int getOneRandomNum(int length){
        Random random = new Random();
        int radom = random.nextInt(length)+1;
        return radom;
    }

    /**
     * 生成usdt兑换订单单号编码(调用方法)
     */

    public static String getUsdtConvertCode() {
        return USDT_CONVERT_CODE + SnowflakeIdWorker.genIdStr();
    }

    /**
     *
     * @return
     */
    public static String getUsdtOrderCode() {
        return USDT_ORDER_CODE + SnowflakeIdWorker.genIdStr();
    }

    /**
     *  usdt 提现订单单号
     * @return
     */
    public static String getUsdtWithdrawCode() {
        return USDT_WITHDRAW_CODE + SnowflakeIdWorker.genIdStr();
    }

    /**
     *
     * @return
     */
    public static String getUsdtOrderDetailCode() {
        return USDT_ORDER_DETAIL_CODE + SnowflakeIdWorker.genIdStr();
    }
}
