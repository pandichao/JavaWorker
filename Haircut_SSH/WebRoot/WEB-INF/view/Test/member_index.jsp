<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <%@include file="../../../../base.jsp" %>
    <script type="text/javascript">
    var datagrid;
    $(function(){
    	datagrid=$("#data_table").datagrid({
	 		   //url:'',
	           fit : true,
	           border:false,
	           pageSize:10,
	           pageList:[10,20,100,200],//选择分页的数据量，一次展示多少数据
		       rownumbers:true,//显示当前行号
		       nowrap:true,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能
		       striped:false,//显示斑马效果，交替显示
		       collapsible:true,//显示折叠的按钮
		       loadMsg:'数据加载中.....',//当联网获取数据的时候提示的消息
		       singleSelect:true,//设置为true的时候，一次只能选中一个
		       fitColumns:true,//允许表格自动拉伸，根据父容器进行拉伸
		       nowrap : true,// 设置为true，当数据长度超出列宽时将会自动截取
		       idField:'pk_roles',//根据主键进行记录，当我们翻页以后系统也可以根据主键知道我们选中了哪些
		       pagination:true,//设置有分页的功能
		    columns:[[    
		     	  {field:'pk_roles',title:'序号',hidden:true},
	              {field:'cversion',title:'版本号',hidden:true},
	              {field:'role_code',title:'角色编码',hidden:true},
	              {field:'creator',title:'创建者',hidden:true},
	              {field:'role_name',title:'名称',width:100,align:'center'},
	              {field:'role_desc',title:'描述',width:180,align:'center'},
	              {field:'status',title:'状态',width:80,align:'center',
	                  formatter: function(value,row,index){
	                      if (row.status==1){
	                          return "<font color='red'>停用</font>";
	                      } else if(row.status==0){
	                          return "<font color='green'>正常</font>";
	                      }
	                  }
	              },
	              {field:'aaa',title:'操作',width:140,align:'center',
	                  formatter: function(value,row,index){
	                          return sta +
	                                  "<a href='javascript:void(0)'   onClick='inputRole("+row.pk_roles+")' >编辑</a> &nbsp" +
	                                  del+
	                                  "<a  href='javascript:void(0)'  onClick='viewRole("+row.pk_roles+")' >查看</a> &nbsp" +
	                                  "<a href='javascript:void(0)'   onClick='privSetRole("+row.pk_roles+")' >权限</a> ";
	                      
	                  }
	              }
			      ]],toolbar: [{
		                iconCls: 'icon-add',
		                text:'新增',
		                handler: function(){
		                	alert(123);
		                }
		            }]
			  });
    });
    //查询
    function QueryData(){
    	
    }
    </script>
  </head>
  
  <body class="easyui-layout" >
    <div data-options="region:'north'">
    	<div style="padding:2px;background-color: #ECECED;">
            <div style="padding:1px;height:auto">
		                会员名称：<input id=hospitalName class="easyui-textbox" />
		        <a id="btnQuery" class="easyui-linkbutton" iconCls="icon-search" onclick="QueryData();">查询</a>
            </div>
        </div>
    </div>
    <div data-options="region:'center'">
    	<table id="data_table"></table>
    </div>
  </body>
</html>
