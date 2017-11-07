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

	/*@RequestMapping("/updateUser")
  public void updateUser(HttpServletResponse response){
	  User u = (User)userService.findUniqueResult("from User u where u.id = 1 ");
	  u.setUserName("woshichaoge");
	  Map<String,Object> map = new HashMap<String, Object>();
	  try {
		userService.JdbcUpdate(u);
		map.put("error",false);
		map.put("msg","SpringJDBC����ɹ����޸��û����ɹ�");
	} catch (Exception e) {
		e.printStackTrace();
		map.put("error",true);
		map.put("msg","SpringJDBCʹ��ʧ��");
	}
	  super.toJson(map);
  }*/

	@RequestMapping("/updateUser")
	public void updateUser(HttpServletResponse response){
		Map<String,Object> map = new HashMap<String, Object>();
		//��������--��ɫ��
		Rose rose = new Rose();
		rose.setRoseName("��������ԱȨ��");
		//�ӱ�user
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < 100000; i++) {
			User user = new User();
			//user.setId(1);
			user.setAge("110");
			user.setUserName("admin123");
			user.setPassword("123456789");
			user.setRoseCode((long)1);
			users.add(user);
		}
		//user.setRoseCode((long)1);
		//map
		/*Map<String,Object> whereMap = new HashMap<String, Object>();
		whereMap.put("age","20");
		whereMap.put("userName","admin");*/
		try {
			//��ʼ���ӱ�һ�����
			//userService.saveOneToOneTable(rose,user,"roseCode",true);
			//��Ϊjdbc����
			//userService.JDBCsave(rose,"id",Sqltype.MYSQL);
			//userService.JDBCsave(user,"id",Sqltype.MYSQL);
			//userService.JdbcUpdate(user,whereMap);
			//userService.JdbcDelete(user);
			//�ڲ����ӱ�
			long time = System.currentTimeMillis();
			//userService.batchJdbcSave(users,"id",Sqltype.MYSQL);
			userService.insertAll(users);
			long ntime = System.currentTimeMillis();
			System.out.println("ʱ���Ϊ��"+(ntime - time)/1000+"s"+(ntime - time)%1000+"ms");
			map.put("error",false);
			map.put("msg","���ӱ�һ�����ɹ�����ϲ��");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error",true);
			map.put("msg","baseDao�����ӱ�һ�����ʧ�ܣ�������ôд�ˣ�����");
		}
		super.toJson(map);
	}
}
