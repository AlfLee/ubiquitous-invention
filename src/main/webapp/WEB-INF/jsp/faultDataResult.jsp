<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
<title>faultdatadetail</title>
<c:import url="global.jsp"></c:import>
<script type="text/javascript" src="/jQuery/jquery-1.10.2.min.js"></script>
</head>

<body style="overflow: auto;margin-left: 1%;margin-right: 1%;">
<table class="layui-table">
	<thead class="tr_1">
    <tr>
		<th>数据</th>
    </tr> 
  </thead>
</table>
<div id="page" style="margin-top: -14px"></div>
<script type="text/javascript">
var datas = "${datas}";
var oldData="";
var currentPage="1";
var pageSize="5";
$(function(){
	getDataDetail(currentPage, pageSize);
});
datas=datas.replace("[","");
datas=datas.replace("]","");
var datass=new Array();
datass=datas.split(",");
var count=0;
  for(var x in datass)
  {
	  count++;
	 // console.log(datass[x]);
  }
//获取数据并渲染页面
function getDataDetail(current, limit){
	  $("tr[name='dataDetail']").remove();
	  console.log(current);
	  layui.use(['laypage', 'layer'], function(){
		  var laypage = layui.laypage
		  laypage.render({
			    elem: 'page'
			    ,count: count
			    ,curr:!current ? "1" : current
			    ,layout: ['prev', 'page', 'next','count','limit']
				  ,limit:!limit ? "5" : limit
				  ,limits:[5,10,20,30,50]
				  ,jump: function(obj, first){
					  if(!first){
						  currentPage = obj.curr;
						//  console.log(currentPage);
						  pageSize = obj.limit;
						 // console.log(pageSize);
						  getDataDetail(currentPage, pageSize);
					  }
				  }	  
			  });
		});
	  var x=(current-1)*limit;
	  var showcount = current*limit;
	  if(showcount>count)
	  {
		  showcount = count;
	  }
	  //console.log(x);
	  for(;x<showcount;x++){
		//  console.log(datass[x]);
		  //console.log(x);
		  $tr = "<tr name='dataDetail' onmousemove='hover(this)' onmouseout='outHover(this)' id='"+x+"'>"
	      $tr += "<td>"+datass[x]+"</td></tr>";
		  $(".tr_1").append($tr);
	  }
}

function hover(obj){
	$(obj).css("background-color","#eee");
}

function outHover(obj){
	$(obj).css("background-color","#FFFFFF");
}



//取消
function closeIndex(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}
</script>

</body>
</html>
