package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * -----------------------------------------------
 *
 * @author ：niki
 * @date ：Created in 2023/9/26 15:29
 * @description： 公共类----文件上传
 * @version: $
 * -----------------------------------------------
 */
@RestController
@RequestMapping("/admin/common")
@Slf4j
@Api(tags = "通用接口")
public class CommonController {
	@Autowired
	private AliOssUtil aliOssUtil;


	/**
	 * 文件上传
	 * @param file
	 * @return
	 */
	@PostMapping("/upload")
	@ApiOperation("文件上传")
	public Result<String> upload(MultipartFile file){
		log.info("文件上传:{}",file);
		try {
			// 获取原始文件名
			String originalFilename = file.getOriginalFilename();
			// 获取文件后缀名
			String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			// 构造新文件名
			String objectName = UUID.randomUUID().toString() + extension;
			// 文件请求路径
			String filePath = aliOssUtil.upload(file.getBytes(),objectName);
			return Result.success(filePath);
		} catch (IOException e) {
			log.info("文件上传失败：{}",e);
//			throw new RuntimeException(e);
		}

		return Result.error(MessageConstant.UPLOAD_FAILED);
	}
}
