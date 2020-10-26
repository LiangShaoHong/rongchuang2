package com.ruoyi.home.service;

import com.ruoyi.common.utils.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author xiaoxia
 */
@Api("首页公告 轮播 系统消息 帮助接口")
public interface IHomeService {

    @ApiOperation("查询公告列表接口")
    ResultDto getNoticeList(Integer pageNumber, Integer limit);

    @ApiOperation("查询公告详情接口")
    ResultDto getNoticeDetail(Integer id);

    @ApiOperation("查询最新公告接口")
    ResultDto getNewNotice();

    @ApiOperation("查询图片轮播接口")
    ResultDto getLunboList();

    @ApiOperation("查询系统消息接口")
    ResultDto getInfoList(Integer pageNumber, Integer limit);

    @ApiOperation("查询帮助列表接口")
    ResultDto getHelpList(Integer pageNumber, Integer limit);

    @ApiOperation("查询帮助详情接口")
    ResultDto getHelpDetail(Integer id);

}
