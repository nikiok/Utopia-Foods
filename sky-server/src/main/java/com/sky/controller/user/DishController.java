package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import com.sky.dto.DishDTO;
import java.util.List;
import java.util.Set;

/**
 * -----------------------------------------------
 *
 * @author ：niki
 * @date ：Created in 2023/9/28 19:52
 * @description： C端-用户菜品浏览
 * @version: $
 * -----------------------------------------------
 */
@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "C端-菜品浏览接口")
public class DishController {
	@Autowired
	private DishService dishService;
	@Autowired
	private RedisTemplate redisTemplate;


	/**
	 * 清理缓存数据
	 * @param pattern
	 */
	private void cleanCache(String pattern){
		Set keys = redisTemplate.keys(pattern);
		redisTemplate.delete(keys);
	}


	/**
	 * 根据分类id查询菜品
	 *
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/list")
	@ApiOperation("根据分类id查询菜品")
	public Result<List<DishVO>> list(Long categoryId) {
		/**
		 * 使用redis数据库做每个类的菜品缓存
		 */
		//构造redis中的key,规则：dish_分类id
		String key = "dish_" + categoryId;

		//查询redis中是否存在菜品数据
		List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
		if (list != null && list.size() > 0){
			//如果存在，直接返回，无须查询数据库
			return Result.success(list);
		}

		/*****************************************/
		Dish dish = new Dish();
		dish.setCategoryId(categoryId);
		dish.setStatus(StatusConstant.ENABLE);//查询起售中的菜品
		//如果不存在，查询数据库，将查询到的数据放入redis中
		list = dishService.listWithFlavor(dish);
		/*****************************************/

		redisTemplate.opsForValue().set(key,list);
		return Result.success(list);
	}
	/**
	 * 新增菜品
	 *
	 * @param dishDTO
	 * @return
	 */
	@PostMapping
	@ApiOperation("新增菜品")
	public Result save(@RequestBody DishDTO dishDTO) {
		log.info("新增菜品：{}", dishDTO);
		dishService.saveWithFlavor(dishDTO);

		//清理缓存数据
		String key = "dish_" + dishDTO.getCategoryId();
		cleanCache(key);
		return Result.success();
	}

	/**
	 * 菜品批量删除
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping
	@ApiOperation("菜品批量删除")
	public Result delete(@RequestParam List<Long> ids) {
		log.info("菜品批量删除：{}", ids);
		dishService.deleteBatch(ids);

		//将所有的菜品缓存数据清理掉，所有以dish_开头的key
		cleanCache("dish_*");

		return Result.success();
	}

	/**
	 * 修改菜品
	 *
	 * @param dishDTO
	 * @return
	 */
	@PutMapping
	@ApiOperation("修改菜品")
	public Result update(@RequestBody DishDTO dishDTO) {
		log.info("修改菜品：{}", dishDTO);
		dishService.updateWithFlavor(dishDTO);

		//将所有的菜品缓存数据清理掉，所有以dish_开头的key
		cleanCache("dish_*");

		return Result.success();
	}

}
