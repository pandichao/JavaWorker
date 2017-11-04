<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@include file="../base.jsp"%>
<style type="text/css">
a {
	text-decoration: none;
}

body {
	margin: 0px;
}

#header {
	width: 100%;
	height: 30px;
	background-color: #E0EFFF;
	padding-top: 5px;
	padding-bottom: 10px;
}

#header .logo {
	margin-left: 50px;
	font-size: 24px;
	font-family: 微软雅黑;
}

#picture {
	width: 100%;
	height: 750px;
}

.panel-title {
	text-align: center;
	font-size: 16px;
}

#bootom {
	padding-top: 50px;
	width: 100%;
	height: 100px;
	background: #eaf2ff;
}

#bootom_content {
	margin-left: 100px;
	width: 80%;
	text-align: center;
	font-size: 0.8em;
}

p {
	line-height: 20px;
}
</style>
<script type="text/javascript">
   $(function(){
     $('#login').bind('click',function(){
       $('#ff').form('submit', {
			url: '/cgxt_webapp/user/login',
			onSubmit: function(){
				var isValid = $(this).form('validate');
				if (!isValid){
					$.messager.alert('异常','请填写正确的用户信息以后在进行提交','error');	
				}
				return isValid;	// 返回false终止表单提交
			},
			success: function(data){
			  if(typeof data == 'string'){
			    data = JSON.parse(data);
			  }
			  if(data['error'] == false){
			     $.messager.alert('成功',data['msg'],'info');	
			  }else{
			     $.messager.alert('失败',data['msg'],'error');
			  }
			}
		});
     });
     
     //测试点击注册时候修改一下admin的账号密码
     $('#test').bind('click',function(){
       $.ajax({
        url : '/cgxt_webapp/user/updateUser',
        async : true,
        type : 'POST',
        dataType : 'JSON',
        success : function(data){
          $.messager.alert('失败',data['msg'],'error');
        },
        error : function(err){
          $.messager.alert('失败','出现异常了','error');
        }
       });
     });
   });
</script>
</head>
<script type="text/javascript">
	  $('#p').panel('move',{
	    left:100,
	    top:100
  }); 
</script>
<body>
	<div id="header">
		<div class="logo" onclick="window.location.href='index.html'">
			<strong>用户登录</strong>
		</div>
	</div>
	<div id="picture"
		style="background:url(images/index.jpg) no-repeat; background-size: cover;">
		<div
			data-options=" region:'east',split:true,style:{position:'absolute',right:50,top:150}"
			class="easyui-panel " title="用户登录"
			style="width:300px;text-align: center;">
			<div style="padding:10px 60px 20px 60px">
				<form id="ff" class="easyui-form" method="post"
					data-options="novalidate:true">
					<table cellpadding="5">
						<tr>
							<td><input class="easyui-textbox" name='name'
								data-options="prompt:'账号',validType:'name'" iconCls="icon-man"
								iconAlign=left style="width:100%;height:32px" /></td>
						</tr>
						<tr>
							<td><input class="easyui-textbox" name='password'
								data-options="prompt:'密码',validType:'password'"
								iconCls="icon-lock" iconAlign=left
								style="width:100%;height:32px"></input></td>
						</tr>
					</table>
				</form>
				<div style="text-align:center;padding:5px; ">
					<a id="login" href="#" rel="external nofollow" rel="external nofollow"
						class="easyui-linkbutton" style="width:45%;height:32px">登录</a>
				    <a
						id="test" href="#" rel="external nofollow" rel="external nofollow"
						class="easyui-linkbutton" style="width:45%;height:32px">注册</a>
				</div>
			</div>
		</div>
	</div>
	<div id="bootom">
		<div id="bootom_content">
			<p>
				<strong>关于我们 法律声明 服务条款 联系我们</strong>
			</p>
			<p>地址：江西省南昌市经济开发区天祥大道 邮箱：330000 Copyright © 2017 - 2018
				hacker_pangdaxing@qq.com 版权所有</p>
			<p>建议使用IE8以上版本浏览器 E-mail：hacker_pandaxing@qq.com</p>
		</div>
	</div>
</body>
</html>
