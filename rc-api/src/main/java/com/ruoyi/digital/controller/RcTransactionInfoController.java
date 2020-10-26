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
import com.ruoyi.digital.domain.RcTransactionInfo;
import com.ruoyi.digital.service.IRcTransactionInfoService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 数据列表Controller
 * 
 * @author xiaoyu
 * @date 2020-10-23
 */
@Controller
@RequestMapping("/digital/info")
public class RcTransactionInfoController extends BaseController
{
    private String prefix = "digital/info";

    @Autowired
    private IRcTransactionInfoService rcTransactionInfoService;

    @RequiresPermissions("digital:info:view")
    @GetMapping()
    public String info()
    {
        return prefix + "/info";
    }

    /**
     * 查询数据列表列表
     */
    @RequiresPermissions("digital:info:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RcTransactionInfo rcTransactionInfo)
    {
        startPage();
        List<RcTransactionInfo> list = rcTransactionInfoService.selectRcTransactionInfoList(rcTransactionInfo);
        return getDataTable(list);
    }

    /**
     * 导出数据列表列表
     */
    @RequiresPermissions("digital:info:export")
    @Log(title = "数据列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RcTransactionInfo rcTransactionInfo)
    {
        List<RcTransactionInfo> list = rcTransactionInfoService.selectRcTransactionInfoList(rcTransactionInfo);
        ExcelUtil<RcTransactionInfo> util = new ExcelUtil<RcTransactionInfo>(RcTransactionInfo.class);
        return util.exportExcel(list, "info");
    }

    /**
     * 新增数据列表
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存数据列表
     */
    @RequiresPermissions("digital:info:add")
    @Log(title = "数据列表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RcTransactionInfo rcTransactionInfo)
    {
        return toAjax(rcTransactionInfoService.insertRcTransactionInfo(rcTransactionInfo));
    }

    /**
     * 修改数据列表
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        RcTransactionInfo rcTransactionInfo = rcTransactionInfoService.selectRcTransactionInfoById(id);
        mmap.put("rcTransactionInfo", rcTransactionInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存数据列表
     */
    @RequiresPermissions("digital:info:edit")
    @Log(title = "数据列表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RcTransactionInfo rcTransactionInfo)
    {
        return toAjax(rcTransactionInfoService.updateRcTransactionInfo(rcTransactionInfo));
    }

    /**
     * 删除数据列表
     */
    @RequiresPermissions("digital:info:remove")
    @Log(title = "数据列表", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(rcTransactionInfoService.deleteRcTransactionInfoByIds(ids));
    }
}
