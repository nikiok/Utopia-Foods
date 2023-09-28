package com.sky.service.impl;

import com.sky.entity.Setmeal;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * -----------------------------------------------
 *
 * @author ：niki
 * @date ：Created in 2023/9/28 19:41
 * @description：
 * @version: $
 * -----------------------------------------------
 */
@Service
public class SetmealServiceImpl implements SetmealService {

	@Autowired
	private SetmealMapper setmealMapper;
	@Autowired
	private DishMapper dishMapper;

	 @Override
	 public void startOrStop(Integer status, Long id) {

	 }
	/**
	 * 条件查询
	 * @param setmeal
	 * @return
	 */
	@Override
	public List<Setmeal> list(Setmeal setmeal) {
		List<Setmeal> list = setmealMapper.list(setmeal);
		return list;
	}

	/**
	 * 根据id查询菜品选项
	 * @param id
	 * @return
	 */
	@Override
	public List<DishItemVO> getDishItemById(Long id) {
		return setmealMapper.getDishItemBySetmealId(id);
	}
}
