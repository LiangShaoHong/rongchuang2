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
import com.ruoyi.user.domain.RcAccount;
import com.ruoyi.user.service.IRcAccountService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 账户收款Controller
 * 
 * @author ruoyi
 * @date 2020-11-02
 */
@Controller
@RequestMapping("/user/RcAccount")
public class RcAccountController extends BaseController
{
    private String prefix = "user/RcAccount";

    @Autowired
    private IRcAccountService rcAccountService;

    @RequiresPermissions("user:RcAccount:view")
    @GetMapping()
    public String RcAccount()
    {
        return prefix + "/RcAccount";
    }

    /**
     * 查询账户收款列表
     */
    @RequiresPermissions("user:RcAccount:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RcAccount rcAccount)
    {
        startPage();
        List<RcAccount> list = rcAccountService.selectRcAccountList(rcAccount);
        return getDataTable(list);
    }

    /**
     * 导出账户收款列表
     */
    @RequiresPermissions("user:RcAccount:export")
    @Log(title = "账户收款", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RcAccount rcAccount)
    {
        List<RcAccount> list = rcAccountService.selectRcAccountList(rcAccount);
        ExcelUtil<RcAccount> util = new ExcelUtil<RcAccount>(RcAccount.class);
        return util.exportExcel(list, "RcAccount");
    }

    /**
     * 新增账户收款
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存账户收款
     */
    @RequiresPermissions("user:RcAccount:add")
    @Log(title = "账户收款", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RcAccount rcAccount)
    {
        return toAjax(rcAccountService.insertRcAccount(rcAccount));
    }

    /**
     * 修改账户收款
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        RcAccount rcAccount = rcAccountService.selectRcAccountById(id);
        mmap.put("rcAccount", rcAccount);
        return prefix + "/edit";
    }

    /**
     * 修改保存账户收款
     */
    @RequiresPermissions("user:RcAccount:edit")
    @Log(title = "账户收款", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RcAccount rcAccount)
    {
        return toAjax(rcAccountService.updateRcAccount(rcAccount));
    }

    /**
     * 删除账户收款
     */
    @RequiresPermissions("user:RcAccount:remove")
    @Log(title = "账户收款", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(rcAccountService.deleteRcAccountByIds(ids));
    }
}
