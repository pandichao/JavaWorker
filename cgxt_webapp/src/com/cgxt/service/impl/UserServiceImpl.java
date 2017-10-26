package com.cgxt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgxt.base.BaseServiceImpl;
import com.cgxt.bean.User;
import com.cgxt.dao.UserDao;
import com.cgxt.service.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
    @Autowired
	UserDao userDao;
    
	@Override
	public boolean login(String username, String password) {
		return userDao.login(username, password);
	}

}
