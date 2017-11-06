<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>注册页面</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <%@include file="../../../../base.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/login.js"></script>
    <script type="text/javascript">
    //保存
      function save(){
    	  var loginName=$("#loginName").val();
    	  var pwd=$('#password').val();
    	  var newPwd=$('#newPwd').val();
    	  if(pwd!=newPwd){
    		  $.messager.alert('提示','两次输入的密码不一致！请重新填写');
    	  }else{
    		  $.ajax({
    		    	url:"/Haircut_SSH/login/zhuce",
    		    	type:"post",
    		    	async:false,
    		    	data:{"userName":loginName,"passWord":pwd},
    		    	success:function(data){
    		    		if(data=="1"){
    		    			$.messager.show({
    							title:'提示消息',
    							msg:"注册成功！",
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
      }
    </script>
  </head>
  
  <body>
    <div class="page">
	<div class="loginwarrp">
		<div class="logo">管理员注册</div>
        <div class="login_form">
				<li class="login-item">
					<span>用户名：</span>
					<input type="text" id="loginName" name="username" class="login_input">
                                        <span id="count-msg" class="error"></span>
				</li>
				<li class="login-item">
					<span>密　码：</span>
					<input type="password" id="password" name="password" class="login_input">
                                        <span id="password-msg" class="error"></span>
				</li>
				<li class="login-item">
					<span>确认密码：</span>
					<input type="password" id="newPwd" name="password" class="login_input">
                                        <span id="password-msg" class="error"></span>
				</li>
				<li class="login-sub">
					<input type="submit" id="save" value="保存" onclick="save();">
                    <input type="button" id="cancle" value="取消" onclick="cancle();"/> 
				</li>
		</div>
	</div>
	</div>
  </body>
</html>
