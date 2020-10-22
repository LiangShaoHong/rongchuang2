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
import com.ruoyi.home.domain.RcLunbo;
import com.ruoyi.home.service.IRcLunboService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 轮播图Controller
 *
 * @author xiaoxia
 * @date 2020-10-22
 */
@Controller
@RequestMapping("/home/rcLunbo")
public class RcLunboController extends BaseController
{
    private String prefix = "home/rcLunbo";

    @Autowired
    private IRcLunboService rcLunboService;

    @RequiresPermissions("home:rcLunbo:view")
    @GetMapping()
    public String rcLunbo()
    {
        return prefix + "/rcLunbo";
    }

    /**
     * 查询轮播图列表
     */
    @RequiresPermissions("home:rcLunbo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RcLunbo rcLunbo)
    {
        startPage();
        List<RcLunbo> list = rcLunboService.selectRcLunboList(rcLunbo);
        return getDataTable(list);
    }

    /**
     * 导出轮播图列表
     */
    @RequiresPermissions("home:rcLunbo:export")
    @Log(title = "轮播图", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RcLunbo rcLunbo)
    {
        List<RcLunbo> list = rcLunboService.selectRcLunboList(rcLunbo);
        ExcelUtil<RcLunbo> util = new ExcelUtil<RcLunbo>(RcLunbo.class);
        return util.exportExcel(list, "rcLunbo");
    }

    /**
     * 新增轮播图
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存轮播图
     */
    @RequiresPermissions("home:rcLunbo:add")
    @Log(title = "轮播图", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RcLunbo rcLunbo)
    {
        return toAjax(rcLunboService.insertRcLunbo(rcLunbo));
    }

    /**
     * 修改轮播图
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        RcLunbo rcLunbo = rcLunboService.selectRcLunboById(id);
        mmap.put("rcLunbo", rcLunbo);
        return prefix + "/edit";
    }

    /**
     * 修改保存轮播图
     */
    @RequiresPermissions("home:rcLunbo:edit")
    @Log(title = "轮播图", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RcLunbo rcLunbo)
    {
        return toAjax(rcLunboService.updateRcLunbo(rcLunbo));
    }

    /**
     * 删除轮播图
     */
    @RequiresPermissions("home:rcLunbo:remove")
    @Log(title = "轮播图", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(rcLunboService.deleteRcLunboByIds(ids));
    }
}
