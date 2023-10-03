package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * -----------------------------------------------
 *
 * @author ：niki
 * @date ：Created in 2023/9/28 19:22
 * @description：
 * @version: $
 * -----------------------------------------------
 */
@Mapper
public interface UserMapper {

	@Select("select * from user where  id = #{userId}")
	User getById(Long userId);

	@Select("select * from user where openid = #{openid}")
	User getByOpenid(String openid);

	/**
	 * 插入数据
	 * @param user
	 */
	void insert(User user);

	/**
	 * 根据动态条件统计用户数量
	 * @param map
	 * @return
	 */
	Integer countByMap(Map map);
}
