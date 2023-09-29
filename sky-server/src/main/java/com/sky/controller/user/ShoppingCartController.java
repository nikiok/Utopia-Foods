package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * -----------------------------------------------
 *
 * @author ：niki
 * @date ：Created in 2023/9/29 14:23
 * @description：购物车相关接口
 * @version: $
 * -----------------------------------------------
 */

@RestController
@RequestMapping("/user/shoppingCart")
@Api(tags = "C端-购物车相关接口")
@Slf4j
public class ShoppingCartController {
	@Autowired
	private ShoppingCartService shoppingCartService;

	/**
	 * 查看购物车
	 * @return
	 */
	@GetMapping("/list")
	@ApiOperation("查看购物车")
	public Result<List<ShoppingCart>> list(){
		return Result.success(shoppingCartService.showShoppingCart());
	}

	/**
	 * 添加购物车
	 * @param shoppingCartDTO
	 * @return
	 */
	@PostMapping("/add")
	@ApiOperation("添加购物车")
	public Result<String> add(@RequestBody ShoppingCartDTO shoppingCartDTO){
		log.info("添加购物车：{}", shoppingCartDTO);
		shoppingCartService.addShoppingCart(shoppingCartDTO);//后绪步骤实现
		return Result.success();
	}

	/**
	 * 清空购物车商品
	 * @return
	 */
	@DeleteMapping("/clean")
	@ApiOperation("清空购物车商品")
	public Result<String> clean(){
		shoppingCartService.cleanShoppingCart();
		return Result.success();
	}

	/**
	 * 删除购物车中一个商品
	 * @param shoppingCartDTO
	 * @return
	 */
	@PostMapping("/sub")
	@ApiOperation("删除购物车中一个商品")
	public Result sub(@RequestBody ShoppingCartDTO shoppingCartDTO){
		log.info("删除购物车中一个商品，商品：{}", shoppingCartDTO);
		shoppingCartService.subShoppingCart(shoppingCartDTO);
		return Result.success();


	}


}
