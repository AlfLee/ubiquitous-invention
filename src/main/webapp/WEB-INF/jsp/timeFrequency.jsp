<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
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
  <legend>参数设置</legend>
</fieldset>
<div style="text-align: left;">
<form class="layui-form" action="">
<div class="layui-form-item">
<div class="layui-inline">
    <label class="layui-form-label">数据选择：</label>
    <div class="layui-input-inline">
      <select name="equipSelect" >
        <option value="">请选择</option>
        <option value="1">数据1</option>
        <option value="2">数据2</option>
        <option value="3">数据3</option>
      </select>
      </div>
      </div>

      <div class="layui-inline">
    <label class="layui-form-label">采样点数：</label>
    <div class="layui-input-inline">
      <select name="samplingCount" >
        <option value="">10</option>
        <option value="1">12</option>
        <option value="2">15</option>
        <option value="3">20</option>
      </select>
      </div>
      </div>
	<div class="layui-inline">
    <label class="layui-form-label">采样率：</label>
    <div class="layui-input-inline">
      <select name="samplingRate" >
        <option value="">0.1</option>
        <option value="1">0.2</option>
        <option value="2">0.4</option>
        <option value="3">0.5</option>
        <option value="4">1</option>
      </select>
      </div>
       <button class="layui-btn" style="float:right;"><i class="layui-icon" >分解</i></button>
      </div>
</div>
</form>
</div>
<fieldset class="layui-elem-field layui-field-title">
  <legend>分解结果</legend>
</fieldset>
</div>
	
<script type="text/javascript">
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

</script>
</body>
</html>