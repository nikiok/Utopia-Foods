package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import org.springframework.stereotype.Service;

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

}
