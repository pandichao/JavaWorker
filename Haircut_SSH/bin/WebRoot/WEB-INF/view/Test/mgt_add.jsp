<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <%@include file="../../../../base.jsp" %>
    <script type="text/javascript">
    </script>
  </head>
  
  <body class="easyui-layout">
  <div data-options="region:'center'" align="center">
  	<table >
  		<tr>
  			<td style="color: #003399">姓名：</td>
  			<td><input type="text" id="memberName"/></td>
  		</tr>
  		<tr>
  			<td style="color: #003399">性别：</td>
  			<td>
  				<input type="radio" name="sex" value="男" checked="checked" id="Like1"></input><label for="Like1">男</label>
                <input type="radio" name="sex" value="女" id="Like2"></input><label for="Like2">女</label>
            </td>
  		</tr>
  		<tr>
  			<td style="color: #003399">电话：</td>
  			<td><input type="text" id="tel"/></td>
  		</tr>
  		<tr>
  			<td style="color: #003399">卡号：</td>
  			<td><input type="text" id="card"  readOnly="true"/></td>
  		</tr>
  		<tr>
  			<td style="color: #003399">首充金额：</td>
  			<td><input type="text" id="money"/></td>
  		</tr>
  	</table>
  </div>
   
  </body>
</html>
