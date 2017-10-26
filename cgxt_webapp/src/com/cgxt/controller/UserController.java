package com.cgxt.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cgxt.base.BaseController;
import com.cgxt.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
  @Autowired
  UserService userService;
  
  @RequestMapping("/login")
  public void login(String name,String password){
	  boolean login = userService.login(name, password);
	  Map<String,Object> map = new HashMap<String, Object>();
	  if(login){
		  //如果为true，说明当前这个用户存在
		  map.put("error",false);
		  map.put("msg","登录成功，正在跳转到主页");
		  super.toJson(map);
	  }else{
		  map.put("error",true);
		  map.put("msg","登录失败，账号或者密码出现异常，请重新输入");
		  super.toJson(map);
	  }
  }
}
