<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <!-- <title>会员管理系统</title> -->
    <%@include file="../../../../base.jsp" %>
    <script type="text/javascript">
      $(function(){
    	  window.setInterval('getMyTime()',1000);
      });
      //获取当前系统时间
      function getMyTime(){
    		var d = new Date();
    		var y = d.getFullYear();
    		var m= d.getMonth()+1;
    		var day = d.getDate();
    		var h = d.getHours();
    		var fen = d.getMinutes();
    		var miao = d.getSeconds();
    		
    		var xq = d.getDay();

    		var info= y+'-'+m+'-'+day+' '+h+':'+fen+':'+miao+'  星期'+xq;
    		$('#gd').text(info);
    	}
      //新增选项卡
      function openPage(url,title){
		if($('#tt').tabs('exists',title)){
		
		$('#tt').tabs('select',title);
		}else{
			$('#tt').tabs('add',{
			  title:title,    
			  href:url,   
			  closable:true
		});
        }		
	}
    //取消
    function cancle(){
    	window.location.href="/Haircut_SSH/User/cancle";
    }
    </script>
  </head>
  
  <body id="cc" class="easyui-layout">
  <div data-options="region:'north',title:'会员管理系统',split:true" style="height:100px;background-color: #dddddd;">
  <!-- <font size="5px" style="黑体">会员管理系统</font> -->
  	<font size="4px">当前登录用户：</font><font size="4px" color="red">${userName }</font>
  	<div id="time" style="float: right;">现在时间：【<font color="red" id="gd" size="4px"></font>】<br><br>
  		<a href="#"onclick="cancle();">退出登录</a>
  	</div>
  </div>   
    <!-- <div data-options="region:'south',title:'底部',split:true" style="height:100px;"></div>  --> 
    <div data-options="region:'west',title:'功能列表',split:true" style="width:12%;">
    	<div id="aa" class="easyui-accordion" data-options="fit:true,border:true" >
			    <div title="考勤申请" data-options="iconCls:'icon-reload',selected:true" style="padding:10px;">
			          <a href="javascript:openPage('addDaka.jsp','会员注册')">会员注册</a><br/>
			        <a href="javascript:openPage('Qiudakaym.jsp','消费记录')">消费记录</a><br/>
			    </div>
			    
		</div>
    </div>
    <div data-options="region:'center',title:'欢迎'" style="padding:5px;background:#eee;">
    <div id="tt" class="easyui-tabs" data-options="fit:true">
    </div>
    </div>
  </body>
</html>
