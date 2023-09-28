package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * -----------------------------------------------
 *
 * @author ：niki
 * @date ：Created in 2023/9/28 16:30
 * @description： 用户端
 * @version: $
 * -----------------------------------------------
 */
@Slf4j
@RestController("userController")
@RequestMapping("/user/shop")
@Api(tags = "店铺相关接口")
public class ShopController {
     private String KEY = "SHOP_STATUS";

     @Autowired
     private RedisTemplate redisTemplate;


     @GetMapping("/status")
     @ApiOperation("获取店铺状态")
     public Result<Integer> getStatus(){
      Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
      log.info("获取到营业状态为:{}",status == 1 ? "营业中":"打烊中");
      return Result.success(status);
     }
}