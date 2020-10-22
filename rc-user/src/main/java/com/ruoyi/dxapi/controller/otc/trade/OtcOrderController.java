package com.ruoyi.dxapi.controller.otc.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.MsgConstants;
import com.ruoyi.common.dto.PageDTO;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.dx.domain.OtcOrder;
import com.ruoyi.dx.domain.OtcOrderDetail;
import com.ruoyi.dx.domain.SysBankRec;
import com.ruoyi.dx.domain.User;
import com.ruoyi.dx.dto.usdt.order.OtcUserBuyOrderListDTO;
import com.ruoyi.dx.dto.usdt.order.*;
import com.ruoyi.dx.mapper.OtcOrderDetailMapper;
import com.ruoyi.dx.service.IOtcOrderDetailService;
import com.ruoyi.dx.service.ISysBankRecService;
import com.ruoyi.dxapi.common.Result;
import com.ruoyi.dxapi.common.SystemUtil;
import com.ruoyi.dxservice.service.OtcOrderDetailService;
import com.ruoyi.dxservice.service.OtcOrderService;
import com.ruoyi.dxservice.service.OtcSysConfService;
import com.ruoyi.dxservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单
 */
@RestController("dxOtcOrderController")
public class OtcOrderController {

    private static final Logger log = LoggerFactory.getLogger(OtcOrderController.class);

    @Resource
    private SystemUtil systemUtil;
    @Resource
    private OtcOrderService otcOrderService;
    @Resource
    private OtcOrderDetailService otcOrderDetailService;
    @Autowired
    private ISysBankRecService iSysBankRecService;
    @Autowired
    private OtcOrderDetailMapper otcOrderDetailMapper;
    @Resource
    private OtcSysConfService otcSysConfService;

    @Autowired
    private IOtcOrderDetailService iOtcOrderDetailService;


