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

<body>
<form class="layui-form" action="" style="margin-top: 10px;margin-right: 20px;" id="form" enctype ="multipart/form-data">
<div class="layui-form-item">
    <label class="layui-form-label"><span style="color:red;" >*</span> 主机或ip：</label>
    <div class="layui-input-inline">
      <input type="text" name="ip" autocomplete="off" lay-verify="required" placeholder="主机名或X.X.X.X" class="layui-input">
    </div>
    <label class="layui-form-label"><span style="color:red;" >*</span> 数据库：</label>
    <div class="layui-input-inline" id="dataSource">
      <select name="dataSource"  lay-filter="dataSource" >
        <option value="">请选择</option>
        <option value="1">Mysql</option>
        <option value="2">Oracle</option>
      </select>
      </div>
      </div>
      <div class="layui-form-item">
      <label class="layui-form-label"><span style="color:red;" >*</span> 端口：</label>
      <div class="layui-input-inline">
        <input type="text" name="port" autocomplete="off" class="layui-input">
      </div>
      </div>
      <div class="layui-form-item" style="display: none;" id="oracleUse">
      <label class="layui-form-label" style="width: 100px"><span style="color:red;" >*</span> 服务名/SID：</label>
      <div class="layui-input-inline">
        <input type="radio" name="chooseName" value="1" title="服务名" lay-filter="radioCheck">
      <input type="radio" name="chooseName" value="2" title="SID" lay-filter="radioCheck">
      </div>
      <div class="layui-input-inline">
        <input type="text" name="serverName" autocomplete="off" class="layui-input" placeholder="请先选择服务名或SID">
      </div>
      </div>
      <div class="layui-form-item">
      <label class="layui-form-label"><span style="color:red;" >*</span> 用户名：</label>
    <div class="layui-input-inline">
      <input type="text" name="newUserName" autocomplete="off" lay-verify="required" placeholder="请输入用户名" class="layui-input" value="">
    </div>
    <label class="layui-form-label"><span style="color:red;" >*</span> 密码：</label>
    <div class="layui-input-inline">
      <input type="password" name="newPassword" autocomplete="off" lay-verify="required" placeholder="请输入密码" class="layui-input" value="">
    </div>
  </div>
  </form>
<script type="text/javascript">
var dataBase="";
var nameOrSID="";
layui.use('form', function(){
	   var form = layui.form;
	   form.on('select(dataSource)', function(data){
		   dataBase = data.value;
		   if(dataBase == 1){
			   $("input[name='port']").val("3306");
			   $("#oracleUse").css('display','none');
		   }else if(dataBase == 2){
			   $("input[name='port']").val("1521");
			   $("#oracleUse").css('display','');
			   layer.tips('本系统暂不支持oracle数据导入，请用其他导入方式！谢谢合作', '#dataSource', {
				   tips: [1, '#3595CC'],
				   time: 4000
				 });
		   }else{
			   $("input[name='port']").val("");
		   }
			
	   });
	   form.on('radio(radioCheck)', function(data){
		   nameOrSID = data.value;
		   if(nameOrSID == 1){
			   $("input[name='serverName']").attr("placeholder","请输入服务名");
		   }else if(nameOrSID == 2){
			   $("input[name='serverName']").attr("placeholder","请输入SID");
		   }else{
			   $("input[name='serverName']").attr("placeholder","请先选择服务名或SID");
		   }
		 });  
});
//调父页面连接方法
function readyToConnect(index){
	var ip = $("input[name='ip']").val();
	var port = $("input[name='port']").val();
	var userName = $("input[name='newUserName']").val();
	var password = $("input[name='newPassword']").val();
	var serverName="";
	var formData = new FormData();
	formData.append("ip",ip);
	formData.append("port",port);
	formData.append("index",index);
	formData.append("dataBase",dataBase);
	formData.append("userName",userName);
	formData.append("password",password);
	if(dataBase == 2){
		serverName = $("input[name='serverName']").val();
	}
	formData.append("nameOrSID",nameOrSID);
	formData.append("serverName",serverName);
	parent.connect(formData);
}
//取消
function closeIndex(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}
</script>

</body>
</html>
