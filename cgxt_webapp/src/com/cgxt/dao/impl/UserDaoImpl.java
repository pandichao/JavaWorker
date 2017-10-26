package com.cgxt.dao.impl;

import org.springframework.stereotype.Repository;
import com.cgxt.base.BaseDaoImpl;
import com.cgxt.bean.User;
import com.cgxt.dao.UserDao;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

	@Override
	public boolean login(String name, String password) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(id) from User  u where u.userName='"+name+"' and u.password='"+password+"'");
		System.out.println(Long.parseLong(this.findUniqueResultBySql(buffer.toString()).toString()) > 0);
		return Long.parseLong(this.findUniqueResultBySql(buffer.toString()).toString()) > 0;
	}

}
