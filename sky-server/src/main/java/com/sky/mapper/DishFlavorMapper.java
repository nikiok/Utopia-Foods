package com.sky.mapper;

import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * -----------------------------------------------
 *
 * @author ：niki
 * @date ：Created in 2023/9/26 17:45
 * @description：
 * @version: $
 * -----------------------------------------------
 */
@Mapper
public interface DishFlavorMapper {
	/**
	 * 批量插入口味数据
	 * @param flavors
	 */
	void insertBatch(List<DishFlavor> flavors);

	@Delete("delete from dish_flavor where dish_id = #{dishId}")
	void deleteByDishId(Long dishId);

	@Select("select  * from dish_flavor where dish_id = #{dishId}")
	List<DishFlavor> getByDishId(Long dishId);
}
