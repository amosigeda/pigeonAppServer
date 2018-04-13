package com.pigeon.spring.service;

import java.util.List;

import com.pigeon.spring.entity.T_IMAGE_URL;

/**
 * 图片上传接口
 * @author liufeng
 * @date 2017-4-27
 */
public interface ImageUrlManageDao {

	/**
	 * 保存图片
	 */
	void saveImage(String id,List<T_IMAGE_URL> list) throws Exception;
	
	/**
	 * 保存图片
	 */
	void addImage(T_IMAGE_URL iu) throws Exception;
}
