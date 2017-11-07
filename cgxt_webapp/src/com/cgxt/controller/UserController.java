package com.cgxt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cgxt.base.BaseController;
import com.cgxt.base.BaseDaoImpl.Sqltype;
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
		//测试1000条jdbc新增方法时间差
		/*List<User> users = new ArrayList<User>();
		for (int i = 0; i < 1000; i++) {
			User user = new User();
			//user.setId(1);
			user.setAge("110");
			user.setUserName("aaa");
			user.setPassword("aaa");
			user.setRoseCode((long)1);
			users.add(user);
		}*/
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO USER (id,age,PASSWORD,roseCode,userName) VALUES((SELECT IFNULL(MAX(ab.id),0)+1 FROM USER ab),'110','123456',1,'admin'),");
		for (int i = 1; i <= 100; i++) {
			builder.append("((SELECT IFNULL(MAX(ab.id),0)+1 FROM USER ab),'110','123456',1,'admin')");
			if(i < 1000){
				builder.append(",");
			}else{
				builder.append(";");
			}
		}
		//子表user
		//map
		/*Map<String,Object> whereMap = new HashMap<String, Object>();
		whereMap.put("age","20");
		whereMap.put("userName","admin");*/
		try {
			//开始主子表一起插入
			//userService.saveOneToOneTable(rose,user,"roseCode",true);
			//改为jdbc插入
			//userService.JDBCsave(rose,"id",Sqltype.MYSQL);
			//userService.JDBCsave(user,"id",Sqltype.MYSQL);
			//userService.JdbcUpdate(user,whereMap);
			long curTime = System.currentTimeMillis();
			//userService.batchSave(users,"id",Sqltype.MYSQL);
			//userService.insertAll(users);
			userService.batchAddBySql(new String(builder.toString()));
			System.out.println("所用时间为："+(System.currentTimeMillis() - curTime)+"ms");
			//users = null;
			//userService.JdbcDelete(user);
			//在插入子表
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
