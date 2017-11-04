package com.cgxt.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cgxt.base.BaseController;
import com.cgxt.bean.Rose;
import com.cgxt.bean.User;
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

	/*@RequestMapping("/updateUser")
  public void updateUser(HttpServletResponse response){
	  User u = (User)userService.findUniqueResult("from User u where u.id = 1 ");
	  u.setUserName("woshichaoge");
	  Map<String,Object> map = new HashMap<String, Object>();
	  try {
		userService.JdbcUpdate(u);
		map.put("error",false);
		map.put("msg","SpringJDBC放入成功，修改用户名成功");
	} catch (Exception e) {
		e.printStackTrace();
		map.put("error",true);
		map.put("msg","SpringJDBC使用失败");
	}
	  super.toJson(map);
  }*/

	@RequestMapping("/updateUser")
	public void updateUser(HttpServletResponse response){
		Map<String,Object> map = new HashMap<String, Object>();
		//创建主表--角色表
		Rose rose = new Rose();
		rose.setRoseName("超级管理员权限");
		//子表user
		User user = new User();
		user.setAge("20");
		user.setUserName("admin");
		user.setPassword("123456");
		try {
			//开始主子表一起插入
			userService.saveOneToOneTable(rose,user,"roseCode",true);
			map.put("error",false);
			map.put("msg","主子表一起插入成功，恭喜哈");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error",true);
			map.put("msg","baseDao中主子表一起插入失败，别想这么写了，智障");
		}
		super.toJson(map);
	}
}
