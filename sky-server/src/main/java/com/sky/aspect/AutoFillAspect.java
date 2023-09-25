package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * -----------------------------------------------
 *
 * @author ：niki
 * @date ：Created in 2023/9/25 20:01
 * @description： 实现公共字段自动填充处理逻辑
 * @version: $
 * -----------------------------------------------
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
	//切入点
	@Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
	public void autoFillPointCut(){}

	/**
	 * 前置通知，在通知中进行公共字段的赋值
	 */
	@Before("autoFillPointCut()")
	public void autoFill(JoinPoint joinPoint) throws NoSuchMethodException {
		log.info("开始进行公共字段自动填充...");

		/**
		 * 1.获取到当前被拦截的方法上的数据库操作类型
		 * 2.获取到当前被拦截的方法的参数--实体对象
		 * 3.准备自动填入的值数据
		 * 4.根据当前不同的数据类型，填入相应的值
		 */
		//1.
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
		OperationType operationType = autoFill.value();

		//2.
		Object[] args = joinPoint.getArgs();
		if (args == null || args.length == 0)
			return;
		Object entity = args[0];

		//3.
		LocalDateTime now = LocalDateTime.now();
		Long currentId = BaseContext.getCurrentId();

		//4.
		if (operationType == OperationType.INSERT){
			try {
				Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME,LocalDateTime.class);
				Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER,Long.class);
				Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
				Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);

				//通过反射为对象属性赋值
				setCreateTime.invoke(entity,now);
				setCreateUser.invoke(entity,currentId);
				setUpdateTime.invoke(entity,now);
				setUpdateUser.invoke(entity,currentId);

			}catch (Exception e){
				e.printStackTrace();  //抛出异常
			}
		} else if (operationType == OperationType.UPDATE) {
			try {
				Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
				Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);

				//通过反射为对象属性赋值
				setUpdateTime.invoke(entity,now);
				setUpdateUser.invoke(entity,currentId);
			}catch (Exception e){
				e.printStackTrace();  //抛出异常
			}
		}
		}
	}



