<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
<title>算法详情</title>
<c:import url="global.jsp"></c:import>
<script type="text/javascript" src="/jQuery/jquery-1.10.2.min.js"></script>
</head>

<body>
<div>
	<form class="layui-form" action="" style="margin-top: 10px;margin-right: 20px;" id="form" enctype ="multipart/form-data">
	<input type="hidden" name="flag" value="" />
	<div id = "SVM" style="display:none">
	 <div class="layui-form-item">
	   <label class="layui-form-label"><span style="color:red;" >*</span> svm类型：</label>
	   <label class="layui-form-label" style="text-align:left" id = "svm"/>

	 </div>
	 <div class="layui-form-item">
	   <label class="layui-form-label" style="text-align:left;"><span style="color:red;" >*</span> kernel类型：</label>
	    <label class="layui-form-label" style="text-align:left" id = "kernel"/>

	 </div>
	 <div class="layui-form-item">
	   <label class="layui-form-label"><span style="color:red;" >*</span> 缓存大小：</label>
	     <label class="layui-form-label" style="text-align:left" id = "cachesize"/>

	 </div>
	 <div class="layui-form-item">
	   <label class="layui-form-label"><span style="color:red;" >*</span> 终止条件：</label>
	     <label class="layui-form-label" style="text-align:left" id = "eps"/>
	 </div>
	 <div class="layui-form-item">
	   <label class="layui-form-label"><span style="color:red;" >*</span> 惩罚系数：</label>
	     <label class="layui-form-label" style="text-align:left" id = "C"/>
	 </div>
	 <div class="layui-form-item">
	   <label class="layui-form-label"><span style="color:red;" >*</span> 精度：</label>
	     <label class="layui-form-label" style="text-align:left" id = "accuracy"/>
	 </div>
	
    </div>
    
	 <!-- Softmxa -->
	<div id = "SoftMax" style="display:none">
	 <div class="layui-form-item">
	   <label class="layui-form-label"><span style="color:red;" >*</span> 迭代次数：</label>
	   <label class="layui-form-label" style="text-align:left" id = "interation"/>

	 </div>
	 <div class="layui-form-item">
	   <label class="layui-form-label" style="text-align:left;"><span style="color:red;" >*</span> 学习率：</label>
	    <label class="layui-form-label" style="text-align:left" id = "learnrate"/>

	 </div>

	 <div class="layui-form-item">
	   <label class="layui-form-label" style="text-align:left;"><span style="color:red;" >*</span> 精度：</label>
	     <label class="layui-form-label" style="text-align:left" id = "softmaxaccuracy"/>
	 </div>
	</div>
	
		 <!-- Bayes -->
	<div id = "Bayes" style="display:none">
	 <div class="layui-form-item">
	   <label class="layui-form-label"><span style="color:red;" >*标准偏差</span> ：</label>
	   <label class="layui-form-label" style="text-align:left" id = "dev"/>
	 </div>
	 <div class="layui-form-item">
	   <label class="layui-form-label" style="text-align:left;"><span style="color:red;" >*</span> 精度：</label>
	     <label class="layui-form-label" style="text-align:left" id = "bayesaccuracy"/>
	 </div>
	</div>
  </form>
</div>
<script type="text/javascript">
var PARAM = "${PARAM}";
var algtype = "${algtype}";

$(function(){
	console.log(PARAM+"inglfDetail");
	console.log(algtype);
	var root;
	switch(algtype)
	{
	case "SVM":
		root = document.getElementById("SVM");
		root.style.display="";
		var svmhandle=document.getElementById("svm");
		var kernelhandle=document.getElementById("kernel");
		var cachesizehandle=document.getElementById("cachesize");
		var epshandle=document.getElementById("eps");
		var Chandle=document.getElementById("C");
		var accuracyhandle=document.getElementById("accuracy");
		var datass=new Array();
		var realvalue = new Array();
		datass=PARAM.split(";");
		for(var x in datass)
		{
			var temp = new Array();
			temp=datass[x].split(":");
			realvalue[x] = temp[1];
		}
		svmhandle.innerText=realvalue[0];
		kernelhandle.innerText=realvalue[1];
		cachesizehandle.innerText=realvalue[2];
		epshandle.innerText=realvalue[3];
		Chandle.innerText=realvalue[4];
		accuracyhandle.innerText=realvalue[5];
		break;
		
	case "Softmax":
		root = document.getElementById("SoftMax");
		root.style.display="";
		var interationhandle=document.getElementById("interation");
		var learnratehandle=document.getElementById("learnrate");
		var softmaxaccuracyhandle=document.getElementById("softmaxaccuracy");
		var datass=new Array();
		datass=PARAM.split(",");
		console.log(datass[0]);
		interationhandle.innerText = datass[0];
		learnratehandle.innerText=datass[1];
		softmaxaccuracyhandle.innerText=datass[2];
		break;
	case "Bayes":
		root = document.getElementById("Bayes");
		root.style.display="";
		var devhandle=document.getElementById("dev");
		var bayesaccuracyhandle=document.getElementById("bayesaccuracy");
		var datass=new Array();
		datass=PARAM.split(",");
		console.log(datass[1]);
		devhandle.innerText = datass[1];
		bayesaccuracyhandle.innerText=datass[0];
		break;
	}
});
//取消

</script>

</body>
</html>
