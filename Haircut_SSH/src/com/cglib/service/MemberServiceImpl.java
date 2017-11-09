package com.cglib.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglib.base.BaseServiceImpl;
import com.cglib.dao.MemberDao;
import com.cglib.entity.Member;

@Service
public class MemberServiceImpl extends BaseServiceImpl<Member> implements MemberService{
	@Autowired
	private MemberDao memberDao;
}
