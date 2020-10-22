package com.ruoyi.dxapi.controller.user;

import com.ruoyi.common.constant.MsgConstants;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.dx.domain.User;
import com.ruoyi.dxapi.common.Result;
import com.ruoyi.dxapi.common.SystemUtil;
import com.ruoyi.dxservice.service.UserRankRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 套餐信息信息接口层
 */
@RestController("dxUserRankRecController")
public class UserRankRecController {

    @Autowired
    UserRankRecService userRankRecService;

    @Resource
    private SystemUtil systemUtil;

    /**
     * 获取套餐列表
     * @return
     */
    @RequestMapping("/dx-api/user/getRankUserList")
    public Result getRankUserList(HttpServletRequest request){
        User user = systemUtil.getPlatformIdAndUserId(request);
        List<JSONObject> rankUserList = userRankRecService.getRankUserList(user.getPlatformId());
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(rankUserList);
    }
}
