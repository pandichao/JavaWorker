package com.cglib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cglib.base.BaseController;
//import com.cglib.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController{
	//@Autowired
    //private MemberService memberService;
	
	@RequestMapping("/mgt_add")
    public String index(){
    	return "/Test/mgt_add";
    }
}
