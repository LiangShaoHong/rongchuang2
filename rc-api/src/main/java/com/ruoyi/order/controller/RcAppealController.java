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
import com.ruoyi.order.domain.RcAppeal;
import com.ruoyi.order.service.IRcAppealService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单申诉Controller
 * 
 * @author ruoyi
 * @date 2020-11-03
 */
@Controller
@RequestMapping("/order/RcAppeal")
public class RcAppealController extends BaseController
{
    private String prefix = "order/RcAppeal";

    @Autowired
    private IRcAppealService rcAppealService;

    @RequiresPermissions("order:RcAppeal:view")
    @GetMapping()
    public String RcAppeal()
    {
        return prefix + "/RcAppeal";
    }

    /**
     * 查询订单申诉列表
     */
    @RequiresPermissions("order:RcAppeal:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RcAppeal rcAppeal)
    {
        startPage();
        List<RcAppeal> list = rcAppealService.selectRcAppealList(rcAppeal);
        return getDataTable(list);
    }

    /**
     * 导出订单申诉列表
     */
    @RequiresPermissions("order:RcAppeal:export")
    @Log(title = "订单申诉", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RcAppeal rcAppeal)
    {
        List<RcAppeal> list = rcAppealService.selectRcAppealList(rcAppeal);
        ExcelUtil<RcAppeal> util = new ExcelUtil<RcAppeal>(RcAppeal.class);
        return util.exportExcel(list, "RcAppeal");
    }

    /**
     * 新增订单申诉
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存订单申诉
     */
    @RequiresPermissions("order:RcAppeal:add")
    @Log(title = "订单申诉", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RcAppeal rcAppeal)
    {
        return toAjax(rcAppealService.insertRcAppeal(rcAppeal));
    }

    /**
     * 修改订单申诉
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        RcAppeal rcAppeal = rcAppealService.selectRcAppealById(id);
        mmap.put("rcAppeal", rcAppeal);
        return prefix + "/edit";
    }

    /**
     * 修改保存订单申诉
     */
    @RequiresPermissions("order:RcAppeal:edit")
    @Log(title = "订单申诉", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RcAppeal rcAppeal)
    {
        return toAjax(rcAppealService.updateRcAppeal(rcAppeal));
    }

    /**
     * 删除订单申诉
     */
    @RequiresPermissions("order:RcAppeal:remove")
    @Log(title = "订单申诉", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(rcAppealService.deleteRcAppealByIds(ids));
    }
}
