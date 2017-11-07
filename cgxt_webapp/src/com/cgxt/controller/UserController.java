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
		//����1000��jdbc��������ʱ���
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
		System.out.println(builder.toString());
		//�ӱ�user
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
			long curTime = System.currentTimeMillis();
			//userService.batchSave(users,"id",Sqltype.MYSQL);
			//userService.insertAll(users);
			userService.batchAddBySql(new String(builder.toString()));
			System.out.println("����ʱ��Ϊ��"+(System.currentTimeMillis() - curTime)+"ms");
			//users = null;
			//userService.JdbcDelete(user);
			//�ڲ����ӱ�
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
