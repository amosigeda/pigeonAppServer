package com.pigeon.spring.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.xml.registry.infomodel.User;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.pigeon.spring.service.UserLoginService;

@Component
@Aspect
public class PermissionAspect {
	
	/** 日志实例 */
	private static final Logger logger = Logger.getLogger(PermissionAspect.class);

	@Before("@annotation(com.pigeon.controller.dao.UserAccess)")
	public void checkPermission(JoinPoint joinPoint) throws Exception {
		logger.info("=========开始验证是否登陆=========");
		//String methodName = joinPoint.getSignature().getName();
		//Object target = joinPoint.getTarget();
		//Method method = getMethodByClassAndName(target.getClass(), methodName); // 得到拦截的方法
		Object[] args = joinPoint.getArgs();
		HttpServletRequest request = (HttpServletRequest) args[0];
		String token = request.getParameter("token");
		User user = (User) UserLoginService.loginUserMap.get(token);
		if (user == null) {
			logger.error("验证不通过！");
			throw new Exception("没有权限");
		}
	}

	/**
	 * 根据类和方法名得到方法
	 */
	@SuppressWarnings("unchecked")
	public Method getMethodByClassAndName(Class c, String methodName) {
		Method[] methods = c.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		return null;
	}

}
