package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * -----------------------------------------------
 *
 * @author ：niki
 * @date ：Created in 2023/9/29 14:27
 * @description：
 * @version: $
 * -----------------------------------------------
 */
@Service
public interface ShoppingCartService {


	/**
	 * 添加购物车
	 * @param shoppingCartDTO
	 */
	void addShoppingCart(ShoppingCartDTO shoppingCartDTO);
	/**
	 * 查看购物车
	 * @return
	 */
	List<ShoppingCart> showShoppingCart();
	/**
	 * 清空购物车商品
	 */
	void cleanShoppingCart();

	/**
	 * 删除购物车中一个商品
	 * @param shoppingCartDTO
	 */
	void subShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
