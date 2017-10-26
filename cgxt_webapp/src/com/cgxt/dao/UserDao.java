package com.cgxt.dao;

import com.cgxt.base.BaseDao;
import com.cgxt.bean.User;

public interface UserDao extends BaseDao<User>{
  public boolean login(String name,String password);
}
