package com.ruoyi.home.controller;

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
import com.ruoyi.home.domain.RcInformation;
import com.ruoyi.home.service.IRcInformationService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 系统信息Controller
 * 
 * @author xiaoyu
 * @date 2020-10-22
 */
@Controller
@RequestMapping("/home/rcInformation")
public class RcInformationController extends BaseController
{
    private String prefix = "home/rcInformation";

    @Autowired
    private IRcInformationService rcInformationService;

    @RequiresPermissions("home:rcInformation:view")
    @GetMapping()
    public String rcInformation()
    {
        return prefix + "/rcInformation";
    }

    /**
     * 查询系统信息列表
     */
    @RequiresPermissions("home:rcInformation:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RcInformation rcInformation)
    {
        startPage();
        List<RcInformation> list = rcInformationService.selectRcInformationList(rcInformation);
        return getDataTable(list);
    }

    /**
     * 导出系统信息列表
     */
    @RequiresPermissions("home:rcInformation:export")
    @Log(title = "系统信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RcInformation rcInformation)
    {
        List<RcInformation> list = rcInformationService.selectRcInformationList(rcInformation);
        ExcelUtil<RcInformation> util = new ExcelUtil<RcInformation>(RcInformation.class);
        return util.exportExcel(list, "rcInformation");
    }

    /**
     * 新增系统信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存系统信息
     */
    @RequiresPermissions("home:rcInformation:add")
    @Log(title = "系统信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RcInformation rcInformation)
    {
        return toAjax(rcInformationService.insertRcInformation(rcInformation));
    }

    /**
     * 修改系统信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        RcInformation rcInformation = rcInformationService.selectRcInformationById(id);
        mmap.put("rcInformation", rcInformation);
        return prefix + "/edit";
    }

    /**
     * 修改保存系统信息
     */
    @RequiresPermissions("home:rcInformation:edit")
    @Log(title = "系统信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RcInformation rcInformation)
    {
        return toAjax(rcInformationService.updateRcInformation(rcInformation));
    }

    /**
     * 删除系统信息
     */
    @RequiresPermissions("home:rcInformation:remove")
    @Log(title = "系统信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(rcInformationService.deleteRcInformationByIds(ids));
    }
}