    /**
     * 寄售委托列表接口
     * @param request
     * @param pageNumber
     * @param limit
     * @return
     */
    @PostMapping("/dx-api/otc/saleOrderList")
    public Result getSaleOrderList(HttpServletRequest request,
                                   @RequestParam(value = "pageNumber") Integer pageNumber,
                                   @RequestParam(value = "limit") Integer limit) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        PageDTO dto = new PageDTO(pageNumber, limit);
        List<OtcUserSaleOrderListDTO> list = otcOrderService.getSaleOrderList(dto, user.getPlatformId(), user.getId(), MsgConstants.OtcOrderStatus.PROCESSING.getKey());
        return Result.isOk().data(list);
    }

    /**
     * 查找购买寄售列表(只找未领取的)
     * @param pageNumber 页数
     * @param limit 每页大小
     * @param startMoney 起始金额
     * @param endMoney 结束金额
     * @param startEarnings 起始收益
     * @param endEarnings 结束收益
     * @param request
     * @return
     */
    @RequestMapping("dx-api/otc/buyOrderList")
    public Result buyOrderList (@RequestParam("pageNumber") Integer pageNumber,
                                @RequestParam("limit") Integer limit,
                                @RequestParam(value = "startMoney",required = false)BigDecimal startMoney,
                                @RequestParam(value = "endMoney",required = false)BigDecimal endMoney,
                                @RequestParam(value = "startEarnings",required = false)BigDecimal startEarnings,
                                @RequestParam(value = "endEarnings",required = false)BigDecimal endEarnings,
                                @RequestParam(value = "offset", required = false) Integer offset,
                                HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        if (null != offset && null != limit && offset.compareTo(limit) >= 0){
            return Result.isFail("任务列表获取失败");
        }
        PageDTO dto = null;
        if (null != offset){
            dto = new PageDTO(pageNumber, pageNumber, offset);
        } else {
            dto = new PageDTO(pageNumber, limit);
        }
        List<OtcUserBuyOrderListDTO> buyOrderList = otcOrderService.getBuyOrderList(dto, user.getPlatformId(), startMoney, endMoney, startEarnings, endEarnings,user.getId());
        return Result.isOk().msg("查询成功").data(buyOrderList);
    }

    /**
     * 领取购买寄售
     * @param id 订单 orderDetail的id
     * @param request
     * @return
     */
    @RequestMapping("dx-api/otc/doBuyOrder")
    public Result doBuyOrder (@RequestParam("detailId") String id, HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        //领取的话：检测该单的user_id是否有值，有的话表示被人领了，就返回。
        OtcOrderDetail otcOrderDetail = otcOrderDetailService.selectOtcOrderDetailById(id);
        if (null == otcOrderDetail){
            log.info("购买寄售的交易订单详情缺失,查找的id为："+id);
            return Result.isOk().msg(MsgConstants.DATE_NEVER_INIT);
        }

        if(null == otcOrderDetail.getOrderUserId() || otcOrderDetail.getOrderUserId().equals(user.getId())){
            throw new BusinessException("不能领取自己提现的任务");
        }

        if (StringUtils.isNotEmpty(otcOrderDetail.getUserId())){
            return Result.isOk().msg("该单已被领取");
        }
        //领取
        boolean b = otcOrderDetailService.doBuyOrder(user.getPlatformId(), id, user.getId());
        if (b){
            return Result.isOk().msg("提交成功");
        }
        return Result.isFail().msg("提交失败");
    }

    /**
     * 寄售委托查看订单接口
     * @param request
     * @param id
     * @return
     */
    @PostMapping("/dx-api/otc/saleOrderInfo")
    public Result getSaleOrderInfo(HttpServletRequest request, @RequestParam(value = "id") String id) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        OtcUserSaleOrderInfoDTO saleOrderInfo = otcOrderService.getSaleOrderInfo(user.getPlatformId(), id);
        return Result.isOk().data(saleOrderInfo);
    }

    /**
     * 寄售委托查看详情接口
     * @param request
     * @param id
     * @return
     */
    @PostMapping("/dx-api/otc/saleOrderDetails")
    public Result getSaleOrderDetails(HttpServletRequest request, @RequestParam(value = "id") String id) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        OtcUserSaleOrderDetailInfoDTO saleOrderDetail = otcOrderDetailService.getSaleOrderDetail(user.getPlatformId(), id);
        return Result.isOk().data(saleOrderDetail);
    }

    /**
     * 客商委托出售USDT接口
     * @param request
     * @param money
     * @return
     */
    @PostMapping("/dx-api/otc/saveSaleOrder")
    public Result saveSaleOrder(HttpServletRequest request, @RequestParam("money") BigDecimal money) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        try {
            otcOrderService.saveSaleOrder(user, money);
        } catch (Exception e){
            if (e instanceof BusinessException){
                return Result.isFail(e.getMessage());
            } else {
                log.error("otc寄售订单保存失败，异常信息：{}", e);
                return Result.isFail("委托失败");
            }
        }
        return Result.isOk().msg("委托成功");
    }

    /**
     * 我的寄售委托列表接口
     * @param request
     * @param pageNumber
     * @param limit
     * @param status
     * @return
     */
    @PostMapping("/dx-api/otc/mySaleOrderList")
    public Result mySaleOrderList(HttpServletRequest request,
                                  @RequestParam(value = "pageNumber") Integer pageNumber,
                                  @RequestParam(value = "limit") Integer limit,
                                  @RequestParam(value = "status", required = false) Integer status) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        PageDTO dto = new PageDTO(pageNumber, limit);
        List<OtcUserSaleOrderListDTO> list = otcOrderService.getSaleOrderList(dto, user.getPlatformId(), user.getId(), status);
        return Result.isOk().data(list);
    }

    /**
     * 查看我的委托寄售详情接口
     * @param request
     * @param id
     * @return
     */
    @PostMapping("/dx-api/otc/mySaleOrderDetails")
    public Result mySaleOrderDetails(HttpServletRequest request, @RequestParam("id") String id) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        OtcUserSaleOrderInfoDTO saleOrderInfo = otcOrderService.getSaleOrderInfo(user.getPlatformId(), id);
        return Result.isOk().data(saleOrderInfo);
    }

    /**
     * 我的购买寄售列表接口
     * @param request
     * @param pageNumber
     * @param limit
     * @param status
     * @return
     */
    @PostMapping("/dx-api/otc/myBuyOrderDetailList")
    public Result myBuyOrderDetailList(HttpServletRequest request,
                                       @RequestParam(value = "pageNumber") Integer pageNumber,
                                       @RequestParam(value = "limit") Integer limit,
                                       @RequestParam(value = "status", required = false) Integer status) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        PageDTO dto = new PageDTO(pageNumber, limit);
        List<OtcUserBuyOrderDetailListDTO> list = otcOrderDetailService.getBuyOrderDetailList(dto, user.getPlatformId(), user.getId(), status);
        return Result.isOk().data(list);
    }

    /**
     * 取消购买寄售订单接口
     * @param request
     * @param id
     * @return
     */
    @PostMapping("/dx-api/otc/cancelBuyOrder")
    public Result cancelBuyOrder(HttpServletRequest request, @RequestParam(value = "id") String id) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        try {
            otcOrderDetailService.cancelBuyOrder(user.getPlatformId(), id);
        } catch (Exception e) {
            log.error("otc寄售订单保存失败，异常信息：{}", e);
            return Result.isFail("取消失败");
        }
        return Result.isOk().msg("取消成功");
    }

    /**
     * 购买寄售支付接口
     * @param request
     * @param id
     * @param payType 支付类型
     * @param payImg 付款截图
     * @param receiveInfo
     * @return
     */
    @PostMapping("/dx-api/otc/doPay")
    public Result doPayBuyOrder(HttpServletRequest request,
                                @RequestParam(value = "id") String id,
                                @RequestParam(value = "payType") Integer payType,
                                @RequestParam(value = "payImg") String payImg,
                                @RequestParam(value = "receiveInfo") String receiveInfo) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        try {
            otcOrderDetailService.submitBuyOrderPayInfo(user.getPlatformId(), id, payType, payImg, receiveInfo);
        } catch (Exception e) {
            if (e instanceof BusinessException){
                return Result.isFail(e.getMessage());
            } else {
                log.error("购买寄售订单支付提交失败，异常信息：{}", e);
                return Result.isFail("提交失败");
            }
        }
        return Result.isOk().msg("提交成功");
    }

    /**
     * 查看购买寄售详情接口
     * @param request
     * @param id
     * @return
     */
    @PostMapping("/dx-api/otc/myBuyOrderDetails")
    public Result myBuyOrderDetails(HttpServletRequest request, @RequestParam("id") String id) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        OtcUserBuyOrderDetailInfoDTO info = otcOrderDetailService.getBuyOrderDetailInfo(user.getPlatformId(), id);
        return Result.isOk().data(info);
    }

    /**
     * 我的寄售委托列表接口
     * @param request
     * @param pageNumber
     * @param limit
     * @param status
     * @return
     */
    @PostMapping("/dx-api/otc/mySaleOrderDetailList")
    public Result mySaleOrderDetailList(HttpServletRequest request,
                                        @RequestParam(value = "pageNumber") Integer pageNumber,
                                        @RequestParam(value = "limit") Integer limit,
                                        @RequestParam(value = "status", required = false) Integer status) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        PageDTO dto = new PageDTO(pageNumber, limit);
        List<OtcUserSaleOrderDetailListDTO> list = otcOrderDetailService.getSaleOrderDetailList(dto, user.getPlatformId(), user.getId(), status);
        return Result.isOk().data(list);
    }

    /**
     * 订单申诉接口
     * @param request
     * @param id
     * @param reason
     * @return
     */
    @PostMapping("/dx-api/otc/orderAppeal")
    public Result orderAppeal(HttpServletRequest request,
                              @RequestParam("id") String id,
                              @RequestParam("reason") String reason) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        try {
            otcOrderDetailService.appealSaleOrder(user.getPlatformId(), id, reason);
        } catch (Exception e) {
            log.error("取消购买寄售订单失败，异常信息：{}", e);
            return Result.isFail("申诉失败");
        }
        return Result.isOk().msg("申诉成功");
    }

    /**
     * 会员确认收到提现金额
     * @param request
     * @param id 提现订单id
     * @return
     */
    @PostMapping("/dx-api/otc/sureGetCash")
    public Result sureGetCash(HttpServletRequest request, @RequestParam("id") String id) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        //会员确认后，系统给客商加usdt，提现订单和otc的交易订单的状态改变。
        Integer result = iOtcOrderDetailService.sureGetCash(user,id,MsgConstants.OtcOrderDetailStatus.COMPLETED.getKey(),MsgConstants.OtcOrderDetailStatus.PAID.getKey());
        if(result > 0){
            return Result.isOk().msg("操作成功");
        }
        return Result.isFail("操作失败");
    }

    /**
     * 客商确定充值已收到款
     * @param request
     * @param id otcOrderDetails 的id
     * @return
     */
    @PostMapping("/dx-api/otc/traderSureGetMoney")
    public Result traderSureGetMoney(HttpServletRequest request, @RequestParam("id") String id) {
        //id = moneyCashInfo
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        //客商确认后，系统给客商减usdt，给会员加余额，充值订单和otc的交易订单的状态改变。
        Integer result = iOtcOrderDetailService.traderSureGetMoney(user,id,MsgConstants.OtcOrderDetailStatus.COMPLETED.getKey(),MsgConstants.OtcOrderDetailStatus.PAID.getKey());
        if(result > 0){
            return Result.isOk().msg("操作成功");
        }
        return Result.isFail("操作失败");
    }

    /**
     * 获取当前用户得收款信息
     * @param request
     * @return
     */
    @PostMapping("/dx-api/otc/getUserPayMsg")
    public Result getUserPayMsg(HttpServletRequest request,@RequestParam("id")String id) {
        //id = moneyCashInfo
        User user = systemUtil.getPlatformIdAndUserId(request);
        //会员确认后，系统给客商加usdt，提现订单和otc的交易订单的状态改变。
        //根据子单的id获取用户的id
        String userId = otcOrderDetailMapper.selectOtcOrderDetailById(id).getUserId();
        return Result.isOk().data(iSysBankRecService.getUserPayType(user.getPlatformId(),userId));
    }

    /**
     * 会员走otc充值
     * @param request
     * @param money 充值金额
     * @param payType 充值支付方式
     * @return
     */
    @PostMapping("/dx-api/otc/otcPay")
    public Result otcPay(HttpServletRequest request, @RequestParam("money") BigDecimal money,@RequestParam("payType") int payType) {
        if(money.compareTo(BigDecimal.ZERO) <= 0){
            return Result.isFail().msg("金额有误");
        }
        User user = systemUtil.getPlatformIdAndUserId(request);
        //会员提交充值金额，然后去匹配客商的委托。匹配到就生成一个detail单子，跳转到另一个页面
        JSONObject jsonObject = otcOrderDetailService.otcPay(money, user,payType);
        if(null != jsonObject){
            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(jsonObject);
        }
        return Result.isFail().msg("请重试");
    }

    /**
     * 检测是否存在未付款的otc的充值单
     * @param request
     * @return
     */
    @PostMapping("/dx-api/otc/existOtcOrderDetail")
    public Result existOtcOrderDetail(HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        OtcOrderDetail otcOrderDetailQuery = new OtcOrderDetail();
        otcOrderDetailQuery.setUserId(user.getId());
        otcOrderDetailQuery.setStatus(MsgConstants.OtcOrderDetailStatus.PENDING_PAY.getKey());
        otcOrderDetailQuery.setPlatformId(user.getPlatformId());
        List<OtcOrderDetail> otcOrderDetailList = otcOrderDetailService.selectOtcOrderDetailList(otcOrderDetailQuery);
        //表示有未支付的充值
        if (0 != otcOrderDetailList.size()){
            OtcOrderDetail otcOrderDetail = otcOrderDetailList.get(0);
            OtcOrder otcOrder = otcOrderService.selectOtcOrderById(otcOrderDetail.getOrderId());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("money",otcOrderDetail.getOrderMoney());
            List<OtcOrderDetailSupportPayTypeDTO> userPayTypeList = JSON.parseArray(otcOrder.getSupportPayType(), OtcOrderDetailSupportPayTypeDTO.class);
            jsonObject.put("payInfo",userPayTypeList);
            jsonObject.put("id",otcOrderDetail.getId());
            return Result.isOk().data(jsonObject);
        }
        return Result.isOk().msg("未存在尚未支付的订单").data("").status(false);
    }

    /**
     * 会员提交确认支付
     * @param request
     * @param id OtcOrderDetail的id
     * @return
     */
    @PostMapping("/dx-api/otc/submitPay")
    public Result submitPay(HttpServletRequest request, @RequestParam("id") String id, @RequestParam("payImg") String payImg, @RequestParam("payType") Integer payType,@RequestParam("payInfo")String payInfo) {
        //提交支付后，就修改otc订单状态，生成会员充值订单.
        User user = systemUtil.getPlatformIdAndUserId(request);
        OtcOrderDetailSupportPayTypeDTO otcOrderDetailSupportPayTypeDTO = JSON.parseObject(payInfo, OtcOrderDetailSupportPayTypeDTO.class);
        boolean b = otcOrderDetailService.submitPay(id, payImg,payType ,user,otcOrderDetailSupportPayTypeDTO);
        if (b){
            return Result.isOk().msg("提交成功");
        }
        return Result.isFail().msg("提交失败");
    }

    /**
     * 会员取消otc支付订单
     * @param request
     * @param id OtcOrderDetail的id
     * @return
     */
    @PostMapping("/dx-api/otc/cancelOtcPay")
    public Result cancelOtcPay(HttpServletRequest request, @RequestParam("id") String id) {
        boolean b = otcOrderDetailService.cancelOtcPay(id);
        if (b){
            return Result.isOk().msg("取消成功");
        }
        return Result.isFail().msg("取消失败，请重试");
    }

    /**
     * 客商取消委托订单
     * @param request
     * @param id otcOrder 的id
     * @return
     */
    @PostMapping("/dx-api/otc/cancelSaleOrder")
    public Result cancelSaleOrder(HttpServletRequest request, @RequestParam("id") String id) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        boolean b = otcOrderService.cancelSaleOrder(id, user.getPlatformId(),user);
        if(b){
            return Result.isOk().msg("取消成功");
        }
        return Result.isFail("取消失败");
    }
}
