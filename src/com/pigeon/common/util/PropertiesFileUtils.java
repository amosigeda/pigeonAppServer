package com.pigeon.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 文件工具类
 * 自动加载conf.properties文件的内容到内存,在需要用到的地方直接调用PropertiesFileUtils.getProperties().getProperty("key")即可
 * @author fengmb
 * 2016-9-5
 */
public class PropertiesFileUtils {

	private static Logger logger = Logger.getLogger(PropertiesFileUtils.class);
	public static Properties propes = null;
	public static Properties propes_code = null;
	public static Properties push = null;
	
	static {
		try {
			loads();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
	}

	synchronized static public void loads() throws IOException, Exception {
		InputStream is = PropertiesFileUtils.class.getResourceAsStream("/resources/conf.properties");
		Properties dbProps = new Properties();
		dbProps.load(is);
		propes = dbProps;
		
		InputStream is_codes = PropertiesFileUtils.class.getResourceAsStream("/resources/codes.properties");
		Properties codesProps = new Properties();
		codesProps.load(is_codes);
		propes_code = codesProps;
		
		InputStream push_codes = PropertiesFileUtils.class.getResourceAsStream("/resources/push.properties");
		Properties pushProps = new Properties();
		pushProps.load(push_codes);
		push = pushProps;
	}
	
	public static Properties getProperties() {
		return propes;
	}
	
	public static Properties getPropertiesByCodes() {
		return propes_code;
	}
	
	public static Properties getPropertiesPush(){
		return push;
	}
}
