<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>用户管理</title>
<script type="text/javascript" src="/jQuery/jquery-1.10.2.min.js"></script>
<style type="text/css">
body > div.form { width:78%; float:right;margin-right: 1%;margin-top: 20px;}
.ssss {position:fixed; width: 20%;height:96%; padding-top:20px; border:none; border-right:1px solid #eee;overflow: auto;}
</style>
<c:import url="global.jsp"></c:import>
</head>

<body>
<!-- <iframe src="equipTree" class="ssss"></iframe> -->
<div class="form">
<fieldset class="layui-elem-field layui-field-title">
  <legend>用户详情</legend>
</fieldset>
<form class="layui-form layui-form-pane">
<input type="hidden" name="id" value="" />
<input type="hidden" name="parentId" value="" />
  <div class="layui-form-item">
    <label class="layui-form-label">用户名</label>
    <div class="layui-input-block">
      <input type="text" name="name" autocomplete="off" disabled="disabled"  lay-verify="required"  class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">密码</label>
    <div class="layui-input-block">
      <input type="text" name="parentName"  disabled="disabled"  autocomplete="off" class="layui-input">
    </div>
  </div>
 <div class="layui-form-item">
    <label class="layui-form-label">邮件</label>
    <div class="layui-input-block">
      <input type="text" name="parentName"  disabled="disabled"  autocomplete="off" class="layui-input">
    </div>
  </div><div class="layui-form-item">
    <label class="layui-form-label">等级</label>
    <div class="layui-input-block">
      <input type="text" name="parentName"  disabled="disabled"  autocomplete="off" class="layui-input">
    </div>
  </div>
  </form>
  <div style="text-align:right;">
	  <button class="layui-btn" onclick="edit(0);" lay-filter="">新增</button>
	  <button class="layui-btn" onclick="edit(1);" lay-filter="">编辑</button>
	  <button class="layui-btn" onclick="del();" lay-filter="">删除</button>
  </div>
</div>
<script type="text/javascript">
var parentName;
var parentDetail;
var flag;
//删除
function del(){
	layui.use('layer', function(){
		var layer = layui.layer;
		/* if(parentId == null){
			layer.msg('请先选择用户！', {icon: 7,time: 1000});
			return;
		} */
		layer.confirm('您确定要删除吗？', {
			  btn: ['确定','取消'] //按钮
			  ,offset : ['30%' , '30%']
			}, function(){
				dele();
			});
	});
	
}
function dele(){
	$.ajax({
		  type: 'POST',
		  url: 'deleteEquip',
		  beforeSend:function(){
		  
		  },
		  data: {id:parentId},
		  success: function (result){
			  layer.msg('删除成功！', {icon: 1,time: 1000,
					end:function(){
						location.reload();
					}
				});
		  }
		  ,error: function(){
          //请求出错处理
			  layer.msg('出错了', {icon: 2,time: 1000});
           }  
		});
}
//新增或编辑
function edit(num){
	flag = num;
	var info = num == 0 ? "新增" : "编辑" ;
	layui.use('layer', function(){ //独立版的layer无需执行这一句
		  var layer = layui.layer;
		  /* if(parentId == null){
				layer.msg('请先选择用户！', {icon: 7,time: 1000});
				return;
			} */
		  layer.open({
			  type: 2,
			  title:info,
			  shade: 0.4,
			  area: ['650px', '350px'],
			  fixed: false, //不固定
			  maxmin: true,
			  content: 'editUser'
			});
	});
}
var parentId;
//点击节点触发
function clickNode(node){
	parentId = node.id;
	$.ajax({
		  type: 'POST',
		  url: 'getEquip',
		  data: {id:node.id,parentId:node.parentId},
		  success: function (result){
			  $("input[name='name']").val(node.name);
			  $("input[name='parentName']").val(result.data);
			  $("textarea[name='detail']").val(result.msg);
			  parentName = $("input[name='name']").val();
			  parentDetail = $("textarea[name='detail']").val();
		  }
		  ,error: function(){
              //请求出错处理
			  layer.msg('出错了', {icon: 2,time: 1000});
          }  
		});
}

</script>

</body>
</html>
