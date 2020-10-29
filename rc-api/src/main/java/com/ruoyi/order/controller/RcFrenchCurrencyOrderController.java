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
import com.ruoyi.order.domain.RcFrenchCurrencyOrder;
import com.ruoyi.order.service.IRcFrenchCurrencyOrderService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 法币订单Controller
 * 
 * @author ruoyi
 * @date 2020-10-29
 */
@Controller
@RequestMapping("/order/RcFrenchCurrencyOrder")
public class RcFrenchCurrencyOrderController extends BaseController
{
    private String prefix = "order/RcFrenchCurrencyOrder";

    @Autowired
    private IRcFrenchCurrencyOrderService rcFrenchCurrencyOrderService;

    @RequiresPermissions("order:RcFrenchCurrencyOrder:view")
    @GetMapping()
    public String RcFrenchCurrencyOrder()
    {
        return prefix + "/RcFrenchCurrencyOrder";
    }

    /**
     * 查询法币订单列表
     */
    @RequiresPermissions("order:RcFrenchCurrencyOrder:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RcFrenchCurrencyOrder rcFrenchCurrencyOrder)
    {
        startPage();
        List<RcFrenchCurrencyOrder> list = rcFrenchCurrencyOrderService.selectRcFrenchCurrencyOrderList(rcFrenchCurrencyOrder);
        return getDataTable(list);
    }

    /**
     * 导出法币订单列表
     */
    @RequiresPermissions("order:RcFrenchCurrencyOrder:export")
    @Log(title = "法币订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RcFrenchCurrencyOrder rcFrenchCurrencyOrder)
    {
        List<RcFrenchCurrencyOrder> list = rcFrenchCurrencyOrderService.selectRcFrenchCurrencyOrderList(rcFrenchCurrencyOrder);
        ExcelUtil<RcFrenchCurrencyOrder> util = new ExcelUtil<RcFrenchCurrencyOrder>(RcFrenchCurrencyOrder.class);
        return util.exportExcel(list, "RcFrenchCurrencyOrder");
    }

    /**
     * 新增法币订单
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存法币订单
     */
    @RequiresPermissions("order:RcFrenchCurrencyOrder:add")
    @Log(title = "法币订单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RcFrenchCurrencyOrder rcFrenchCurrencyOrder)
    {
        return toAjax(rcFrenchCurrencyOrderService.insertRcFrenchCurrencyOrder(rcFrenchCurrencyOrder));
    }

    /**
     * 修改法币订单
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        RcFrenchCurrencyOrder rcFrenchCurrencyOrder = rcFrenchCurrencyOrderService.selectRcFrenchCurrencyOrderById(id);
        mmap.put("rcFrenchCurrencyOrder", rcFrenchCurrencyOrder);
        return prefix + "/edit";
    }

    /**
     * 修改保存法币订单
     */
    @RequiresPermissions("order:RcFrenchCurrencyOrder:edit")
    @Log(title = "法币订单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RcFrenchCurrencyOrder rcFrenchCurrencyOrder)
    {
        return toAjax(rcFrenchCurrencyOrderService.updateRcFrenchCurrencyOrder(rcFrenchCurrencyOrder));
    }

    /**
     * 删除法币订单
     */
    @RequiresPermissions("order:RcFrenchCurrencyOrder:remove")
    @Log(title = "法币订单", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(rcFrenchCurrencyOrderService.deleteRcFrenchCurrencyOrderByIds(ids));
    }
}
