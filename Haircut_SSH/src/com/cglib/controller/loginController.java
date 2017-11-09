package com.cglib.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cglib.base.BaseController;
import com.cglib.entity.User;
import com.cglib.service.UserService;
import com.cglib.utils.MD5Util;

//ע��controller
@Controller
@RequestMapping("/login")
public class loginController extends BaseController{
	// ע��service
	@Autowired
	private UserService userService;

	//登录
	@RequestMapping("/cheklogin")
	public void cheklogin(HttpServletRequest request, HttpServletResponse response)throws IOException {
		String userName=request.getParameter("userName");
		String passWord=request.getParameter("passWord");
		String pwd=MD5Util.encodeHapPassword(userName, passWord);
		System.out.println(pwd);
		List<User> list=this.userService.checkLogin(userName, pwd);
		if(list.size()>0){
			super.writeJson(response,1);
		}else{
			super.writeJson(response,"登录失败");
		}
	}
	
	@RequestMapping("/zhuce")
	public void zhuce(HttpServletRequest request, HttpServletResponse response)throws IOException {
		try{
			String userName=request.getParameter("userName");
			String passWord=request.getParameter("passWord");
			String pwd=MD5Util.encodeHapPassword(userName, passWord);
			System.out.println(pwd);
			Map<String,Object> parms=new HashMap<String,Object>();
			parms.put("userName", userName);
			parms.put("passWord", passWord);
			User user=new User();
			user.setUserName(userName);
			user.setPassword(pwd);
			this.userService.save(user);
			super.writeJson(response,1);
			//boolean a= this.userService.insert(parms);
			/*if(a){
				super.writeJson(response,1);
			}else{
				super.writeJson(response,"注册失败");
			}*/
		}catch(Exception e){
			super.writeJson(response,"注册失败");
			e.getMessage();
			e.printStackTrace();
		}
	}
}
