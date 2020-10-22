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
import com.ruoyi.home.domain.RcNotice;
import com.ruoyi.home.service.IRcNoticeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 首页公告Controller
 * 
 * @author xiaoyu
 * @date 2020-10-22
 */
@Controller
@RequestMapping("/home/notice")
public class RcNoticeController extends BaseController
{
    private String prefix = "home/notice";

    @Autowired
    private IRcNoticeService rcNoticeService;

    @RequiresPermissions("home:notice:view")
    @GetMapping()
    public String notice()
    {
        return prefix + "/notice";
    }

    /**
     * 查询首页公告列表
     */
    @RequiresPermissions("home:notice:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RcNotice rcNotice)
    {
        startPage();
        List<RcNotice> list = rcNoticeService.selectRcNoticeList(rcNotice);
        return getDataTable(list);
    }

    /**
     * 导出首页公告列表
     */
    @RequiresPermissions("home:notice:export")
    @Log(title = "首页公告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RcNotice rcNotice)
    {
        List<RcNotice> list = rcNoticeService.selectRcNoticeList(rcNotice);
        ExcelUtil<RcNotice> util = new ExcelUtil<RcNotice>(RcNotice.class);
        return util.exportExcel(list, "notice");
    }

    /**
     * 新增首页公告
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存首页公告
     */
    @RequiresPermissions("home:notice:add")
    @Log(title = "首页公告", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RcNotice rcNotice)
    {
        return toAjax(rcNoticeService.insertRcNotice(rcNotice));
    }

    /**
     * 修改首页公告
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        RcNotice rcNotice = rcNoticeService.selectRcNoticeById(id);
        mmap.put("rcNotice", rcNotice);
        return prefix + "/edit";
    }

    /**
     * 修改保存首页公告
     */
    @RequiresPermissions("home:notice:edit")
    @Log(title = "首页公告", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RcNotice rcNotice)
    {
        return toAjax(rcNoticeService.updateRcNotice(rcNotice));
    }

    /**
     * 删除首页公告
     */
    @RequiresPermissions("home:notice:remove")
    @Log(title = "首页公告", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(rcNoticeService.deleteRcNoticeByIds(ids));
    }
}
