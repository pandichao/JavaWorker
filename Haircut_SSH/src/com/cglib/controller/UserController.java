package com.cglib.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cglib.base.BaseController;
import com.cglib.service.UserService;



//ע��controller
@Controller
@RequestMapping("/User")
public class UserController extends BaseController{
	 //ע��service
    @Autowired
    private UserService userService;
    
    @RequestMapping("/index")
    public String index(){
    	return "/Test/index";
    }
    
    @RequestMapping("/mgt_index")
    public String mgt_index(String name,HttpServletRequest request) throws Exception{
    	request.setCharacterEncoding("UTF-8");
    	String a = new String(name.getBytes("iso-8859-1"),"utf-8"); 
    	//System.out.println("登录名："+a);
    	request.setAttribute("userName", a);
    	return "/Test/mgt_index";
    }
    @RequestMapping("/zhuce")
    public String zhuce(){
    	return "/Test/zhuce";
    }
    //返回登录
    @RequestMapping("/cancle")
    public String cancle(){
    	return "../../index";
    }
    
    @RequestMapping("/getUserAll")
    public void testGetAll(){
    	List<Map<String,Object>> datas = userService.getAll();
    	super.ajaxJson(datas);
    }
    
}
