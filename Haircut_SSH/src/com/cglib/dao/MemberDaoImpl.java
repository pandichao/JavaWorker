package com.cglib.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglib.base.BaseDao;
import com.cglib.base.BaseDaoImpl;
import com.cglib.entity.Member;
import com.cglib.entity.User;
@Repository
public class MemberDaoImpl extends BaseDaoImpl<Member> implements MemberDao{
//	@Autowired
//	BaseDao<User> baseDao;
}
