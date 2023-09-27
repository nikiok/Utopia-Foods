package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * -----------------------------------------------
 *
 * @author ：niki
 * @date ：Created in 2023/9/26 17:33
 * @description： aliOss配置类
 * @version: $
 * -----------------------------------------------
 */
@Configuration
@Slf4j
public class OssConfiguration {
	@Bean
	@ConditionalOnMissingBean
	public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){
		log.info("开始创建阿里Oss文件上传工具类对象：{}",aliOssProperties);
		return new AliOssUtil(aliOssProperties.getEndpoint(),
				aliOssProperties.getAccessKeyId(),
				aliOssProperties.getAccessKeySecret(),
				aliOssProperties.getBucketName());
	}

}
