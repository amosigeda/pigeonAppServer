package com.pigeon.spring.test;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 基于Restful风格架构测试
 * 
 * @author fengmingbao
 * @history 2016-10-15 下午3:00:12
 */
@Controller
@RequestMapping("/serviceTest")
public class DekotaController {

	/** 日志实例 */
	private static final Logger logger = Logger.getLogger(DekotaController.class);

	@RequestMapping(value = "/hello", produces = "application/json;charset=UTF-8")
	public @ResponseBody String hello() {
		logger.info("你好！hello");
		return "你好！hello";
	}
	
	
}
