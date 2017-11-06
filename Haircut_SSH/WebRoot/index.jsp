<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
	<%@include file="base.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/login.js"></script>
  </head>
  <script type="text/javascript">
  //登录
  function LoginDate(){
	  var userName=$("#username").val();
	  var passWord=$("#password").val();
	  $.ajax({
	    	url:"/Haircut_SSH/login/cheklogin",
	    	type:"post",
	    	async:false,
	    	data:{"userName":userName,"passWord":passWord},
	    	success:function(data){
	    		if(data=="1"){
	    			$.messager.show({
						title:'提示消息',
						msg:"登录成功！",
						timeout:3000,
						showType:'slide'
					});
	    			window.location.href="/Haircut_SSH/User/mgt_index";
	    		}else{
	    			$.messager.show({
						title:'提示消息',
						msg:data,
						timeout:3000,
						showType:'slide'
					});
	    		}
	    	}
	    });
  }
  //注册
  function zhuce(){
	  window.location.href="/Haircut_SSH/User/zhuce";
  }
  </script>
  <body>
  <div class="page">
	<div class="loginwarrp">
		<div class="logo">管理员登陆</div>
        <div class="login_form">
				<li class="login-item">
					<span>用户名：</span>
					<input type="text" id="username" name="username" class="login_input">
                                        <span id="count-msg" class="error"></span>
				</li>
				<li class="login-item">
					<span>密　码：</span>
					<input type="password" id="password" name="password" class="login_input">
                                        <span id="password-msg" class="error"></span>
				</li>
				<li class="login-sub">
					<input type="submit" name="button" value="登录" onclick="LoginDate();"><span id="error" style="color: red"></span>
                    <input type="button" name="zhuce" value="注册" onclick="zhuce();"/> 
				</li>
		</div>
	</div>
	</div>
  </body>
</html>
