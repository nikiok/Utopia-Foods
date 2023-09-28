package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
	@Autowired
	private DishMapper dishMapper;
	@Autowired
	private DishFlavorMapper dishFlavorMapper;
	@Autowired
	private SetmealMapper setmealMapper;

	@Override
	public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
		PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
		Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Transactional
	public void startOrStop(Integer status, Long id) {
		Dish dish = Dish.builder()
				.id(id)
				.status(status)
				.build();
		dishMapper.update(dish);

		if (status == StatusConstant.DISABLE) {
			// 如果是停售操作，还需要将包含当前菜品的套餐也停售
			List<Long> dishIds = new ArrayList<>();
			dishIds.add(id);
			// select setmeal_id from setmeal_dish where dish_id in (?,?,?)
			List<Long> setmealIds = setmealMapper.getSetmealIdsByDishIds(dishIds);
			if (setmealIds != null && setmealIds.size() > 0) {
				for (Long setmealId : setmealIds) {
					Setmeal setmeal = Setmeal.builder()
							.id(setmealId)
							.status(StatusConstant.DISABLE)
							.build();
					setmealMapper.update(setmeal);
				}
			}
		}
	}


	/**
	 * 新增菜品和对应的口味
	 *
	 * @param dishDTO
	 */
	@Transactional
	public void saveWithFlavor(DishDTO dishDTO) {

		Dish dish = new Dish();
		BeanUtils.copyProperties(dishDTO, dish);

		//向菜品表插入1条数据
		dishMapper.insert(dish);//后绪步骤实现

		//获取insert语句生成的主键值
		Long dishId = dish.getId();

		List<DishFlavor> flavors = dishDTO.getFlavors();
		if (flavors != null && flavors.size() > 0) {
			flavors.forEach(dishFlavor -> {
				dishFlavor.setDishId(dishId);
			});
			//向口味表插入n条数据
			dishFlavorMapper.insertBatch(flavors);//后绪步骤实现
		}
	}


	/**
	 * 批量删除
	 *
	 * @param ids
	 */
	@Transactional //事务注解
	public void deleteBatch(List<Long> ids) {
		// 判断菜品status
		for (Long id : ids) {
			Dish dish = dishMapper.getById(id);
			if (dish.getStatus() == StatusConstant.ENABLE) {
				throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
			}
		}
		// 判断菜品是否被某个套餐关联
		List<Long> setmealIds = setmealMapper.getSetmealIdsByDishIds(ids);
		if (setmealIds != null && setmealIds.size() > 0) {
			throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
		}
		//删除dish表中的数据
		for (Long id : ids) {
			dishMapper.deleteById(id);
			//删除菜品关联的口味
			dishFlavorMapper.deleteByDishId(id);
		}
	}

	/**
	 * 根据菜品id查询菜品及对应的口味
	 *
	 * @param id
	 * @return
	 */
	@Override
	public DishVO getByIdWithFlavor(Long id) {
		//根据id查菜品数据表
		Dish dish = dishMapper.getById(id);
		//根据dish_id查口味表
		List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(id);
		//将查询到的结果封装到DishVO里
		DishVO dishVO = new DishVO();
		BeanUtils.copyProperties(dish, dishVO);
		dishVO.setFlavors(dishFlavors);

		return dishVO;
	}

	/**
	 * 修改菜品及对应的口味
	 *
	 * @param dishDTO
	 */
	@Override
	public void updateWithFlavor(DishDTO dishDTO) {
		Dish dish = new Dish();
		BeanUtils.copyProperties(dishDTO, dish);
		//修改菜品的基本信息
		dishMapper.update(dish);

		/**
		 * 修改对应的口味
		 * todo: 1.删除原有的口味 2.重新插入新的口味
		 */
		//删除原有口味
		dishFlavorMapper.deleteByDishId(dishDTO.getId());

		List<DishFlavor> dishFlavors = dishDTO.getFlavors();
		if (dishFlavors != null && dishFlavors.size() > 0) {
			dishFlavors.forEach(dishFlavor -> {
				dishFlavor.setDishId(dishDTO.getId());
			});
			dishFlavorMapper.insertBatch(dishFlavors);
		}
	}

	@Override
	public List<DishVO> listWithFlavor(Dish dish) {
		List<Dish> dishList = dishMapper.list(dish);

		List<DishVO> dishVOList = new ArrayList<>();

		for (Dish d : dishList) {
			DishVO dishVO = new DishVO();
			BeanUtils.copyProperties(d,dishVO);

			//根据菜品id查询对应的口味
			List<DishFlavor> flavors = dishFlavorMapper.getByDishId(d.getId());

			dishVO.setFlavors(flavors);
			dishVOList.add(dishVO);
		}

		return dishVOList;
	}

}
