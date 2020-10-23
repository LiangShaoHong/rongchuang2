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
import com.ruoyi.digital.domain.RcNewestMarket;
import com.ruoyi.digital.service.IRcNewestMarketService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 最新上市Controller
 * 
 * @author xiaoyu
 * @date 2020-10-23
 */
@Controller
@RequestMapping("/digital/market")
public class RcNewestMarketController extends BaseController
{
    private String prefix = "digital/market";

    @Autowired
    private IRcNewestMarketService rcNewestMarketService;

    @RequiresPermissions("digital:market:view")
    @GetMapping()
    public String market()
    {
        return prefix + "/market";
    }

    /**
     * 查询最新上市列表
     */
    @RequiresPermissions("digital:market:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RcNewestMarket rcNewestMarket)
    {
        startPage();
        List<RcNewestMarket> list = rcNewestMarketService.selectRcNewestMarketList(rcNewestMarket);
        return getDataTable(list);
    }

    /**
     * 导出最新上市列表
     */
    @RequiresPermissions("digital:market:export")
    @Log(title = "最新上市", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RcNewestMarket rcNewestMarket)
    {
        List<RcNewestMarket> list = rcNewestMarketService.selectRcNewestMarketList(rcNewestMarket);
        ExcelUtil<RcNewestMarket> util = new ExcelUtil<RcNewestMarket>(RcNewestMarket.class);
        return util.exportExcel(list, "market");
    }

    /**
     * 新增最新上市
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存最新上市
     */
    @RequiresPermissions("digital:market:add")
    @Log(title = "最新上市", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RcNewestMarket rcNewestMarket)
    {
        return toAjax(rcNewestMarketService.insertRcNewestMarket(rcNewestMarket));
    }

    /**
     * 修改最新上市
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        RcNewestMarket rcNewestMarket = rcNewestMarketService.selectRcNewestMarketById(id);
        mmap.put("rcNewestMarket", rcNewestMarket);
        return prefix + "/edit";
    }

    /**
     * 修改保存最新上市
     */
    @RequiresPermissions("digital:market:edit")
    @Log(title = "最新上市", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RcNewestMarket rcNewestMarket)
    {
        return toAjax(rcNewestMarketService.updateRcNewestMarket(rcNewestMarket));
    }

    /**
     * 删除最新上市
     */
    @RequiresPermissions("digital:market:remove")
    @Log(title = "最新上市", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(rcNewestMarketService.deleteRcNewestMarketByIds(ids));
    }
}
