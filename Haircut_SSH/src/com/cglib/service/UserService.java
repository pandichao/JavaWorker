package com.cglib.service;

import java.util.List;
import java.util.Map;
import com.cglib.base.BaseService;
import com.cglib.entity.User;



public interface UserService extends BaseService<User>{
	
	public List<Map<String,Object>> getAll();
	//��¼��֤
	public List<User> checkLogin(String userName,String pwd);
	
	//注册
	public boolean insert(Map<String,Object> parms);
}
