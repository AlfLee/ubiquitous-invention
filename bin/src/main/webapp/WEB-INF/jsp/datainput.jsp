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
<div style="text-align: left;">
<form class="layui-form" action="">
<div class="layui-form-item">
	<div class="layui-inline">
     <label class="layui-form-label">数据名称：</label>
      <div class="layui-input-inline">
        <input type="tel" name="phone" lay-verify="required|phone" autocomplete="off" class="layui-input" placeholder="请输入名称">
      </div> 
      <button class="layui-btn"><i class="layui-icon">&#xe615;</i><span></button>
      </div>
      <div class="layui-inline">
      <label class="layui-form-label">数据用途：</label>
    <div class="layui-input-block">
      <input type="radio" name="dataUseWay" value="1" title="分析" checked="">
      <input type="radio" name="dataUseWay" value="2" title="训练">
    </div>
    </div>
    <div class="layui-inline">
    <label class="layui-form-label">导入方式：</label>
    <div class="layui-input-inline">
      <select name="importWay" >
        <option value="">请选择</option>
        <option value="1">文件导入</option>
        <option value="2">数据库导入</option>
        <option value="3">实时导入</option>
      </select>
      </div>
    </div>
</div>
</form>
</div>
<div class="layui-btn-group" style="text-align: right;">
    <button class="layui-btn" onclick="edit(0)">增加</button>
    <button class="layui-btn " onclick="edit(1)">编辑</button>
    <button class="layui-btn">删除</button>
  </div>
<table class="layui-table">
	<thead class="tr_1">
    <tr>
      <th style="width:20px;"><input type="checkbox" onclick="selectAll(this);" /></th>
      <th>数据名称</th>
      <th>数据用途</th>
      <th>导入时间</th>
      <th>导入方式</th>
    </tr> 
  </thead>
  <tr onclick='checkCheckBox(this)' onmousemove='hover(this)' onmouseout='outHover(this)'>
  	<td style="width:20px;"><input type="checkbox" name='box' /></td>
  	<td>1</td>
  	<td>1</td>
  	<td>1</td>
  	<td>1</td>
  </tr>
  <tr onclick='checkCheckBox(this)' onmousemove='hover(this)' onmouseout='outHover(this)'>
  	<td style="width:20px;"><input type="checkbox" name='box' /></td>
  	<td>1</td>
  	<td>1</td>
  	<td>1</td>
  	<td>1</td>
  </tr>
  <tr onclick='checkCheckBox(this)' onmousemove='hover(this)' onmouseout='outHover(this)'>
  	<td style="width:20px;"><input type="checkbox" name='box' /></td>
  	<td>1</td>
  	<td>1</td>
  	<td>1</td>
  	<td>1</td>
  </tr>
  <tr onclick='checkCheckBox(this)' onmousemove='hover(this)' onmouseout='outHover(this)'>
  	<td style="width:20px;"><input type="checkbox" name='box' /></td>
  	<td>1</td>
  	<td>1</td>
  	<td>1</td>
  	<td>1</td>
  </tr>
</table>
<div id="demo5" style="margin-top: -20px"></div>
</div>
<script type="text/javascript">
//开启HASH
layui.use(['laypage', 'layer'], function(){
  var laypage = layui.laypage
laypage.render({
  elem: 'demo5'
  ,count: 500
});
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
var parentId=1;
var flag=1;
//新增或编辑
function edit(num){
	flag = num;
	var info = num == 0 ? "新增" : "编辑" ;
	layui.use('layer', function(){ //独立版的layer无需执行这一句
		  var layer = layui.layer;
		  if(parentId == null){
				layer.msg('请先选择设备！', {icon: 7,time: 1000});
				return;
			}
		  var index = layer.open({
			  type: 2,
			  title:info,
			  area: ['650px', '400px'],
			  fixed: false, //不固定
			  maxmin: num != 0,//是否需要最大、小化
			  content:['editDataInfo', 'no']
			});
	});
}

//点击节点触发
function clickNode(node){
	parentId = node.id;
	$.ajax({
		  type: 'POST',
		  url: 'getEquip',
		  data: {id:node.id,parentId:node.parentId},
		  success: function (result){
			  $tr = "<tr onclick='checkCheckBox(this)' onmousemove='hover(this)' onmouseout='outHover(this)'><td style='width:20px;'><input type='checkbox' name='box' /></td>"
			  $tr += "<td>"+node.name+"</td>";
			  $tr += "<td>"+result.data+"</td>";
			  $tr += "<td>"+result.msg+"</td>";
			  $tr += "<td>"+result.msg+"</td></tr>";
			  $(".tr_1").after($tr);
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
</script>

</body>
</html>
