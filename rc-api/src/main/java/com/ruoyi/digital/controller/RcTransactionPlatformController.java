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
import com.ruoyi.digital.domain.RcTransactionPlatform;
import com.ruoyi.digital.service.IRcTransactionPlatformService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 交易所信息Controller
 * 
 * @author xiaoyu
 * @date 2020-10-22
 */
@Controller
@RequestMapping("/digital/platform")
public class RcTransactionPlatformController extends BaseController
{
    private String prefix = "digital/platform";

    @Autowired
    private IRcTransactionPlatformService rcTransactionPlatformService;

    @RequiresPermissions("digital:platform:view")
    @GetMapping()
    public String platform()
    {
        return prefix + "/platform";
    }

    /**
     * 查询交易所信息列表
     */
    @RequiresPermissions("digital:platform:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RcTransactionPlatform rcTransactionPlatform)
    {
        startPage();
        List<RcTransactionPlatform> list = rcTransactionPlatformService.selectRcTransactionPlatformList(rcTransactionPlatform);
        return getDataTable(list);
    }

    /**
     * 导出交易所信息列表
     */
    @RequiresPermissions("digital:platform:export")
    @Log(title = "交易所信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RcTransactionPlatform rcTransactionPlatform)
    {
        List<RcTransactionPlatform> list = rcTransactionPlatformService.selectRcTransactionPlatformList(rcTransactionPlatform);
        ExcelUtil<RcTransactionPlatform> util = new ExcelUtil<RcTransactionPlatform>(RcTransactionPlatform.class);
        return util.exportExcel(list, "platform");
    }

    /**
     * 新增交易所信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存交易所信息
     */
    @RequiresPermissions("digital:platform:add")
    @Log(title = "交易所信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RcTransactionPlatform rcTransactionPlatform)
    {
        return toAjax(rcTransactionPlatformService.insertRcTransactionPlatform(rcTransactionPlatform));
    }

    /**
     * 修改交易所信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        RcTransactionPlatform rcTransactionPlatform = rcTransactionPlatformService.selectRcTransactionPlatformById(id);
        mmap.put("rcTransactionPlatform", rcTransactionPlatform);
        return prefix + "/edit";
    }

    /**
     * 修改保存交易所信息
     */
    @RequiresPermissions("digital:platform:edit")
    @Log(title = "交易所信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RcTransactionPlatform rcTransactionPlatform)
    {
        return toAjax(rcTransactionPlatformService.updateRcTransactionPlatform(rcTransactionPlatform));
    }

    /**
     * 删除交易所信息
     */
    @RequiresPermissions("digital:platform:remove")
    @Log(title = "交易所信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(rcTransactionPlatformService.deleteRcTransactionPlatformByIds(ids));
    }
}
