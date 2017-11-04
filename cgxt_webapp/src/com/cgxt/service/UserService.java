package com.cgxt.service;

import com.cgxt.base.BaseService;
import com.cgxt.bean.User;

public interface UserService extends BaseService<User>{
  public boolean login(String username,String password);
}
