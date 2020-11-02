package com.ruoyi.order.service;

import com.ruoyi.common.Result;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.utils.ResultDto;
import com.ruoyi.user.domain.RcUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

/**
 * 币币业务接口
 *
 * @author ruoyi
 * @date 2020-10-22
 */
@Api("币币业务接口")
public interface CurrencyService {

    /**
     * 查询个人信息接口
     *
     * @param user
     * @return
     */
    public Result getBbPerInformation(RcUser user);

    /**
     * 查询我的订单列表接口
     *
     * @param user
     * @return
     */
    public Result getBbMyOrderList(RcUser user, Integer pageNum, Integer pageSize);

    /**
     * 查询自动抢单状态接口
     *
     * @param user
     * @return
     */
    public Result getBbAutomaticOrder(HttpServletRequest request, RcUser user);

    /**
     * 改变自动抢单状态接口
     *
     * @param user
     * @return
     */
    public Result editBbAutomaticOrder(HttpServletRequest request,RcUser user, Boolean automatic);

    /**
     * 查询可选订单列表接口
     *
     * @param user
     * @return
     */
    public Result getBbOptionalOrder(RcUser user, Integer pageNum, Integer pageSize);

    /**
     * 查询历史记录接口
     *
     * @param user
     * @return
     */
    public Result getBbHistorical(RcUser user, Integer pageNum, Integer pageSize);

    /**
     * 查询历史记录详情接口
     *
     * @param user
     * @return
     */
    public Result getBbDetails(RcUser user, String id);

    /**
     * 抢订单接口
     *
     * @param user
     * @return
     */
    public Result robBbOrder(RcUser user, String id);
}
