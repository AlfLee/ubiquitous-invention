<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Document</title>
<style type="text/css">
body > div.dddd { width:78%; float:right;margin-right: 1%}
.ssss { position:fixed; width: 20%;height:96%; padding-top:20px; border:none; border-right:1px solid #eee;overflow: auto;}

body > div .layui-btn-group{display: block;}

.ztree_a { background-color: #eee !important;}
</style>
<c:import url="global.jsp"></c:import>
<script type="text/javascript" src="/jQuery/jquery-1.10.2.min.js"></script>
</head>

<body>
<iframe src="equipTree" class="ssss"></iframe>
<div class="dddd">
<fieldset class="layui-elem-field layui-field-title">
  <legend>数据列表</legend>
</fieldset>
<div style="text-align: left">
<form class="layui-form">
<div class="layui-form-item">
	<div class="layui-inline">
     <label class="layui-form-label">数据名称：</label>
      <div class="layui-input-inline">
        <input type="tel" id="dataName" autocomplete="off" class="layui-input" placeholder="请输入名称">
      </div> 
      </div>
      <div class="layui-inline">
      <label class="layui-form-label">数据用途：</label>
    <div class="layui-input-block">
      <input type="radio" name="dataUseWay" value="1" title="分析">
      <input type="radio" name="dataUseWay" value="2" title="训练">
    </div>
    </div>
    <div class="layui-inline">
    <div class="layui-btn-group" >
    <input type="button" value="查询" class="layui-btn layui-btn-sm" id="searchData">
    <input type="reset" value="重置" class="layui-btn layui-btn-sm" onclick="selectVal='';">
    </div>
  </div>
      <div>
      <div class="layui-inline">
    <label class="layui-form-label">导入方式：</label>
    <div class="layui-input-inline">
      <select name="importWay" lay-filter="importWay">
        <option value="">请选择</option>
        <option value="1">文件导入</option>
        <option value="2">数据库导入</option>
        <option value="3">实时导入</option>
      </select>
      </div>
    </div>
     <div class="layui-inline">
    <label class="layui-form-label">数据目的：</label>
    <div class="layui-input-block">
      <input type="radio" name="dataTarget" value="1" title="诊断">
      <input type="radio" name="dataTarget" value="2" title="预测">
    </div>
    </div>
    </div>
</div>
</form>
</div>
<div class="layui-btn-group" style="text-align: right;">
    <button class="layui-btn" onclick="edit(0)">新增</button>
    <button class="layui-btn " onclick="edit(1)">编辑</button>
    <button class="layui-btn" onclick="lookUp()" >数据详情</button>
    <button class="layui-btn" onclick="del();">删除</button>
  </div>
<table class="layui-table">
	<thead class="tr_1">
    <tr class="">
      <th style="width:20px;"><input type="checkbox" onclick="selectAll(this);" /></th>
      <th>数据名称</th>
      <th>数据用途</th>
      <th>数据目的</th>
      <th>导入时间</th>
      <th>导入方式</th>
    </tr> 
  </thead>
  
</table>
<div id="page" style="margin-top: -14px"></div>
</div>
<script type="text/javascript">

$(function(){
	var jsonData = {id:1};
	clickNode(jsonData);
});
//选中样式
function checkCheckBox(dom) {
    var flag = $(dom).find("input:checkbox").is(':checked');
    if(flag === true){
    	$(dom).find("input:checkbox").prop("checked",false);
    }else{
    	$(dom).find("input:checkbox").prop("checked",true);
    }
}

//必须有 否则CheckBox跟select框无样式
layui.use('form', function(){
	   var form = layui.form;
});
var parentId;
var flag;
//新增或编辑
function edit(num){
	flag = num;
	var info,a,b;
	if(num == 0){
		info = "新增";
		a = "100%";
		b = "100%";
	}else{
		var data = $(".layui-table").find("input[name='box']:checkbox:checked");
		if(!data || data.length < 1){
			layer.msg('请选择一条数据！', {icon: 7,time: 1000});
			return;
		}
		if(data.length > 1){
			layer.msg('请只选择一条数据！', {icon: 7,time: 1000});
			return;
		}
		var dataId = data[0].id;
		info = "编辑";
		a = "650px";
		b = "400px";
	}
	layui.use('layer', function(){ //独立版的layer无需执行这一句
		  var layer = layui.layer;
		  var index = layer.open({
			  type: 2,
			  title:info,
			  offset: 't',
			  area: [a, b],
			  fixed: false, //不固定
			  maxmin: num != 0,//是否需要最大、小化
			  content:['editDataInfo?dataId='+dataId, 'no']
			});
	});
}

//查看
function lookUp(){
	var data = $(".layui-table").find("input[name='box']:checkbox:checked");
	if(!data || data.length < 1){
		layer.msg('请选择一条数据！', {icon: 7,time: 1000});
		return;
	}
	if(data.length > 1){
		layer.msg('请只选择一条数据！', {icon: 7,time: 1000});
		return;
	}
	var dataId = data[0].id;
	layui.use('layer', function(){ //独立版的layer无需执行这一句
		  var layer = layui.layer;
		  var index = layer.open({
			  type: 2,
			  title:'数据详情',
			  offset: 't',
			  area: ['750px', '500px'],
			  fixed: false, //不固定
			  maxmin: true,
			  content:['lookUp?dataId='+dataId]
			});
	});
}
var currentPage;
var pageSize;
var loadingFlag=0;
//点击节点触发
function clickNode(node){
	parentId = node.id;
	var dataName = $("#dataName").val();
	var dataUseWay = $("input[name='dataUseWay']:checked").val();
	if(!dataUseWay){
		dataUseWay = "";
	}
	var dataTarget = $("input[name='dataTarget']:checked").val();
	if(!dataTarget){
		dataTarget = "";
	}
	var formData = new FormData();
	formData.append("dataName",dataName);
	formData.append("dataUseWay",dataUseWay);
	formData.append("dataTarget",dataTarget);
	formData.append("equipId",parentId);
	formData.append("importWay",selectVal);
	formData.append("current",!node.current ? "1" : node.current);
	formData.append("limit",!node.limit ? "5" : node.limit);
	var index1;
	$.ajax({
		  type: 'POST',
		  url: 'getDataInfo',
		  data: formData,
		  beforeSend:function(){
			  if(loadingFlag == 1){//看是不是第一次加载页面 1代表不是
				  $("#saveButton").html("查询中");
				  layui.use('layer', function(){ //独立版的layer无需执行这一句
					  var layer = layui.layer;
					  index1 = layer.load(0, {
						  shade: [0.5,'#fff'] //0.1透明度的白色背景
						});
				  });
			  }else{
				  loadingFlag = 1;
			  }
		  },
		  processData : false, 
		  contentType : false,
		  success: function (result){
			  $("tr[name='datas']").remove();
			  var datas = result.data;
			  var count = result.count;
			  layui.use(['laypage', 'layer'], function(){
				  var laypage = layui.laypage
				  laypage.render({
					    elem: 'page'
					    ,count: count
					    ,curr:!node.current ? "1" : node.current
					    ,layout: ['prev', 'page', 'next','count','limit']
						  ,limit:!node.limit ? "5" : node.limit
						  ,limits:[5,10,20,30,50]
						  ,jump: function(obj, first){
							  if(!first){
								  currentPage = obj.curr;
								  pageSize = obj.limit;
								  var jsonData = {id:parentId,current:obj.curr,limit:obj.limit};
								   clickNode(jsonData);
							  }
						  }	  
					  });
				});
			  var x;
			  for(x in datas){
				  var userWay = "";
				  var target = "";
				  var importWay = "";
				  $tr = "<tr name='datas' style='background-color:#FFFFFF'><td style='width:20px;'><input type='checkbox' name='box' id='"+datas[x].id+"'/></td>"
				  $tr += "<td name='dataName'>"+datas[x].dataName+"</td>";
				  
				  if(datas[x].dataUseWay == 1){
					  userWay = "分析";
				  }else if(datas[x].dataUseWay == 2){
					  userWay = "训练";
				  }
				  $tr += "<td name='userWay'>"+userWay+"</td>";
				 
				  if(datas[x].dataTarget == 1){
					  target = "诊断";
				  }else if(datas[x].dataTarget == 2){
					  target = "预测";
				  }
				  $tr += "<td name='dataTarget'>"+target+"</td>";
				 
				  $tr += "<td>"+datas[x].import_time+"</td>";
				  if(datas[x].importWay == 1){
					  importWay = "文件导入";
				  }else if(datas[x].importWay == 2){
					  importWay = "数据库导入";
				  }else if(datas[x].importWay == 3){
					  importWay = "实时导入";
				  }
				  $tr += "<td>"+importWay+"</td></tr>";
				  $(".tr_1").append($tr);
			  }
			  layui.use('layer', function(){
				  var layer = layui.layer;
				  layer.close(index1);
			  });
			  
		  }
		  ,error: function(){
              //请求出错处理
			  layer.msg('出错了', {icon: 2,time: 1000});
			  if(!index1){
				  layer.close(index1);
			  }
          }  
		});
}

function hover(obj){
	$(obj).css("background-color","#eee");
}

function outHover(obj){
	$(obj).css("background-color","#FFFFFF");
}

function selectAll(obj){
	var ch = $("input[name='box']");
	if($(obj).is(':checked') === false){
		for(var i=0;i<ch.length;i++){
			ch[i].checked = false;
		}
	}else{
		for(var i=0;i<ch.length;i++){
			ch[i].checked = true;
		}
	}
}
//删除
function del(){
	var data = $(".layui-table").find("input[name='box']:checkbox:checked");
	if(!data || data.length < 1){
		layer.msg('请至少选择一条数据！', {icon: 7,time: 1000});
		return;
	}
	var dataIds="";
	for(var x = 0; x < data.length; x++){
		dataIds = dataIds + data[x].id+",";
	}
	layui.use('layer', function(){
		var layer = layui.layer;
		layer.confirm('您确定要删除吗？', {
			  btn: ['确定','取消'] //按钮
			  ,offset : ['30%' , '30%']
			}, function(){
				dele(dataIds);
			});
	});
	function dele(dataIds){
		var index;
		$.ajax({
			  type: 'POST',
			  url: 'deleteData',
			  beforeSend:function(){
				  index = layer.load(0, {
					  shade: [0.1,'#fff'] //0.1透明度的白色背景
					});
			  },
			  data: {dataIds:dataIds},
			  success: function (result){
				  layer.msg('删除成功！', {icon: 1,time: 1000,
						end:function(){
							var jsonData = {id:parentId,current:currentPage,limit:pageSize};
							clickNode(jsonData);
							layer.close(index); 
						}
					});
			  }
			  ,error: function(){
	          //请求出错处理
	          layer.close(index); 
				  layer.msg('出错了', {icon: 2,time: 1000});
	           }  
			});
	}	
}

$("#searchData").bind("click",function(){
	var jsonData = {id:parentId,flag:1};
	clickNode(jsonData);
});

var selectVal="";
//必须有 否则CheckBox跟select框无样式
layui.use('form', function(){
	   var form = layui.form;
	   form.on('select(importWay)', function(data){
		   selectVal = data.value;
	   });
	  
});
</script>

</body>
</html>
