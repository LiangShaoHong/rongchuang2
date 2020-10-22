package com.ruoyi.dxapi.controller.common;

import com.ruoyi.common.constant.MsgConstants;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.dx.domain.PlatformBase;
import com.ruoyi.dxapi.controller.BaseController;
import com.ruoyi.dxservice.service.SysPlatformInfoService;
import com.ruoyi.framework.upload.UploadStorageUtil;
import com.ruoyi.framework.upload.aliyun.oss.AliyunStorageService;
import com.ruoyi.framework.upload.aws.storage.AmazonService;
import com.ruoyi.framework.upload.base.UploadBaseService;
import com.ruoyi.framework.upload.base.UploadConstant;
import com.ruoyi.framework.upload.base.UploadResultModel;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.dxapi.common.Result;
import com.ruoyi.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件上传接口
 */
@RestController("fileUploadController")
public class FileUploadController extends BaseController {

	@Autowired
	private ISysConfigService configService;

	@Autowired
	private UploadStorageUtil uploadStorageUtil;

	@Autowired
	private SysPlatformInfoService sysPlatformInfoService;

	/**
	 * 文件上传
	 */
	@PostMapping("/dx-api/common/fileUpload")
	public Result fileUpload(HttpServletRequest request,
							 @RequestParam("file")MultipartFile file,
							 @RequestParam(value = "prefix", required = false) String prefix)
	{
		String originName = super.getOrigin(request);
		PlatformBase platformBase = sysPlatformInfoService.getPlatformBaseByDomain(MsgConstants.IS_NO, MsgConstants.IS_NO, originName);
		if (null == platformBase){
			return Result.isFail("平台上传功能未开放");
		}
		prefix = platformBase.getId() + "/" + (null == prefix || "".equals(prefix) ? "" : prefix);
		String storageType = configService.selectConfigByKey("upload.storage.type");
		Class<? extends UploadBaseService> clazz = null;
		//取数据库配置信息
		if (UploadConstant.UploadStorageType.ALIYUN.getKey().equals(storageType)){
			clazz = AliyunStorageService.class;
		}else if (UploadConstant.UploadStorageType.AWS.getKey().equals(storageType)){
			clazz = AmazonService.class;
		}
		UploadResultModel amazonFileModel = uploadStorageUtil.upload(clazz, file, prefix, IdUtils.simpleUUID());
		if (amazonFileModel != null) {
			JSONObject data = new JSONObject();
			data.put("url", amazonFileModel.getUrl());
			data.put("path", amazonFileModel.getFilePath());
			return Result.isOk().data(data).msg(MsgConstants.OPERATOR_SUCCESS);
		}
		return Result.isOk().msg(MsgConstants.OPERATOR_FAIL);
	}

}
