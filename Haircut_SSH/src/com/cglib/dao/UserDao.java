package com.cglib.dao;

import java.util.List;
import java.util.Map;

import com.cglib.base.BaseDao;
import com.cglib.base.Page;
import com.cglib.entity.User;



public interface UserDao extends BaseDao<User>{

	public List<Map<String,Object>> getAll();
	//��¼��֤
	public List<User> checkLogin(String userName,String pwd);
	//��ѯ��Ա�б�
	public Page<User> queryList(Map<String,Object> params,Integer rows,Integer page);
	//注册
	public boolean insert(Map<String,Object> parms);
}
