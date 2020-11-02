package com.ruoyi.user.controller;

import java.util.Date;
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
import com.ruoyi.user.domain.RcAuth;
import com.ruoyi.user.service.IRcAuthService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户认证Controller
 * 
 * @author xiaoyu
 * @date 2020-10-30
 */
@Controller
@RequestMapping("/user/userAuth")
public class RcAuthController extends BaseController
{
    private String prefix = "user/userAuth";

    @Autowired
    private IRcAuthService rcAuthService;

    @RequiresPermissions("user:userAuth:view")
    @GetMapping()
    public String userAuth()
    {
        return prefix + "/userAuth";
    }

    /**
     * 查询用户认证列表
     */
    @RequiresPermissions("user:userAuth:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RcAuth rcAuth)
    {
        startPage();
        List<RcAuth> list = rcAuthService.selectRcAuthList(rcAuth);
        return getDataTable(list);
    }

    /**
     * 导出用户认证列表
     */
    @RequiresPermissions("user:userAuth:export")
    @Log(title = "用户认证", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RcAuth rcAuth)
    {
        List<RcAuth> list = rcAuthService.selectRcAuthList(rcAuth);
        ExcelUtil<RcAuth> util = new ExcelUtil<RcAuth>(RcAuth.class);
        return util.exportExcel(list, "userAuth");
    }

    /**
     * 新增用户认证
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存用户认证
     */
    @RequiresPermissions("user:userAuth:add")
    @Log(title = "用户认证", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RcAuth rcAuth)
    {
        return toAjax(rcAuthService.insertRcAuth(rcAuth));
    }

    /**
     * 修改用户认证
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        RcAuth rcAuth = rcAuthService.selectRcAuthById(id);
        mmap.put("rcAuth", rcAuth);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户认证
     */
    @RequiresPermissions("user:userAuth:edit")
    @Log(title = "用户认证", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RcAuth rcAuth)
    {
        rcAuth.setReviewTime(new Date());
        return toAjax(rcAuthService.updateRcAuth(rcAuth));
    }

    /**
     * 删除用户认证
     */
    @RequiresPermissions("user:userAuth:remove")
    @Log(title = "用户认证", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(rcAuthService.deleteRcAuthByIds(ids));
    }
}
