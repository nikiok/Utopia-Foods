package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * -----------------------------------------------
 *
 * @author ：niki
 * @date ：Created in 2023/9/28 19:54
 * @description：C端-分类接口
 * @version: $
 * -----------------------------------------------
 */
@RestController("userCategoryController")
@RequestMapping("/user/category")
@Api(tags = "C端-分类接口")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	/**
	 * 查询分类
	 * @param type
	 * @return
	 */
	@GetMapping("/list")
	@ApiOperation("查询分类")
	public Result<List<Category>> list(Integer type) {
		List<Category> list = categoryService.list(type);
		return Result.success(list);
	}
}
