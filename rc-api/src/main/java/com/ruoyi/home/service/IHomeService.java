package com.ruoyi.home.service;

import com.ruoyi.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xiaoxia
 */
@Api("首页公告 轮播 系统消息 帮助接口")
public interface IHomeService {

    @ApiOperation("查询公告列表接口")
    Result getNoticeList(Integer pageNum, Integer pageSize);

    @ApiOperation("查询公告详情接口")
    Result getNoticeDetail(Integer id);

    @ApiOperation("查询最新公告接口")
    Result getNewNotice();

    @ApiOperation("查询图片轮播接口")
    Result getLunboList();

    @ApiOperation("查询系统消息接口")
    Result getInfoList(Integer pageNum, Integer pageSize);

    @ApiOperation("查询帮助列表接口")
    Result getHelpList(Integer pageNum, Integer pageSize);

    @ApiOperation("查询帮助详情接口")
    Result getHelpDetail(Integer id);

}
