package com.ruoyi.order;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 法币业务接口
 *
 * @author ruoyi
 * @date 2020-10-22
 */
@Api("法币业务接口")
@RestController
@RequestMapping("/rc-api/legalCurrency")
public class LegalCurrencyApi {

    @ApiOperation("查询个人信息接口")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", dataType = "String", name = "X_Token", value = "用户登录凭据", required = true)})
    @PostMapping("/getFbPerInformation")
    public ResultDto getUser(String profitType) {

        return new ResultDto(1, "正確", profitType);
    }
}
