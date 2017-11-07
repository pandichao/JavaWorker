package com.cglib.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.cglib.base.BaseDaoImpl;
import com.cglib.base.Page;
import com.cglib.entity.User;


//ע��
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Override
	public List<Map<String,Object>> getAll() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select u.id,u.age,u.userName,u.`password` from user as u");
		return this.findBySql(buffer.toString());
	}
	//登录
	public List<User> checkLogin(String userName,String pwd){
		String sql = "select a.id,a.userName from user a " +
				" where a.userName='"+userName+"' and a.password='"+pwd+"'";
	Session session = this.getSession();
	SQLQuery sqlQuery = session.createSQLQuery(sql);
	sqlQuery.addScalar("id", IntegerType.INSTANCE);
	sqlQuery.addScalar("userName", StringType.INSTANCE);
	sqlQuery.setResultTransformer(Transformers.aliasToBean(User.class));
	List<User> list=sqlQuery.list();
	return list;
	}
	//��ѯ��Ա�б�
	public Page<User> queryList(Map<String,Object> params,Integer rows,Integer page){
		Page<User> pages = new Page<User>();
		pages.setPage(page);
        pages.setRows(rows);
        String sqlWhere = "";
        return pages;
	}
	//注册
	public boolean insert(Map<String,Object> parms){
		String userName="";
		String pwd="";
		if(parms.get("userName")!=null){
			userName=parms.get("userName").toString();
		}
		if(parms.get("passWord")!=null){
			pwd=parms.get("passWord").toString();
		}
		String sql = "insert into user(id,userName,password)";
		return true;
	}
}
