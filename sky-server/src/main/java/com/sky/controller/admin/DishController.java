package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * -----------------------------------------------
 *
 * @author ：niki
 * @date ：Created in 2023/9/25 18:27
 * @description： 菜品管理
 * @version: $
 * -----------------------------------------------
 */
@Api(tags = "菜品管理相关接口")
@Slf4j
@RestController
@RequestMapping("/admin/dish")
public class DishController {
	@Autowired
	private DishService dishService;


	/**
	 * 新增菜品
	 * @return
	 */
	@PostMapping()
	@ApiOperation("新增菜品")
	public Result add(@RequestBody DishDTO dishDTO){
		log.info("新增菜品：{}",dishDTO);
		dishService.saveWithFlavor(dishDTO);
		return Result.success();
	}

	/**
	 * 分页查询
	 * @param dishPageQueryDTO
	 * @return
	 */
	@GetMapping("/page")
	@ApiOperation("菜品分页查询")
	public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
		log.info("分页查询:{}",dishPageQueryDTO);
		PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
		return Result.success(pageResult);
	}

	/**
	 * 启用菜品禁用功能
	 * @param status
	 * @param id
	 * @return
	 */
	@PostMapping("/status/{status}")
	@ApiOperation("菜品起售停售")
	public Result<String> startOrStop(@PathVariable Integer status, Long id){
		log.info("菜品起售停售");
		dishService.startOrStop(status,id);
		return Result.success();
	}

	/**
	 * 菜品批量删除
	 * @param ids
	 * @return
	 */
	@DeleteMapping()
	@ApiOperation("批量删除功能")
	public Result  deleteBatch(@RequestParam List<Long> ids){
		log.info("批量删除:{}",ids);
		dishService.deleteBatch(ids);
		return Result.success();
	}

	@GetMapping("/{id}")
	@ApiOperation("根据id查询菜品")
	public Result<DishVO> getByID(@PathVariable Long id){
		log.info("根据id查询菜品：{}",id);
		DishVO dishVO = dishService.getByIdWithFlavor(id);
		return Result.success(dishVO);
	}

	@PutMapping()
	@ApiOperation("修改菜品")
	public Result update(@RequestBody DishDTO dishDTO){
		log.info("修改菜品:{}",dishDTO);
		dishService.updateWithFlavor(dishDTO);
		return Result.success();
	}

	@GetMapping("/list")
	@ApiOperation("根据分类id查询菜品")
	public Result<List<Dish>> list(Long categoryId){
		List<Dish> list = dishService.list(categoryId);
		return Result.success(list);
	}
}
