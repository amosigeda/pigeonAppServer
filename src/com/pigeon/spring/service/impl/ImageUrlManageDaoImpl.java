package com.pigeon.spring.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigeon.spring.base.SuperDao;
import com.pigeon.spring.entity.T_IMAGE_URL;
import com.pigeon.spring.service.ImageUrlManageDao;

/**
 * 图片上传接口实现类
 * @author liufeng
 * @date 2017-4-27
 */
@Repository("imageUrlManageDao")
public class ImageUrlManageDaoImpl extends SuperDao implements ImageUrlManageDao {

	/**
	 * 保存图片
	 */
	@Transactional("transactionManager")
	public void saveImage(String id,List<T_IMAGE_URL> list) {
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				T_IMAGE_URL iu = list.get(i);
				iu.setDYNAMIC_ID(id);
				getSession().save(iu);
			}
		}
		getSession().flush();
	}

	/**
	 * 保存图片
	 */
	@Transactional("transactionManager")
	public void addImage(T_IMAGE_URL iu) throws Exception {
		getSession().save(iu);
		getSession().flush();
	}
	
}
