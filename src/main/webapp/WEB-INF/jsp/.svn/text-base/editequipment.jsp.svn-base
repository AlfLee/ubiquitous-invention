<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Document</title>
<c:import url="global.jsp"></c:import>
<script type="text/javascript" src="/jQuery/jquery-1.10.2.min.js"></script>
</head>

<body>
<div>
	<form class="layui-form" action="" style="margin-top: 10px;margin-right: 20px;">
	<input type="hidden" name="flag" value="" />
  <div class="layui-form-item">
    <label class="layui-form-label"><span style="color:red;" >*</span> 设备名称：</label>
    <div class="layui-input-block">
      <input type="text" name="name" autocomplete="off" lay-verify="required" placeholder="请输入名称" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item layui-form-text">
    <label class="layui-form-label">简介：</label>
    <div class="layui-input-block">
      <textarea name="detail" placeholder="请输入内容" class="layui-textarea"></textarea>
    </div>
  </div>
  
  </form>
  <div class="" align="center">
    <button class="layui-btn" onclick="save()">保存</button>
    <button class="layui-btn"  onclick="closeIndex()">取消</button>
  </div>
  </div>
<script type="text/javascript">
$(function (){
	//初始化判断是新增还是编辑
	if(parent.flag == 1){
		//编辑：给输入框赋值
		$("input[name='name']").val(parent.parentName);
		$("textarea[name='detail']").val(parent.parentDetail);
	}
	//将flag保存至edit页面 提交表单时需一并提交
	$("input[name='flag']").val(parent.flag);
});
//保存
function save(){
	var name = $("input[name='name']").val().trim();
	if(name == "" || name == null){
		layui.use('layer', function(){
			  var layer = layui.layer;
			  layer.msg('请输入设备名称！', {icon: 7,time: 1000});
	 	 });
		return;
	}
	var detail = $("textarea[name='detail']").val();
	var flag = $("input[name='flag']").val();
	var id = parent.parentId;
	$.ajax({
		  type: 'POST',
		  url: 'saveEquip',
		  data: {id:id,flag: flag,parentId:parent.parentId,name:name,detail:detail},
		  success: function (result){
			  if(result.code == "0"){
				  layui.use('layer', function(){
					  var layer = layui.layer;
					  layer.msg('保存成功！', {icon: 1,time: 1000,
							end:function(){
								parent.location.reload();
							}
						});
			 	 });
			  }else{
				  layui.use('layer', function(){
					  var layer = layui.layer;
					  layer.msg('保存失败，请稍后再试！', {icon: 2,time: 1000});
			 	 });
				  
			  }
		  }
		  ,error: function(){
            //请求出错处理
            layui.use('layer', function(){
					  var layer = layui.layer;
					  layer.msg('出错了', {icon: 2,time: 1000});
			 	 });
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
