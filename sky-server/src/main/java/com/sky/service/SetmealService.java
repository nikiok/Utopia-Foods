package com.sky.service;

import com.sky.entity.Setmeal;
import com.sky.vo.DishItemVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * -----------------------------------------------
 *
 * @author ：niki
 * @date ：Created in 2023/9/25 19:17
 * @description：
 * @version: $
 * -----------------------------------------------
 */

@Service
public interface SetmealService {
	 /**
	  * 菜品起售停售
	  * @param status
	  * @param id
	  */
	 void startOrStop(Integer status, Long id);

	/**
	 * 条件查询
	 * @param setmeal
	 * @return
	 */
	List<Setmeal> list(Setmeal setmeal);

	/**
	 * 根据id查询菜品选项
	 * @param id
	 * @return
	 */
	List<DishItemVO> getDishItemById(Long id);
}
