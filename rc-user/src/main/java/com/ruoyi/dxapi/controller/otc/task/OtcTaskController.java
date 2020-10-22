package com.ruoyi.dxapi.controller.otc.task;

import com.ruoyi.common.constant.InfoConstants;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.dto.PageDTO;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.redis.RedisService;
import com.ruoyi.dx.domain.User;
import com.ruoyi.dx.service.ITaskRecService;
import com.ruoyi.dx.vo.PageParm;
import com.ruoyi.dxapi.common.Result;
import com.ruoyi.dxapi.common.SystemUtil;
import com.ruoyi.dxservice.service.OtcSysConfService;
import com.ruoyi.dxservice.service.TaskRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 任务专区
 */
@RestController("dxOtcTaskController")
public class OtcTaskController {

    @Resource
    private SystemUtil systemUtil;

    @Autowired
    private ITaskRecService iTaskRecService;

    @Autowired
    private TaskRecService taskRecService;

    @Resource
    private OtcSysConfService otcSysConfService;


    /**
     * 获取火币大厅的所有任务
     * @param request
     * @return
     */
    @PostMapping("/dx-api/otc/fireTaskList")
    public Result fireTaskList(HttpServletRequest request, PageParm vo,@RequestParam(value = "offset", required = false) Integer offset) {
        if (null != offset && null != vo.getLimit() && offset.compareTo(vo.getLimit()) >= 0){
            return Result.isFail("任务列表获取失败");
        }
        PageDTO dto = null;
        if (null != offset){
            dto = new PageDTO(vo.getPageNumber(), vo.getLimit(), offset);
        } else {
            dto = new PageDTO(vo.getPageNumber(), vo.getLimit());
        }
        User user = systemUtil.getPlatformIdAndUserId(request);
        return Result.isOk().msg("查询成功").data(iTaskRecService.selectFireTask(dto,user.getPlatformId(),user.getId()));
    }

    /**
     * 火币大厅任务详情
     * @param request
     * @return
     */
    @PostMapping("/dx-api/otc/fireTaskDetails")
    public Result fireTaskDetails(HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        return null;
    }

    /**
     * 领取火币大厅的任务
     * @param request
     * @return
     */
    @PostMapping("/dx-api/otc/receiveFireTask")
    public Result receiveFireTask(HttpServletRequest request, @RequestParam("id")String id) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        if(!otcSysConfService.userIsWalletTravellingTrader(user.getId())){
            throw new BusinessException("当前用户不是客商身份，无法操作，请联系客服开放权限");
        }
        Map<Integer,String> result = taskRecService.getFireTask(id,user.getId(),user.getPlatformId());
        String msg = result.get(0);
        if(null == msg){
            msg = result.get(1);
            return Result.isOk().msg(msg);
        }
        return Result.isFail(msg);
    }
}
