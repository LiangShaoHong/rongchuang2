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
import com.ruoyi.home.domain.RcHelp;
import com.ruoyi.home.service.IRcHelpService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 帮助信息Controller
 * 
 * @author xiaoyu
 * @date 2020-10-22
 */
@Controller
@RequestMapping("/home/help")
public class RcHelpController extends BaseController
{
    private String prefix = "home/help";

    @Autowired
    private IRcHelpService rcHelpService;

    @RequiresPermissions("home:help:view")
    @GetMapping()
    public String help()
    {
        return prefix + "/help";
    }

    /**
     * 查询帮助信息列表
     */
    @RequiresPermissions("home:help:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RcHelp rcHelp)
    {
        startPage();
        List<RcHelp> list = rcHelpService.selectRcHelpList(rcHelp);
        return getDataTable(list);
    }

    /**
     * 导出帮助信息列表
     */
    @RequiresPermissions("home:help:export")
    @Log(title = "帮助信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RcHelp rcHelp)
    {
        List<RcHelp> list = rcHelpService.selectRcHelpList(rcHelp);
        ExcelUtil<RcHelp> util = new ExcelUtil<RcHelp>(RcHelp.class);
        return util.exportExcel(list, "help");
    }

    /**
     * 新增帮助信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存帮助信息
     */
    @RequiresPermissions("home:help:add")
    @Log(title = "帮助信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RcHelp rcHelp)
    {
        return toAjax(rcHelpService.insertRcHelp(rcHelp));
    }

    /**
     * 修改帮助信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        RcHelp rcHelp = rcHelpService.selectRcHelpById(id);
        mmap.put("rcHelp", rcHelp);
        return prefix + "/edit";
    }

    /**
     * 修改保存帮助信息
     */
    @RequiresPermissions("home:help:edit")
    @Log(title = "帮助信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RcHelp rcHelp)
    {
        return toAjax(rcHelpService.updateRcHelp(rcHelp));
    }

    /**
     * 删除帮助信息
     */
    @RequiresPermissions("home:help:remove")
    @Log(title = "帮助信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(rcHelpService.deleteRcHelpByIds(ids));
    }
}
