package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * -----------------------------------------------
 *
 * @author ：niki
 * @date ：Created in 2023/9/25 18:28
 * @description：
 * @version: $
 * -----------------------------------------------
 */
@Service
public interface DishService {
	PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);
	void startOrStop(Integer status,Long id);

	/**
	 * 新增菜品和对应的口味
	 * @param dishDTO
	 */
	void saveWithFlavor(DishDTO dishDTO);

	void deleteBatch(List<Long> ids);

	DishVO getByIdWithFlavor(Long id);

	void updateWithFlavor(DishDTO dishDTO);

	/**
	 * 条件查询菜品和口味
	 * @param dish
	 * @return
	 */
	List<DishVO> listWithFlavor(Dish dish);
}
