package com.ruoyi.dxapi.controller.common;


import com.ruoyi.dxapi.common.Result;
import com.ruoyi.framework.upload.aliyun.oss.AliyunStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dx-api")
public class AliYunOssController {

    @Autowired
    private AliyunStorageService aliyunStorageService;

    /**
     * 获取 oss 签名
     * @return
     */
    @GetMapping("oss/get_sign")
    public Result getSign(@RequestParam(required = false) String prefix){

        return Result.isOk().data(aliyunStorageService.getAliYunSign(prefix));
    }
}
