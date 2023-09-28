package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * -----------------------------------------------
 *
 * @author ：niki
 * @date ：Created in 2023/9/28 15:44
 * @description： redis配置类
 * @version: $
 * -----------------------------------------------
 */
@Configuration
@Slf4j
public class RedisConfiguration {
	
	@Bean
	public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
		log.info("开始创建redis模板对象...");
		RedisTemplate redisTemplate = new RedisTemplate();
		//设置redis的连接工厂对象
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		//设置redis key的序列化器
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		return redisTemplate;
	}

}
