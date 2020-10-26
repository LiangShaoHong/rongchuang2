package com.ruoyi.digital.controller;

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
import com.ruoyi.digital.domain.RcExchangeRate;
import com.ruoyi.digital.service.IRcExchangeRateService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 汇率Controller
 * 
 * @author xiaoyu
 * @date 2020-10-26
 */
@Controller
@RequestMapping("/digital/rate")
public class RcExchangeRateController extends BaseController
{
    private String prefix = "digital/rate";

    @Autowired
    private IRcExchangeRateService rcExchangeRateService;

    @RequiresPermissions("digital:rate:view")
    @GetMapping()
    public String rate()
    {
        return prefix + "/rate";
    }

    /**
     * 查询汇率列表
     */
    @RequiresPermissions("digital:rate:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RcExchangeRate rcExchangeRate)
    {
        startPage();
        List<RcExchangeRate> list = rcExchangeRateService.selectRcExchangeRateList(rcExchangeRate);
        return getDataTable(list);
    }

    /**
     * 导出汇率列表
     */
    @RequiresPermissions("digital:rate:export")
    @Log(title = "汇率", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RcExchangeRate rcExchangeRate)
    {
        List<RcExchangeRate> list = rcExchangeRateService.selectRcExchangeRateList(rcExchangeRate);
        ExcelUtil<RcExchangeRate> util = new ExcelUtil<RcExchangeRate>(RcExchangeRate.class);
        return util.exportExcel(list, "rate");
    }

    /**
     * 新增汇率
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存汇率
     */
    @RequiresPermissions("digital:rate:add")
    @Log(title = "汇率", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RcExchangeRate rcExchangeRate)
    {
        return toAjax(rcExchangeRateService.insertRcExchangeRate(rcExchangeRate));
    }

    /**
     * 修改汇率
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        RcExchangeRate rcExchangeRate = rcExchangeRateService.selectRcExchangeRateById(id);
        mmap.put("rcExchangeRate", rcExchangeRate);
        return prefix + "/edit";
    }

    /**
     * 修改保存汇率
     */
    @RequiresPermissions("digital:rate:edit")
    @Log(title = "汇率", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RcExchangeRate rcExchangeRate)
    {
        return toAjax(rcExchangeRateService.updateRcExchangeRate(rcExchangeRate));
    }

    /**
     * 删除汇率
     */
    @RequiresPermissions("digital:rate:remove")
    @Log(title = "汇率", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(rcExchangeRateService.deleteRcExchangeRateByIds(ids));
    }
}
