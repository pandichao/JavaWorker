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
		  //���Ϊtrue��˵����ǰ����û�����
		  map.put("error",false);
		  map.put("msg","��¼�ɹ���������ת����ҳ");
		  super.toJson(map);
	  }else{
		  map.put("error",true);
		  map.put("msg","��¼ʧ�ܣ��˺Ż�����������쳣������������");
		  super.toJson(map);
	  }
  }
}
