package com.cglib.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cglib.base.BaseController;
import com.cglib.entity.Member;
//import com.cglib.service.MemberService;
import com.cglib.entity.User;
import com.cglib.service.MemberService;
import com.cglib.utils.ComUtil;
import com.cglib.utils.MD5Util;

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController{
	@Autowired
    private MemberService memberService;
	
	//新增跳转
	@RequestMapping("/mgt_add")
    public String mgt_add(){
    	return "/Test/mgt_add";
    }
	//会员主页跳转
	@RequestMapping("/index")
    public String index(){
    	return "/Test/member_index";
    }
	
	//会员新增
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response)throws IOException {
		try{
			String memberName=request.getParameter("memberName");
			String sex=request.getParameter("sex");
			String tel=request.getParameter("tel");
			String money=request.getParameter("money");
			String user=request.getParameter("user");
			Member m=new Member();
			m.setCreator(user);
			m.setMember_name(memberName);
			m.setSex(sex);
			m.setTel(tel);
			if(!ComUtil.isEmpty(money)){
				m.setBalance(Double.valueOf(money));
			}
			this.memberService.save(m);
			super.writeJson(response,1);
		}catch(Exception e){
			super.writeJson(response,"会员注册失败");
			e.getMessage();
			e.printStackTrace();
		}
	}
}
