package com.ruoyi.user.controller;

import com.ruoyi.common.Result;
import com.ruoyi.common.SystemUtil;
import com.ruoyi.common.constant.MsgConstants;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.controller.BaseController;
import com.ruoyi.framework.web.service.DictService;
import com.ruoyi.system.utils.DictUtils;
import com.ruoyi.user.domain.RcApiAuth;
import com.ruoyi.user.domain.RcUser;
import com.ruoyi.user.mapper.RcAuthApiMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;

/**
 * @author xiaoxia
 */
@Api("个人信息认证接口")
@RestController
@RequestMapping("/rc-api/userAuth")
public class RcAuthApiController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(RcAuthApiController.class);

    @Autowired
    private DictService dictService;

    @Resource
    private SystemUtil systemUtil;

    @Autowired
    private RcAuthApiMapper rcAuthApiMapper;

    @ApiOperation("请求个人信息接口的国家/地区和证件类型")
    @ApiImplicitParams(
            {
            })
    @PostMapping("/index")
    public Result register(HttpServletRequest request){
        JSONObject data = new JSONObject();
        data.set("nationality", "下面的接口一样 dictLabel=前端的显示字段 dictValue=传给后台的值 nationality=国家/地区 documentType=证件类型");
        data.set("documentType", dictService.getType("rc_user_auth"));
        return Result.isOk().data(data).msg(MsgConstants.OPERATOR_SUCCESS);
    }

    @ApiOperation("新增个人信息认证")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "String", name = "nationality", value = "国籍", required = true),
                    @ApiImplicitParam(dataType = "String", name = "documentType", value = "证件类型", required = true),
                    @ApiImplicitParam(dataType = "String", name = "certificateNum", value = "证件号码", required = true),
                    @ApiImplicitParam(dataType = "String", name = "certificateImg", value = "证件截图", required = true)
            })
    @PostMapping("/insertRcAuth")
    public Result insertRcAuth(
            @RequestParam("nationality") String nationality,
            @RequestParam("documentType") String documentType,
            @RequestParam("certificateNum") String certificateNum,
            @RequestParam("certificateImg") String certificateImg,HttpServletRequest request){
        RcUser user =  systemUtil.getPlatformIdAndUserId(request);
        RcApiAuth domain = new RcApiAuth();
        domain.setUserid(user.getId());
        domain.setName(user.getAccount());
        domain.setNationality(nationality);
        domain.setDocumentType(documentType);
        domain.setCertificateNum(certificateNum);
        domain.setCertificateImg(certificateImg);
        domain.setSubmitTime(new Date());
        rcAuthApiMapper.insertRcAuth(domain);
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
    }

    @ApiOperation("查询个人信息认证")
    @ApiImplicitParams(
            {
            })
    @PostMapping("/selectRcAuth")
    public Result selectRcAuth(HttpServletRequest request){
        RcUser user =  systemUtil.getPlatformIdAndUserId(request);
        JSONObject data = rcAuthApiMapper.selectRcAuth(user.getId());
        data.set("status",DictUtils.getDictLabel("rc_admin_auth", data.get("status").toString()));
        data.set("documentType", DictUtils.getDictLabel("rc_user_auth", data.get("documentType").toString()));
        return Result.isOk().data(data).msg(MsgConstants.OPERATOR_SUCCESS);
    }


}
