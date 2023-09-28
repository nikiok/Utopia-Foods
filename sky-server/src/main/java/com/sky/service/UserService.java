package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import org.springframework.stereotype.Service;

/**
 * -----------------------------------------------
 * @author ：niki
 * @date ：Created in 2023/9/28 19:07
 * @description： C端用户操作接口
 * @version: $
 * -----------------------------------------------
 */
@Service
public interface UserService {
	User wechatLogin(UserLoginDTO userLoginDTO);
}
