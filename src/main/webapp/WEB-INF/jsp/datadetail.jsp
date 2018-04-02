<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
<title>Document</title>
<c:import url="global.jsp"></c:import>
<script type="text/javascript" src="/jQuery/jquery-1.10.2.min.js"></script>
</head>

<body style="overflow: auto;margin-left: 1%;margin-right: 1%;">
<table class="layui-table">
	<thead class="tr_1">
    <tr>
      <!-- 遍历列名 -->
      <c:forEach items="${datas}" var="data">
      	<th>${data}</th>
      </c:forEach>
    </tr> 
  </thead>
</table>
<div id="page" style="margin-top: -14px"></div>
<script type="text/javascript">
var dataId = '${dataId}';
$(function(){
	getDataDetail(currentPage, pageSize);
});
var oldData="";
var currentPage="1";
var pageSize="8";
//获取数据并渲染页面
function getDataDetail(current, limit){
	var formData = new FormData();
	formData.append("dataId",dataId);
	formData.append("current",current);
	formData.append("limit",limit);
	$.ajax({
		  type: 'POST',
		  url: 'getDatas',
		  data: formData,
		  processData : false, 
		  contentType : false,
		  success: function (result){
			  $("tr[name='dataDetail']").remove();
			  var datas = result.data;
			  var count = result.count;
			  layui.use(['laypage', 'layer'], function(){
				  var laypage = layui.laypage
				  laypage.render({
					    elem: 'page'
					    ,count: count
					    ,curr:current
					    ,layout: ['prev', 'page', 'next','count','limit']
						  ,limit:limit
						  ,limits:[8,20,30,50]
						  ,jump: function(obj, first){
							  if(!first){
								  currentPage = obj.curr;
								  pageSize = obj.limit;
								  getDataDetail(currentPage, pageSize);
							  }
						  }	  
					  });
				});
			  var x;
			  for(x in datas){//遍历map
				  var userWay = "";
				  var target = "";
				  var importWay = "";
				  
				  $tr = "<tr name='dataDetail' onmousemove='hover(this)' onmouseout='outHover(this)' id='"+x+"'>"
				  for(var y in datas[x]){
					  $tr += "<td>"+datas[x][y]+"</td>";
				  }
				  $tr += "</tr>";
				  $(".tr_1").after($tr);
			  }
			 
		  }
		  ,error: function(){
              //请求出错处理
			  layer.msg('出错了', {icon: 2,time: 1000});
          }  
		});
}

function hover(obj){
	$(obj).css("background-color","#eee");
}

function outHover(obj){
	$(obj).css("background-color","#FFFFFF");
}

function saveOne(id,currColumn,text,row,column){
	var index;
	$.ajax({
		  type: 'POST',
		  url: 'saveOne',
		  beforeSend:function(){
			  index = layer.load(0, {
				  shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
		  },
		  data: {id:id,currColumn:currColumn,text:text,row:row,column:column,currentPage:currentPage,pageSize:pageSize},
		  success: function (result){
			  layui.use('layer', function(){
					var layer = layui.layer;
					  layer.msg('保存成功！', {icon: 1,time: 1000,
							end:function(){
								getDataDetail(currentPage, pageSize);
								layer.close(index);
							}
						});
			  });
		  }
		  ,error: function(){
          //请求出错处理
          layer.close(index);
			  layer.msg('出错了', {icon: 2,time: 1000});
           }  
		});
}	

//取消
function closeIndex(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}
</script>

</body>
</html>
