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
<!-- <fieldset class="layui-elem-field layui-field-title">
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
    <label class="layui-form-label">算法选择：</label>
    <div class="layui-input-inline">
      <select name="algSelect" >
        <option value="">SVM</option>
        <option value="1">Softmax</option>
        <option value="2">Bayes</option>
        <option value="3">DeepLearning</option>
        <option value="1">Markov</option>
        <option value="2">Delta</option>
        <option value="3">Fenduan</option>
      </select>
      </div>
       <button class="layui-btn" style="text-align:right;"><i class="layui-icon">分析</i></button>
      </div>
</div>
</form>
</div> -->
<fieldset class="layui-elem-field layui-field-title">
  <legend>诊断结果</legend>
</fieldset>
<table class="layui-table">
	<thead class="tr_1">
    <tr>
      <th style="width:20px;"><input type="checkbox" onclick="selectAll(this);" /></th>
      <th>算法名称</th>
      <th>设备名称</th>
      <th>模型参数</th>
      <th>训练时间</th>
      <th>诊断结果</th>
    </tr> 
  </thead>
</table>
<div id="demo5" style="margin-top: -20px"></div>
</div>   
<script type="text/javascript">
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

</script>
</body>
</html>