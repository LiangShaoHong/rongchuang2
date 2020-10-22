package com.ruoyi.home;


import com.ruoyi.common.utils.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * 首页公告 轮播 系统消息 帮助接口
 */
@Api("首页公告 轮播 系统消息 帮助接口")
@RestController
@RequestMapping("/rc-api/home")
public class HomeApi {

    @ApiOperation("查询公告列表接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int", name = "pageNumber", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页大小", required = true)
            })
    @PostMapping("/getNoticeList")
    public ResultDto getNoticeList(HttpServletRequest request, Integer pageNumber, Integer limit) {
        String X_Token = request.getHeader("X_Token");
        return new ResultDto(1);
    }

    @ApiOperation("查询公告详情接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int", name = "pageNumber", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页大小", required = true)
            })
    @PostMapping("/getNoticeDetail")
    public ResultDto getNoticeDetail(HttpServletRequest request, Integer pageNumber, Integer limit) {
        String X_Token = request.getHeader("X_Token");
        return new ResultDto(1);
    }

    @ApiOperation("查询最新公告接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int", name = "pageNumber", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页大小", required = true)
            })
    @PostMapping("/getNewNotice")
    public ResultDto getNewNotice(HttpServletRequest request, Integer pageNumber, Integer limit) {
        String X_Token = request.getHeader("X_Token");
        return new ResultDto(1);
    }

    @ApiOperation("查询图片轮播接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int", name = "pageNumber", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页大小", required = true)
            })
    @PostMapping("/getLunboList")
    public ResultDto getLunboList(HttpServletRequest request, Integer pageNumber, Integer limit) {
        return new ResultDto(1);
    }

    @ApiOperation("查询系统消息接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int", name = "pageNumber", value = "当前页号", required = true),
                    @ApiImplicitParam(dataType = "int", name = "limit", value = "每页大小", required = true)
            })
    @PostMapping("/getInfoList")
    public ResultDto getInfoList(HttpServletRequest request, Integer pageNumber, Integer limit) {
        return new ResultDto(1);
    }

}
