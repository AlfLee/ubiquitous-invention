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
<style>
ul{overflow:hidden;width:100%;height:25px;}
ul li{width:10%;float:left;height:25px;}
</style>   
</head>
<body>

<div class="layui-layout layui-layout-admin kit-layout-admin">
             <ul class="layui-nav  kit-nav">
               <li class="layui-nav-item" id="INPUT_tab1" onClick="setTab('INPUT',1,3)">小波分解</li>
                <li class="layui-nav-item" id="INPUT_tab2" onClick="setTab('INPUT',2,3)">EMD分解</li>
                 <li class="layui-nav-item" id="INPUT_tab3" onClick="setTab('INPUT',3,3)">SSA分解</li>
            </ul> 
        </div>

<iframe src="equipTree" class="ssss"></iframe>
  
<div class="dddd" id="INPUT_con1">
 <fieldset class="layui-elem-field layui-field-title">
  <legend>参数设置</legend>
</fieldset>
<div style="text-align: left;" >
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
    <label class="layui-form-label">设置维数：</label>
    <div class="layui-input-inline">
      <select name="equipSelect" >
        <option value="">6</option>
        <option value="1">7</option>
        <option value="2">8</option>
        <option value="3">9</option>
      </select>
      </div>
      </div>
	<div class="layui-inline">
    <label class="layui-form-label">采样比例：</label>
    <div class="layui-input-inline">
      <select name="samplingRate" >
        <option value="">0.1</option>
        <option value="1">0.2</option>
        <option value="2">0.4</option>
        <option value="3">0.5</option>
        <option value="4">1</option>
      </select>
      </div>
       <button class="layui-btn" style="text-align:right;"><i class="layui-icon">分解</i></button>
      </div>
</div>
</form>
</div>
<fieldset class="layui-elem-field layui-field-title">
  <legend>分解结果</legend>
</fieldset>
</div>
<div class="dddd" id="INPUT_con2" style="display: none">
 <fieldset class="layui-elem-field layui-field-title">
  <legend>参数设置</legend>
</fieldset>
<div style="text-align: left;" >
<form class="layui-form" action="">
<div class="layui-form-item">
<div class="layui-inline">
    <label class="layui-form-label">设备选择：</label>
    <div class="layui-input-inline">
      <select name="equipSelect" >
        <option value="">请选择</option>
        <option value="1">设备1</option>
        <option value="2">设备2</option>
        <option value="3">设备3</option>
      </select>
      </div>
      </div>
      <div class="layui-inline">
    <label class="layui-form-label">分解个数：</label>
    <div class="layui-input-inline">
      <select name="equipSelect" >
        <option value="">6</option>
        <option value="1">7</option>
        <option value="2">8</option>
        <option value="3">9</option>
      </select>
      </div>
      </div>
      <div class="layui-inline">
    <label class="layui-form-label">包络均值：</label>
    <div class="layui-input-inline">
      <select name="equipSelect" >
        <option value="">1</option>
        <option value="1">0.8</option>
        <option value="2">1.5</option>
        <option value="3">2</option>
      </select>
      </div>
      </div>
	<div class="layui-inline">
    <label class="layui-form-label">采样比例：</label>
    <div class="layui-input-inline">
      <select name="samplingRate" >
        <option value="">0.1</option>
        <option value="1">0.2</option>
        <option value="2">0.4</option>
        <option value="3">0.5</option>
        <option value="4">1</option>
      </select>
      </div>
       <button class="layui-btn" style="text-align:right;"><i class="layui-icon">分解</i></button>
      </div>
</div>
</form>
</div>
<fieldset class="layui-elem-field layui-field-title">
  <legend>分解结果</legend>
</fieldset>
</div>
<div class="dddd" id="INPUT_con3" style="display: none">
 <fieldset class="layui-elem-field layui-field-title">
  <legend>参数设置</legend>
</fieldset>
<div style="text-align: left;" >
<form class="layui-form" action="">
<div class="layui-form-item">
<div class="layui-inline">
    <label class="layui-form-label">设备选择：</label>
    <div class="layui-input-inline">
      <select name="equipSelect" >
        <option value="">请选择</option>
        <option value="1">设备1</option>
        <option value="2">设备2</option>
        <option value="3">设备3</option>
      </select>
      </div>
      </div>
      <div class="layui-inline">
    <label class="layui-form-label">方差贡献：</label>
    <div class="layui-input-inline">
      <select name="varContribution" >
        <option value="">1</option>
        <option value="1">2</option>
        <option value="2">1.5</option>
        <option value="3">0.5</option>
      </select>
      </div>
      </div>
	<div class="layui-inline">
    <label class="layui-form-label">采样比例：</label>
    <div class="layui-input-inline">
      <select name="samplingRate" >
        <option value="">0.1</option>
        <option value="1">0.2</option>
        <option value="2">0.4</option>
        <option value="3">0.5</option>
        <option value="4">1</option>
      </select>
      </div>
       <button class="layui-btn" style="text-align:right;"><i class="layui-icon">分解</i></button>
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

function setTab(name, cursel, n) //tab页点击脚本
{
	for (i = 1; i <= n; i++) {
		d=document.getElementsByTagName('li')
		for(p=d.length;p--;){
		if(d[p].id==cursel){
			d[p].style.backgroundColor="pink" /* 点击#7FFFD4 */
		}
		else{
			d[p].style.backgroundColor="#20B2AA" /*其他#20B2AA*/
			}
		}
		var menu = document.getElementById(name  + "_tab" + i); //提取tab页名
		var con = document.getElementById(name  + "_con" + i); //提取内容名
		menu.className = i == cursel ? "layui-this" : ""; //把当前点击的tab页class改为"hover",其他的消除
		con.style.display = i == cursel ? "block" : "none"; //把当前点击的内容显示，其他的消除
	}

}


</script>       
</body>
</html>