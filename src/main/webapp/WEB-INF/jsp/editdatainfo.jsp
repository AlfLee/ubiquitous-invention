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
<div>
	<form class="layui-form" action="" style="margin-top: 10px;margin-right: 20px;" id="form" enctype ="multipart/form-data">
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
      <input type="radio" name="dataUseWay" id="dataUseWay_1" value="1" title="分析" ${dataInfo.dataUseWay == 1 ?'checked' :''} >
      <input type="radio" name="dataUseWay" id="dataUseWay_2" value="2" title="训练" ${dataInfo.dataUseWay == 2 ?'checked' :''}>
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label"><span style="color:red;" >*</span> 数据目的：</label>
    <div class="layui-input-block">
      <input type="radio" name="dataTarget" id="dataTarget_1" value="1" title="诊断" ${dataInfo.dataTarget == 1 ?'checked' :''}>
      <input type="radio" name="dataTarget" id="dataTarget_2" value="2" title="预测" ${dataInfo.dataTarget == 2 ?'checked' :''}>
    </div>
  </div>
  <div class="layui-form-item" id="importWay">
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
		<input type="file" name="file" id="chooseFile">
      </div>
  </div>
  <div class="layui-form-item" id="database3" style="display: none;">
      <label class="layui-form-label"><span style="color:red;" >*</span> 选择库：</label>
    <div class="layui-input-inline">
      <select name="database"  lay-filter="chooseDatabase">
        <option value="" id="chooseDatabase">请选择</option>
      </select>
    </div>
    <label class="layui-form-label" style="width: 150px"><span style="color:red;" >*</span> 选择表或执行sql：</label>
    <div class="layui-input-inline"  style="width: 195px" id="choosetable">
      <input type="radio" name="chooseSelectWay" value="1" title="选择表" lay-filter="chooseSelectWay">
      <input type="radio" name="chooseSelectWay" value="2" title="执行sql" lay-filter="chooseSelectWay">
    </div>
    <div class="layui-input-inline" style="display: none;" id="tableSelect">
      <select name="table" id="table" lay-filter="chooseTable">
        <option value="" id="chooseTable">请选择</option>
      </select>
    </div>
    <div class="layui-input-inline" style="display: none;" id="inputSql">
      <textarea style="height: 100px;width: 400px" id="textarea" autocomplete="off" lay-verify="required" placeholder="" class="layui-input"></textarea>
    </div>
  </div>
  </form>
 <div class="" align="center">
    <button class="layui-btn" onclick="saveAll()" id="saveButton">保存</button>
    <button class="layui-btn" onclick="closeIndex()">取消</button>
  </div>
  </div>
<script type="text/javascript">
$(function (){
	if(parent.flag == 1){
		var dataName = '${dataInfo.dataName}';
		$("#importWay").css("display","none");
		$("input[name='name']").val(dataName);
	}
	$("input[name='flag']").val(parent.flag);
	$("textarea[name='text']").attr("placeholder","注：选择sql导入数据，数据列名会用A1，A2，A3...替代");
});

