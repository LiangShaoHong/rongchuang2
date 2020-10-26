package com.ruoyi.user.controller;

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
import com.ruoyi.user.domain.RcUser;
import com.ruoyi.user.service.IRcUserService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户注册Controller
 * 
 * @author ruoyi
 * @date 2020-10-22
 */
@Controller
@RequestMapping("/UserList/userData")
public class RcUserController extends BaseController
{
    private String prefix = "UserList/userData";

    @Autowired
    private IRcUserService rcUserService;

    @RequiresPermissions("UserList:userData:view")
    @GetMapping()
    public String userData()
    {
        return prefix + "/userData";
    }

    /**
     * 查询用户注册列表
     */
    @RequiresPermissions("UserList:userData:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RcUser rcUser)
    {
        startPage();
        List<RcUser> list = rcUserService.selectRcUserList(rcUser);
        return getDataTable(list);
    }

    /**
     * 导出用户注册列表
     */
    @RequiresPermissions("UserList:userData:export")
    @Log(title = "用户注册", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RcUser rcUser)
    {
        List<RcUser> list = rcUserService.selectRcUserList(rcUser);
        ExcelUtil<RcUser> util = new ExcelUtil<RcUser>(RcUser.class);
        return util.exportExcel(list, "userData");
    }

    /**
     * 新增用户注册
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存用户注册
     */
    @RequiresPermissions("UserList:userData:add")
    @Log(title = "用户注册", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RcUser rcUser)
    {
        return toAjax(rcUserService.insertRcUser(rcUser));
    }

    /**
     * 修改用户注册
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        RcUser rcUser = rcUserService.selectRcUserById(id);
        mmap.put("rcUser", rcUser);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户注册
     */
    @RequiresPermissions("UserList:userData:edit")
    @Log(title = "用户注册", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RcUser rcUser)
    {
        return toAjax(rcUserService.updateRcUser(rcUser));
    }

    /**
     * 删除用户注册
     */
    @RequiresPermissions("UserList:userData:remove")
    @Log(title = "用户注册", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(rcUserService.deleteRcUserByIds(ids));
    }
}
