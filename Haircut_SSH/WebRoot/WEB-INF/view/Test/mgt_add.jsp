<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <%@include file="../../../../base.jsp" %>
    <script type="text/javascript">
    function dosubmit(){
    	var memberName=$('#memberName').val();
    	var sex=$("input[name='sex']:checked").val();
    	var tel=$('#tel').val();
    	var money=$('#money').val();
    	var user=$('#user').val();
    	$.ajax({
	    	url:"/Haircut_SSH/member/add",
	    	type:"post",
	    	async:false,
	    	data:{
	    		"memberName":memberName,
	    		"sex":sex,
	    		"tel":tel,
	    		"money":money,
	    		"user":user
	    		},
	    	success:function(data){
	    		if(data=="1"){
	    			$.messager.show({
						title:'提示消息',
						msg:"会员注册成功！",
						timeout:3000,
						showType:'slide'
					});
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
    </script>
  </head>
  
  <body class="easyui-layout" >
  <div data-options="region:'center'" align="center"> 
  	<table >
  		<tr style="line-height: 35px;">
  			<td style="color: #003399" align="right">姓名：</td>
  			<td><input type="text" class="easyui-textbox" id="memberName" data-options="required:true"/></td>
  		</tr>
  		<tr style="line-height: 35px;">
  			<td style="color: #003399" align="right">性别：</td>
  			<td>
  				<input type="radio" name="sex" value="男" checked="checked" id="Like1"></input><label for="Like1">男</label>
                <input type="radio" name="sex" value="女" id="Like2"></input><label for="Like2">女</label>
            </td>
  		</tr>
  		<tr style="line-height: 35px;">
  			<td style="color: #003399" align="right">电话：</td>
  			<td><input type="text" class="easyui-numberbox" id="tel" data-options="required:true"/></td>
  		</tr>
  		<tr style="line-height: 35px;">
  			<td style="color: #003399" align="right">首充金额：</td>
  			<td><input type="text" class="easyui-numberbox" id="money" data-options="min:0,precision:2" style="width: 80px"/>元</td>
  		</tr>
  		<tr style="line-height: 35px;">
  			<td style="color: #003399" align="right">卡号：</td>
  			<td><input type="text"  class="easyui-textbox" id="card"  readOnly="true"/></td>
  		</tr>
  		<tr style="line-height: 35px;">
  			<td colspan="2" align="center">
  			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="dosubmit()" id="btn-save" icon="icon-save">保存</a>&nbsp;
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="cancel()" id="btn-cancel" icon="icon-cancel">取消</a>
  			</td>
  		</tr>
  	</table>
  </div>
   
  </body>
</html>
