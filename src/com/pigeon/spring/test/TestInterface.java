package com.pigeon.spring.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pigeon.spring.entity.T_USER_INFO;
import com.pigeon.spring.service.DynamicManageDao;
import com.pigeon.spring.service.UserManageDao;


@Component  
@Transactional  
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestInterface {
	
	@Autowired
	private UserManageDao userManageDao;
	@Autowired
	private DynamicManageDao dynamicManageDao;
	
	@Test
	public void testQueryFriends() throws Exception{
		List<String> list = dynamicManageDao.queryFriends("1");
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<list.size();i++){
			if(list.size() == 1){
				sb.append(list.get(i));
//				continue;
			}
			if(i == list.size() - 1){
				sb.append(list.get(i));
				break;
			}
			sb.append(list.get(i)+",");
		}
		System.out.println(sb);
	}
	
	@Test
	@Rollback(false)
	public void testRegedit() throws Exception {
		T_USER_INFO user = userManageDao.getUserInfoByObjID("1");
		System.out.println(user.getUSER_PWD());
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("jack");
		list.add("tom");
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<list.size();i++){
			if((list.size() == 1)||(i == list.size() - 1)){
				sb.append(list.get(i));
				continue;
			}
			sb.append(list.get(i)+",");
		}
		System.out.println(sb);
	}
	
}
