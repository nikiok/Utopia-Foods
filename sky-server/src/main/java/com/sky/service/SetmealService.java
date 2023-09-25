package com.sky.service;

import org.springframework.stereotype.Service;

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
}
