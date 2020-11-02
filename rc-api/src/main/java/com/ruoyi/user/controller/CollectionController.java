package com.ruoyi.user.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.Result;
import com.ruoyi.common.SystemUtil;
import com.ruoyi.user.domain.RcAccount;
import com.ruoyi.user.domain.RcUser;
import com.ruoyi.user.mapper.CollectionMapper;
import com.ruoyi.user.service.ICollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

@Api("收款接口")
@RestController
@RequestMapping("/rc-api/collection")
public class CollectionController {

    @Resource
    private SystemUtil systemUtil;

    @Autowired
    private ICollectionService iCollectionService;

    @ApiOperation("个人收款账户列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
            })
    @PostMapping("/getPersonalCollectionAccountTypeList")
    public Result getPersonalCollectionAccountList(HttpServletRequest request) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        if (user == null) {
            return new Result().isFail("登录超时");
        }
        return iCollectionService.getPersonalCollectionAccountList(user.getId());
    }


    @ApiOperation("收款账户类型列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
            })
    @PostMapping("/getCollectionAccountTypeList")
    public Result getCollectionAccountTypeList(HttpServletRequest request) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        if (user == null) {
            return new Result().isFail("登录超时");
        }
        Result result = iCollectionService.getCollectionAccountTypeList();
        return result;
    }

    @ApiOperation("收款账户类型属性列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "String", name = "dictValue", value = "账户类型", required = true)
            })
    @PostMapping("/getCollectionAccountTypeAttributeList")
    public Result getCollectionAccountTypeAttributeList(HttpServletRequest request, String dictValue) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        if (user == null) {
            return new Result().isFail("登录超时");
        }
        Result result = iCollectionService.getCollectionAccountTypeAttributeList(dictValue);
        return result;
    }


    @ApiOperation("新增个人收款账户接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "String", name = "dictType", value = "收款类型", required = true),
                    @ApiImplicitParam(dataType = "String", name = "json", value = "收款信息", required = true),
                    @ApiImplicitParam(dataType = "String", name = "qrcode", value = "二维码", required = true)
            })
    @PostMapping("/addPersonalCollection")
    public Result addPersonalCollection(HttpServletRequest request, String dictType, String json, String qrcode) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        if (user == null) {
            return new Result().isFail("登录超时");
        }
        Result result = iCollectionService.addPersonalCollection(user.getId(), dictType, json, qrcode);
        return result;
    }


    @ApiOperation("修改个人收款账户接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "Long", name = "id", value = "业务id", required = true),
                    @ApiImplicitParam(dataType = "String", name = "dictType", value = "收款类型", required = true),
                    @ApiImplicitParam(dataType = "String", name = "json", value = "收款信息", required = true),
                    @ApiImplicitParam(dataType = "String", name = "qrcode", value = "二维码", required = true)
            })
    @PostMapping("/updatePersonalCollection")
    public Result updatePersonalCollection(HttpServletRequest request, Long id, String dictType, String json, String qrcode) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        if (user == null) {
            return new Result().isFail("登录超时");
        }
        Result result = iCollectionService.updatePersonalCollection(user.getId(), id, dictType, json, qrcode);
        return result;
    }


    @ApiOperation("删除个人收款账户接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true),
                    @ApiImplicitParam(dataType = "Long", name = "id", value = "业务id", required = true)
            })
    @PostMapping("/deletePersonalCollection")
    public Result deletePersonalCollection(HttpServletRequest request, Long id) {
        RcUser user = systemUtil.getPlatformIdAndUserId(request);
        if (user == null) {
            return new Result().isFail("登录超时");
        }
        Result result = iCollectionService.deletePersonalCollection(user.getId(), id);
        return result;
    }
}
