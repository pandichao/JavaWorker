package com.cglib.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cglib.base.BaseServiceImpl;
import com.cglib.dao.UserDao;
import com.cglib.entity.User;


@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
    //ע��Dao
	@Autowired
	private UserDao userDao;

	@Override
	public List<Map<String,Object>> getAll() {
		return userDao.getAll();
	}
	//登录
	public List<User> checkLogin(String userName,String pwd){
		return userDao.checkLogin(userName,pwd);
	}
	//注册
	public boolean insert(Map<String,Object> parms){
		return userDao.insert(parms);
	}
}
