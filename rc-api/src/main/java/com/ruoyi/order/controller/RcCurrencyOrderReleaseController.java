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
import com.ruoyi.order.domain.RcCurrencyOrderRelease;
import com.ruoyi.order.service.IRcCurrencyOrderReleaseService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 币币订单发布Controller
 * 
 * @author ruoyi
 * @date 2020-10-29
 */
@Controller
@RequestMapping("/order/RcCurrencyOrderRelease")
public class RcCurrencyOrderReleaseController extends BaseController
{
    private String prefix = "order/RcCurrencyOrderRelease";

    @Autowired
    private IRcCurrencyOrderReleaseService rcCurrencyOrderReleaseService;

    @RequiresPermissions("order:RcCurrencyOrderRelease:view")
    @GetMapping()
    public String RcCurrencyOrderRelease()
    {
        return prefix + "/RcCurrencyOrderRelease";
    }

    /**
     * 查询币币订单发布列表
     */
    @RequiresPermissions("order:RcCurrencyOrderRelease:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RcCurrencyOrderRelease rcCurrencyOrderRelease)
    {
        startPage();
        List<RcCurrencyOrderRelease> list = rcCurrencyOrderReleaseService.selectRcCurrencyOrderReleaseList(rcCurrencyOrderRelease);
        return getDataTable(list);
    }

    /**
     * 导出币币订单发布列表
     */
    @RequiresPermissions("order:RcCurrencyOrderRelease:export")
    @Log(title = "币币订单发布", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RcCurrencyOrderRelease rcCurrencyOrderRelease)
    {
        List<RcCurrencyOrderRelease> list = rcCurrencyOrderReleaseService.selectRcCurrencyOrderReleaseList(rcCurrencyOrderRelease);
        ExcelUtil<RcCurrencyOrderRelease> util = new ExcelUtil<RcCurrencyOrderRelease>(RcCurrencyOrderRelease.class);
        return util.exportExcel(list, "RcCurrencyOrderRelease");
    }

    /**
     * 新增币币订单发布
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存币币订单发布
     */
    @RequiresPermissions("order:RcCurrencyOrderRelease:add")
    @Log(title = "币币订单发布", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RcCurrencyOrderRelease rcCurrencyOrderRelease)
    {
        return toAjax(rcCurrencyOrderReleaseService.insertRcCurrencyOrderRelease(rcCurrencyOrderRelease));
    }

    /**
     * 修改币币订单发布
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        RcCurrencyOrderRelease rcCurrencyOrderRelease = rcCurrencyOrderReleaseService.selectRcCurrencyOrderReleaseById(id);
        mmap.put("rcCurrencyOrderRelease", rcCurrencyOrderRelease);
        return prefix + "/edit";
    }

    /**
     * 修改保存币币订单发布
     */
    @RequiresPermissions("order:RcCurrencyOrderRelease:edit")
    @Log(title = "币币订单发布", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RcCurrencyOrderRelease rcCurrencyOrderRelease)
    {
        return toAjax(rcCurrencyOrderReleaseService.updateRcCurrencyOrderRelease(rcCurrencyOrderRelease));
    }

    /**
     * 删除币币订单发布
     */
    @RequiresPermissions("order:RcCurrencyOrderRelease:remove")
    @Log(title = "币币订单发布", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(rcCurrencyOrderReleaseService.deleteRcCurrencyOrderReleaseByIds(ids));
    }
}
