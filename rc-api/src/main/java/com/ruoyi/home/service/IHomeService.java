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
    ResultDto getNoticeList(Integer pageNum, Integer pageSize);

    @ApiOperation("查询公告详情接口")
    ResultDto getNoticeDetail(Integer id);

    @ApiOperation("查询最新公告接口")
    ResultDto getNewNotice();


}
