<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript"
	src="<%=basePath%>jquery/jquery-1.11.1.min.js"></script>
<!-- DataTables js和css(修改了部分源码和样式)  文档官网：http://www.datatables.club/ -->
<link rel="stylesheet" type="text/css" href="<%=basePath%>datatable/css/jquery.dataTables.css">
<script type="text/javascript" charset="utf8" src="<%=basePath%>datatable/js/jquery.dataTables.js"></script>
<!--根据项目做了二次封装-->
<script type="text/javascript" charset="utf8" src="<%=basePath%>HaoYangCustomJs/common.js"></script>
<!-- spin插件用于表格加载的loding -->
<script type="text/javascript" charset="utf8" src="<%=basePath%>spin-2.1.0/jquery.spin.merge.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
/**
 说明：启用fexled布局 必须留一列不要设置宽度
**/
<body>
      <div id="tableContainer" style='position: relative;width:100%;'>
	<!--第二步：添加如下 HTML 代码-->
	<table id="table_id_example" style="width: 100%;" class="cell-border fixed">
		<thead>
			<tr>
				<th>客户名称</th>
				<th>客户性别</th>
				<th>客户年龄</th>
				<th>地址</th>
				
			</tr>
		</thead>
	</table>
</div>
</body>
</html>
<script type="text/javascript">
<!--第三步：初始化Datatables-->
	$(document).ready(function() {
		var table = $("#table_id_example").initAsynTable({
			url : "/dataTables/CustomerSevlet",
			getAjaxParameterCallback:function(d){
			   /**
			      这里是批量参数填写位置
			   **/
			   d["startTime"]=$("#startTime").val();
			   return d;
			},
			"columns" : [ {
				"data" : "name",
				"className" : "ellipsis",
				 width : "80px",
				 render:function(data){
					return "<span style='color:#f66305' title='"+data+"'>" + data + "</span>"
				 }

			}, {

				"data" : "age",
				"className" : "ellipsis",
				width : "100px",
			},{
				"data" :null,
				"className" : "ellipsis",
				"render" : function(data, type, full, meta) {
				  return data?"男":"女";
				},
				width : "80px",

			}, {
				"data" : "address",
				"className" : "ellipsis",
			},

			]
		})

	});
</script>