function saveAll(){
	var id = parent.parentId;
	if(parent.flag == 1){
		var dataUseWay = $("input[name='dataUseWay']:checked").val();
		var dataTarget = $("input[name='dataTarget']:checked").val();
		var formData = new FormData();
		formData.append("dataName",'${dataInfo.dataName}');
		formData.append("dataId",'${dataInfo.id}');
		formData.append("dataUseWay",dataUseWay);
		formData.append("dataTarget",dataTarget);
		var indexQ;
		$.ajax({
			  type: 'POST',
			  url: 'saveData',
			  cache: false,
			  data: formData,
			  beforeSend:function(){
				  $("#saveButton").html("保存中");
				  indexQ = layer.load(0, {
					  shade: [0.5,'#fff'] //0.1透明度的白色背景
					});
			  },
			  processData : false, 
			  contentType : false,
			  success: function (result){
					if(result.code == "0"){
						layer.msg('保存成功！', {icon: 1,time: 1000,
							end:function(){
								layer.close(indexQ); 
								var jsonData = {id:id,current:parent.currentPage,limit:parent.pageSize};
								parent.clickNode(jsonData);
								closeIndex();
							}
						});
					  }else{
						  layer.msg('保存失败，请稍后再试！', {icon: 2,time: 1000});
					  }
					$("#saveButton").html("保存");
			  }
			  ,error: function(){
	            //请求出错处理
	            layer.close(indexQ);
	            $("#saveButton").html("保存");
				  layer.msg('出错了', {icon: 2,time: 1000});
	        }  
			});
	}else{
		if(selectValue == 1){
			saveFile(id);
		}
		if(selectValue == 2){
			saveDatabase(id);
		}
	}
}
//文件导入
function saveFile(id){
	var file = $('#chooseFile')[0].files[0];
	var name = $("input[name='name']").val();
	var dataUseWay = $("input[name='dataUseWay']:checked").val();
	var dataTarget = $("input[name='dataTarget']:checked").val();
	var formData = new FormData();
	formData.append("name",name);
	formData.append("dataUseWay",dataUseWay);
	formData.append("dataTarget",dataTarget);
	formData.append("equipment",id);
	formData.append("importWay",selectValue);
	formData.append("file", file); //ajax上传文件
	var indexO;
	$.ajax({
		  type: 'POST',
		  url: 'uploadFile',
		  cache: false,
		  data: formData,
		  beforeSend:function(){
			  $("#saveButton").html("保存中");
			  indexO = layer.load(0, {
				  shade: [0.5,'#fff'] //0.1透明度的白色背景
				});
		  },
		  processData : false, 
		  contentType : false,
		  success: function (result){
			  if(result.code == "0"){
				  layer.msg('保存成功！', {icon: 1,time: 1000,
						end:function(){
							layer.close(indexO); 
							var jsonData = {id:id};
							parent.clickNode(jsonData);
							closeIndex();
						}
					});
				  }else{
					  layer.msg('保存失败，请稍后再试！', {icon: 2,time: 1000});
				  }
			  $("#saveButton").html("保存");
			  layer.close(indexO);
				
		  }
		  ,error: function(){
            //请求出错处理
            layer.close(indexO);
            $("#saveButton").html("保存");
			  layer.msg('出错了', {icon: 2,time: 1000});
        }  
		});
}
//数据库导入
function saveDatabase(id){
	var name = $("input[name='name']").val();
	var dataUseWay = $("input[name='dataUseWay']:checked").val();
	var dataTarget = $("input[name='dataTarget']:checked").val();
	var formData = new FormData();
	formData.append("name",name);
	formData.append("dataUseWay",dataUseWay);
	formData.append("dataTarget",dataTarget);
	formData.append("equipment",id);
	formData.append("importWay",selectValue);
	formData.append("currDatabase",choosedDatabase);
	formData.append("selectWay",radioValue);
	formData.append("databaseId",databaseId);
	if(radioValue == 2){
		tableOrSql = $("#textarea").val();
	}
	formData.append("tableOrSql",tableOrSql);
	var indexO;
	$.ajax({
		  type: 'POST',
		  url: 'saveDatabase',
		  cache: false,
		  data: formData,
		  beforeSend:function(){
			  $("#saveButton").html("保存中");
			  indexO = layer.load(0, {
				  shade: [0.5,'#fff'] //0.1透明度的白色背景
				});
		  },
		  processData : false, 
		  contentType : false,
		  success: function (result){
			  if(result.code == "0"){
				  layer.msg('保存成功！', {icon: 1,time: 1000,
						end:function(){
							layer.close(indexO); 
							var jsonData = {id:id};
							parent.clickNode(jsonData);
							closeIndex();
						}
					});
				  }else{
					  layer.msg('保存失败，请稍后再试！', {icon: 2,time: 1000});
				  }
			  $("#saveButton").html("保存");
			  layer.close(indexO);
		  }
		  ,error: function(){
            //请求出错处理
            layer.close(indexO);
            $("#saveButton").html("保存");
			  layer.msg('出错了', {icon: 2,time: 1000});
        }  
		});
}
var selectValue="";
var radioValue="";
var tableOrSql="";
//数据库连接变量
var ip="";
var dataBase="";
var port="";
var userName="";
var password="";
var nameOrSID="";
var serverName="";
var choosedDatabase="";
var databaseId="";
layui.use('form', function(){
	   var form = layui.form;
	   form.on('select(selectWay)', function(data){
		   selectValue = data.value;
			if(selectValue == 1){
				$("#file").attr("style","");
			}else{
				$("#file").attr("style","display:none");
			}
			if(selectValue == 2){
				layui.use('layer', function(){ //独立版的layer无需执行这一句
					  var layer = layui.layer;
					  var index = layer.open({
						  type: 2,
						  title:'数据库连接',
						  offset: 't',
						  area: ['700px', '350px'],
						  fixed: false, //不固定
						  maxmin: true,
						  btn: ['连接','关闭'],
						  content:['connectdatabase'],
						  yes:function(index,layero){
							  $(layero).find("iframe")[0].contentWindow.readyToConnect(index);
						  }
						});
				});
			}else{
				$("#database2").attr("style","display:none");
				$("#database1").attr("style","display:none");
				$("#database3").attr("style","display:none");
			}
	   });
	   
	   form.on('radio(chooseSelectWay)', function(data){
		   radioValue = data.value;
		   if(radioValue == 1){
			   $("#tableSelect").attr("style","");
			   $("#inputSql").attr("style","display:none");
		   }else if(radioValue == 2){
			   $("#tableSelect").attr("style","display:none");
			   $("#inputSql").attr("style","");
		   }else{
			   $("#tableSelect").attr("style","display:none");
			   $("#inputSql").attr("style","display:none");
		   }
		 });  
	   
	   form.on('select(chooseDatabase)', function(data){
		   choosedDatabase = data.value;
		   var formData = new FormData();
			formData.append("ip",ip);
			formData.append("port",port);
			formData.append("dataBase",dataBase);
			formData.append("userName",userName);
			formData.append("password",password);
			formData.append("nameOrSID",nameOrSID);
			formData.append("serverName",serverName);
			formData.append("choosedDatabase",choosedDatabase);
			formData.append("getTables",1);//根据库名查表
		   connect(formData);
		   freshSelect();
	   });
	   
	   form.on('select(chooseTable)', function(data){
		   tableOrSql = data.value;
	   });
});
//连接
function connect(formData){
	ip = formData.get('ip');
	var index1 = formData.get('index');
	port = formData.get('port');
	userName = formData.get('userName');
	dataBase = formData.get('dataBase');
	password = formData.get('password');
	nameOrSID = formData.get('nameOrSID');
	serverName = formData.get('serverName');
	$.ajax({
		  type: 'POST',
		  url: 'connectDatabase',
		  cache: false,
		  data: formData,
		  processData : false, 
		  contentType : false,
		  success: function (result){
			  if(result.code == "0"){
				  if(formData.get('getTables') == 1){//选择表
					  $("#table").find("option[id='chooseTables']").remove();
					  var datas = result.data;
						for(var x in datas){
							$tr = "<option id='chooseTables' value='"+ datas[x] +"'>" + datas[x]+"</option>"
							$("#chooseTable").after($tr);
						}
						freshSelect();
				  }else{//连接时
					  layer.msg('连接成功！', {icon: 1,time: 1000,
							end:function(){
								var datas = result.data;
								$("#database3").css('display','');
								for(var x in datas){
									$tr = "<option value='"+ datas[x] +"'>" + datas[x]+"</option>"
									$("#chooseDatabase").after($tr);
								}
								freshSelect();
								databaseId = result.action;
								layer.close(index1);
							}
						});
				  }
			  }else{
				  if(formData.get('getTables') == 1){
					  layer.msg('查询失败，请检查网络或远程数据库！', {icon: 2,time: 1000});
				  }else{
					  layer.msg('连接失败', {icon: 2,time: 1000});
				  }
			  }
		  }
		  ,error: function(){
            //请求出错处理
			layer.msg('出错了', {icon: 2,time: 1000});
        }  
		});
}

//刷新select框
function freshSelect(){
	layui.use('form', function(){
		   var form = layui.form;
		   form.render('select');
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
