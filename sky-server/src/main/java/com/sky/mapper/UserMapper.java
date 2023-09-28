package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

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

	@Select("select * from user where openid = #{openid}")
	User getByOpenid(String openid);

	/**
	 * 插入数据
	 * @param user
	 */
	void insert(User user);
}
