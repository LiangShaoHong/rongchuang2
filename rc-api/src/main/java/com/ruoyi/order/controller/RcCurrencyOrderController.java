package com.ruoyi.order.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.order.domain.RcCurrencyOrder;
import com.ruoyi.order.service.IRcCurrencyOrderService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 币币订单Controller
 * 
 * @author ruoyi
 * @date 2020-10-29
 */
@Controller
@RequestMapping("/order/RcCurrencyOrder")
public class RcCurrencyOrderController extends BaseController
{
    private String prefix = "order/RcCurrencyOrder";

    @Autowired
    private IRcCurrencyOrderService rcCurrencyOrderService;

    @RequiresPermissions("order:RcCurrencyOrder:view")
    @GetMapping()
    public String RcCurrencyOrder()
    {
        return prefix + "/RcCurrencyOrder";
    }

    /**
     * 查询币币订单列表
     */
    @RequiresPermissions("order:RcCurrencyOrder:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RcCurrencyOrder rcCurrencyOrder)
    {
        startPage();
        List<RcCurrencyOrder> list = rcCurrencyOrderService.selectRcCurrencyOrderList(rcCurrencyOrder);
        return getDataTable(list);
    }

    /**
     * 导出币币订单列表
     */
    @RequiresPermissions("order:RcCurrencyOrder:export")
    @Log(title = "币币订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RcCurrencyOrder rcCurrencyOrder)
    {
        List<RcCurrencyOrder> list = rcCurrencyOrderService.selectRcCurrencyOrderList(rcCurrencyOrder);
        ExcelUtil<RcCurrencyOrder> util = new ExcelUtil<RcCurrencyOrder>(RcCurrencyOrder.class);
        return util.exportExcel(list, "RcCurrencyOrder");
    }

    /**
     * 新增币币订单
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存币币订单
     */
    @RequiresPermissions("order:RcCurrencyOrder:add")
    @Log(title = "币币订单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RcCurrencyOrder rcCurrencyOrder)
    {
        return toAjax(rcCurrencyOrderService.insertRcCurrencyOrder(rcCurrencyOrder));
    }

    /**
     * 修改币币订单
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        RcCurrencyOrder rcCurrencyOrder = rcCurrencyOrderService.selectRcCurrencyOrderById(id);
        mmap.put("rcCurrencyOrder", rcCurrencyOrder);
        return prefix + "/edit";
    }

    /**
     * 修改保存币币订单
     */
    @RequiresPermissions("order:RcCurrencyOrder:edit")
    @Log(title = "币币订单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RcCurrencyOrder rcCurrencyOrder)
    {
        return toAjax(rcCurrencyOrderService.updateRcCurrencyOrder(rcCurrencyOrder));
    }

    /**
     * 删除币币订单
     */
    @RequiresPermissions("order:RcCurrencyOrder:remove")
    @Log(title = "币币订单", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(rcCurrencyOrderService.deleteRcCurrencyOrderByIds(ids));
    }
}
