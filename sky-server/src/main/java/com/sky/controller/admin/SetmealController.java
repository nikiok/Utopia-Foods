package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * -----------------------------------------------
 *
 * @author ：niki
 * @date ：Created in 2023/9/28 22:40
 * @description：
 * @version: $
 * -----------------------------------------------
 */
@RestController("adminSetmealController")
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐相关接口")
@Slf4j
public class SetmealController {
	@Autowired
	private SetmealService setmealService;


	/**
	 * 分页查询
	 * @param setmealPageQueryDTO
	 * @return
	 */
	@GetMapping("/page")
	@ApiOperation("套餐分页查询")
	public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
		log.info("套餐分页查询:{}",setmealPageQueryDTO);
		PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
		return Result.success(pageResult);
	}

	/**
	 * 新增套餐
	 *
	 * @param setmealDTO
	 * @return
	 */
	@PostMapping
	@ApiOperation("新增套餐")
	@CacheEvict(cacheNames = "setmealCache", key = "#setmealDTO.categoryId")//key: setmealCache::100
	public Result save(@RequestBody SetmealDTO setmealDTO) {
		log.info("新增套餐:{}",setmealDTO);
		setmealService.saveWithDish(setmealDTO);
		return Result.success();
	}

	/**
	 * 批量删除套餐
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping
	@ApiOperation("批量删除套餐")
	@CacheEvict(cacheNames = "setmealCache", allEntries = true)
	public Result delete(@RequestParam List<Long> ids) {
		log.info("批量删除套餐:{}",ids);
		setmealService.deleteBatch(ids);
		return Result.success();
	}

	/**
	 * 修改套餐
	 *
	 * @param setmealDTO
	 * @return
	 */
	@PutMapping
	@ApiOperation("修改套餐")
	@CacheEvict(cacheNames = "setmealCache", allEntries = true)
	public Result update(@RequestBody SetmealDTO setmealDTO) {
		log.info("修改套餐:{}",setmealDTO);
		setmealService.update(setmealDTO);
		return Result.success();
	}

	/**
	 * 套餐起售停售
	 *
	 * @param status
	 * @param id
	 * @return
	 */
	@PostMapping("/status/{status}")
	@ApiOperation("套餐起售停售")
	@CacheEvict(cacheNames = "setmealCache", allEntries = true)
	public Result startOrStop(@PathVariable Integer status, Long id) {
		log.info("套餐启停售：{}",status,id);
		setmealService.startOrStop(status, id);
		return Result.success();
	}


}
