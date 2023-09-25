package com.sky.controller.admin;

import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * -----------------------------------------------
 *
 * @author ：niki
 * @date ：Created in 2023/9/25 18:27
 * @description：
 * @version: $
 * -----------------------------------------------
 */
@Slf4j
@RestController("/admin/dish")
public class DishController {
	@Autowired
	private DishService dishService;


	@GetMapping("/page")
	public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
		log.info("分页查询:{}",dishPageQueryDTO);
		PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
		return Result.success(pageResult);
	}

	@PostMapping("/status/{status}")
	public Result<String> startOrStop(@PathVariable Integer status, Long id){
		dishService.startOrStop(status,id);
		return Result.success();
	}
}
