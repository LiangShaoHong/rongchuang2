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
import com.ruoyi.digital.domain.RcTransactionData;
import com.ruoyi.digital.service.IRcTransactionDataService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 实时各交易所币种交易数据Controller
 * 
 * @author xiaoyu
 * @date 2020-10-22
 */
@Controller
@RequestMapping("/digital/transaction")
public class RcTransactionDataController extends BaseController
{
    private String prefix = "digital/transaction";

    @Autowired
    private IRcTransactionDataService rcTransactionDataService;

    @RequiresPermissions("digital:transaction:view")
    @GetMapping()
    public String transaction()
    {
        return prefix + "/transaction";
    }

    /**
     * 查询实时各交易所币种交易数据列表
     */
    @RequiresPermissions("digital:transaction:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RcTransactionData rcTransactionData)
    {
        startPage();
        List<RcTransactionData> list = rcTransactionDataService.selectRcTransactionDataList(rcTransactionData);
        return getDataTable(list);
    }

    /**
     * 导出实时各交易所币种交易数据列表
     */
    @RequiresPermissions("digital:transaction:export")
    @Log(title = "实时各交易所币种交易数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RcTransactionData rcTransactionData)
    {
        List<RcTransactionData> list = rcTransactionDataService.selectRcTransactionDataList(rcTransactionData);
        ExcelUtil<RcTransactionData> util = new ExcelUtil<RcTransactionData>(RcTransactionData.class);
        return util.exportExcel(list, "transaction");
    }

    /**
     * 新增实时各交易所币种交易数据
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存实时各交易所币种交易数据
     */
    @RequiresPermissions("digital:transaction:add")
    @Log(title = "实时各交易所币种交易数据", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RcTransactionData rcTransactionData)
    {
        return toAjax(rcTransactionDataService.insertRcTransactionData(rcTransactionData));
    }

    /**
     * 修改实时各交易所币种交易数据
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        RcTransactionData rcTransactionData = rcTransactionDataService.selectRcTransactionDataById(id);
        mmap.put("rcTransactionData", rcTransactionData);
        return prefix + "/edit";
    }

    /**
     * 修改保存实时各交易所币种交易数据
     */
    @RequiresPermissions("digital:transaction:edit")
    @Log(title = "实时各交易所币种交易数据", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RcTransactionData rcTransactionData)
    {
        return toAjax(rcTransactionDataService.updateRcTransactionData(rcTransactionData));
    }

    /**
     * 删除实时各交易所币种交易数据
     */
    @RequiresPermissions("digital:transaction:remove")
    @Log(title = "实时各交易所币种交易数据", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(rcTransactionDataService.deleteRcTransactionDataByIds(ids));
    }
}
