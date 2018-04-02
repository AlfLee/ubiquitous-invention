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
    <label class="layui-form-label"><span style="color:red;" >*</span> 数据名称：</label>
    <div class="layui-input-block">
      <input type="text" name="name" autocomplete="off" lay-verify="required" placeholder="请输入名称" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label"><span style="color:red;" >*</span> 数据用途：</label>
    <div class="layui-input-block">
      <input type="radio" name="dataUseWay" value="1" title="分析" checked="">
      <input type="radio" name="dataUseWay" value="2" title="训练">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label"><span style="color:red;" >*</span> 导入方式：</label>
    <div class="layui-input-inline">
      <select name="importWay"  lay-filter="selectWay">
        <option value="">请选择</option>
        <option value="1">文件导入</option>
        <option value="2">数据库导入</option>
      </select>
      </div>
  </div>
  <div class="layui-form-item" style="display: none;" id="file">
    <label class="layui-form-label"><span style="color:red;" >*</span> 选择文件：</label>
    <div class="layui-input-inline">
		<input type="file" name="file" id="choose">
      </div>
  </div>
  <div class="layui-form-item" style="display: none;" id="database1">
    <label class="layui-form-label"><span style="color:red;" >*</span> 主机或ip：</label>
    <div class="layui-input-inline">
      <input type="text" name="name" autocomplete="off" lay-verify="required" placeholder="主机名或X.X.X.X" class="layui-input">
    </div>
    <label class="layui-form-label"><span style="color:red;" >*</span> 数据库：</label>
    <div class="layui-input-inline">
      <select name="dataSource"  lay-filter="dataSource">
        <option value="">请选择</option>
        <option value="1">Mysql</option>
        <option value="2">Oracle</option>
      </select>
      </div>
      </div>
      <div class="layui-form-item" style="display: none;" id="database2">
      <label class="layui-form-label"><span style="color:red;" >*</span> 用户名：</label>
    <div class="layui-input-inline">
      <input type="text" name="name" autocomplete="off" lay-verify="required" placeholder="请输入用户名" class="layui-input">
    </div>
    <label class="layui-form-label"><span style="color:red;" >*</span> 密码：</label>
    <div class="layui-input-inline">
      <input type="password" name="name" autocomplete="off" lay-verify="required" placeholder="请输入密码" class="layui-input">
    </div>
  </div>
  </form>
  <div class="" align="center">
   <button class="layui-btn" onclick="" id="database3" style="display: none">连接测试</button>
    <button class="layui-btn" onclick="">保存</button>
    <button class="layui-btn" onclick="closeIndex()">取消</button>
  </div>
  </div>
  <script src="/plugins/layui/layui.all.js"></script>
<script type="text/javascript">
$(function (){
	if(parent.flag == 1){
		$("input[name='name']").val(parent.parentName);
		$("textarea[name='detail']").val(parent.parentDetail);
	}
	$("input[name='flag']").val(parent.flag);
});

function save(){
	var name = $("input[name='name']").val();
	var detail = $("textarea[name='detail']").val();
	var flag = $("input[name='flag']").val();
	var id = parent.parentId;
	$.ajax({
		  type: 'POST',
		  url: '',
		  data: {id:id,flag: flag,parentId:parent.parentId,name:name,detail:detail},
		  success: function (result){
				layer.msg('保存成功！', {icon: 1,time: 1000,
					end:function(){
						closeIndex();
					}
				});
		  }
		  ,error: function(){
            //请求出错处理
			  layer.msg('出错了', {icon: 2,time: 1000});
        }  
		});
}
//必须有 否则CheckBox跟select框无样式
layui.use('form', function(){
	   var form = layui.form;
	   form.on('select(selectWay)', function(data){
		   var value = data.value;
			if(value == 1){
				$("#file").attr("style","");
				
			}else{
				$("#file").attr("style","display:none");
			}
			if(value == 2){
				$("#database1").attr("style","");
				$("#database2").attr("style","");
				$("#database3").attr("style","");
			}else{
				$("#database2").attr("style","display:none");
				$("#database1").attr("style","display:none");
				$("#database3").attr("style","display:none");
			}
	   });
	  
});
//取消
function closeIndex(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}
</script>

</body>
</html>
