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
import com.ruoyi.order.domain.RcFrenchCurrencyOrderRelease;
import com.ruoyi.order.service.IRcFrenchCurrencyOrderReleaseService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 法币订单发布Controller
 * 
 * @author ruoyi
 * @date 2020-10-21
 */
@Controller
@RequestMapping("/order/rcFrenchCurrencyOrderRelease")
public class RcFrenchCurrencyOrderReleaseController extends BaseController
{
    private String prefix = "order/rcFrenchCurrencyOrderRelease";

    @Autowired
    private IRcFrenchCurrencyOrderReleaseService rcFrenchCurrencyOrderReleaseService;

    @RequiresPermissions("order:rcFrenchCurrencyOrderRelease:view")
    @GetMapping()
    public String rcFrenchCurrencyOrderRelease()
    {
        return prefix + "/rcFrenchCurrencyOrderRelease";
    }

    /**
     * 查询法币订单发布列表
     */
    @RequiresPermissions("order:rcFrenchCurrencyOrderRelease:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RcFrenchCurrencyOrderRelease rcFrenchCurrencyOrderRelease)
    {
        startPage();
        List<RcFrenchCurrencyOrderRelease> list = rcFrenchCurrencyOrderReleaseService.selectRcFrenchCurrencyOrderReleaseList(rcFrenchCurrencyOrderRelease);
        return getDataTable(list);
    }

    /**
     * 导出法币订单发布列表
     */
    @RequiresPermissions("order:rcFrenchCurrencyOrderRelease:export")
    @Log(title = "法币订单发布", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RcFrenchCurrencyOrderRelease rcFrenchCurrencyOrderRelease)
    {
        List<RcFrenchCurrencyOrderRelease> list = rcFrenchCurrencyOrderReleaseService.selectRcFrenchCurrencyOrderReleaseList(rcFrenchCurrencyOrderRelease);
        ExcelUtil<RcFrenchCurrencyOrderRelease> util = new ExcelUtil<RcFrenchCurrencyOrderRelease>(RcFrenchCurrencyOrderRelease.class);
        return util.exportExcel(list, "rcFrenchCurrencyOrderRelease");
    }

    /**
     * 新增法币订单发布
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存法币订单发布
     */
    @RequiresPermissions("order:rcFrenchCurrencyOrderRelease:add")
    @Log(title = "法币订单发布", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RcFrenchCurrencyOrderRelease rcFrenchCurrencyOrderRelease)
    {
        return toAjax(rcFrenchCurrencyOrderReleaseService.insertRcFrenchCurrencyOrderRelease(rcFrenchCurrencyOrderRelease));
    }

    /**
     * 修改法币订单发布
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        RcFrenchCurrencyOrderRelease rcFrenchCurrencyOrderRelease = rcFrenchCurrencyOrderReleaseService.selectRcFrenchCurrencyOrderReleaseById(id);
        mmap.put("rcFrenchCurrencyOrderRelease", rcFrenchCurrencyOrderRelease);
        return prefix + "/edit";
    }

    /**
     * 修改保存法币订单发布
     */
    @RequiresPermissions("order:rcFrenchCurrencyOrderRelease:edit")
    @Log(title = "法币订单发布", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RcFrenchCurrencyOrderRelease rcFrenchCurrencyOrderRelease)
    {
        return toAjax(rcFrenchCurrencyOrderReleaseService.updateRcFrenchCurrencyOrderRelease(rcFrenchCurrencyOrderRelease));
    }

    /**
     * 删除法币订单发布
     */
    @RequiresPermissions("order:rcFrenchCurrencyOrderRelease:remove")
    @Log(title = "法币订单发布", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(rcFrenchCurrencyOrderReleaseService.deleteRcFrenchCurrencyOrderReleaseByIds(ids));
    }
}
