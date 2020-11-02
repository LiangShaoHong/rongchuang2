package com.ruoyi.order.service;

import com.ruoyi.common.Result;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.utils.ResultDto;
import com.ruoyi.order.domain.FrenchCurrencyOrder;
import com.ruoyi.order.domain.Profit;
import com.ruoyi.user.domain.RcUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 法币业务接口
 *
 * @author ruoyi
 * @date 2020-10-22
 */
public interface LegalCurrencyService {
    /**
     * 查询个人信息接口
     *
     * @param user
     * @return
     */
    public Result getFbPerInformation(RcUser user);

    /**
     * 查询我的订单列表接口
     *
     * @param user
     * @return
     */
    public Result getFbMyOrderList(RcUser user, Integer pageNum, Integer pageSize);

    /**
     * 查询自动抢单状态接口
     *
     * @param user
     * @return
     */
    public Result getFbAutomaticOrder(HttpServletRequest request, RcUser user);

    /**
     * 改变自动抢单状态接口
     *
     * @param user
     * @return
     */
    public Result editFbAutomaticOrder(HttpServletRequest request, RcUser user, Boolean automatic);

    /**
     * 查询可选订单列表接口
     *
     * @param user
     * @return
     */
    public Result getFbOptionalOrder(RcUser user, Integer pageNum, Integer pageSize);

    /**
     * 查询历史记录接口
     *
     * @param user
     * @return
     */
    public Result getFbHistorical(RcUser user, Integer pageNum, Integer pageSize);

    /**
     * 查询历史记录详情接口
     *
     * @param user
     * @return
     */
    public Result getFbDetails(RcUser user, String id);

    /**
     * 确定付款接口
     *
     * @param user
     * @return
     */
    public Result fbConfirm_a(RcUser user, String id, String paymentImg);

    /**
     * 确定收款接口
     *
     * @param user
     * @return
     */
    public Result fbConfirm(RcUser user, String id);

    /**
     * 抢订单接口
     *
     * @param user
     * @return
     */
    public Result robFbOrder(RcUser user, String id);
}
