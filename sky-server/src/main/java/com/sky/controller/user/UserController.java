package com.sky.controller.user;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * -----------------------------------------------
 *
 * @author ：niki
 * @date ：Created in 2023/9/28 19:04
 * @description：C端用户相关接口
 * @version: $
 * -----------------------------------------------
 */
@RestController("wxUserController")
@RequestMapping("/user/user")
@Slf4j
@Api(tags = "C端用户相关接口")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JwtProperties jwtProperties;

	/**
	 * 微信用户登录
	 * 代码实现可以参考员工登录，实现流程相同。原理参考微信小程序登录逻辑图
	 * @param userLoginDTO
	 * @return
	 */
	@PostMapping("/login")
	@ApiOperation("微信登录")
	public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
		log.info("微信用户登录：{}",userLoginDTO.getCode());
		//创建要登陆的用户
		User user = userService.wechatLogin(userLoginDTO);

		//获取相应的请求参数和jwt令牌
		Map<String,Object>  claims = new HashMap<>();
		claims.put(JwtClaimsConstant.USER_ID, user.getId());
		String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(),
				jwtProperties.getUserTtl(),
				claims);
		UserLoginVO userLoginVO = UserLoginVO.builder()
				.id(user.getId())
				.openid(user.getOpenid())
				.token(token)
				.build();
		return Result.success(userLoginVO);
	}
}
