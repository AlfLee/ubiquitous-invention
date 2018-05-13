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

.ztree_a { background-color: #eee !important;text-align:right;}
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
               <li class="layui-nav-item" id="INPUT_tab1" onClick="setTab('INPUT',1,4)">SVM</li>
                <li class="layui-nav-item" id="INPUT_tab2" onClick="setTab('INPUT',2,4)">Softmax</li>
                 <li class="layui-nav-item" id="INPUT_tab3" onClick="setTab('INPUT',3,4)">Bayes</li>
                  <li class="layui-nav-item" id="INPUT_tab4" onClick="setTab('INPUT',4,4)">DeepLearning</li>
            </ul> 
        </div>

<iframe src="equipTree" class="ssss"></iframe>
  
<div class="dddd" id="INPUT_con1">
 <fieldset class="layui-elem-field layui-field-title">
  <legend>参数设置</legend>
</fieldset>
<div style="text-align: left;">
<form class="layui-form" action="">
<div class="layui-form-item">
<div class="layui-inline">
    <label class="layui-form-label">数据选择：</label>
    <div class="layui-input-inline">
      <select name="dataSelect" id="dataSelect">
      </select>
      </div>
      </div>
	<div class="layui-inline">
    <label class="layui-form-label">SVM类型：</label>
    <div class="layui-input-inline">
      <select name="Style" id="SVM_TYPE">
     <option value="0">C_SVC</option>
        <option value="1">NU_SVC</option>
        <option value="2">ONE_CLASS</option>
        <option value="3">EPSILON_SVR</option>
        <option value="4">NU_SVR</option>
      </select>
      </div>
      </div>
      <div class="layui-inline">
    <label class="layui-form-label">核函数：</label>
    <div class="layui-input-inline">
      <select name="Kernel" id="KERNEL_TYPE">
        <option value="0">LINEAR</option>
        <option value="1">POLY</option>
        <option value="2">RBF</option>
        <option value="3">SIGMOID</option>
      </select>
      </div>
      </div>
      <div class="layui-inline">
    <label class="layui-form-label" >缓存大小：</label>
    <div class="layui-input-inline">
      <select name="Cache" id="CACHE_SIZE">
      
      <option value="100">100</option>
      <option value="">请输入</option> 
      </select>
      </div>
      </div>
      <div class="layui-inline">
    <label class="layui-form-label">终止条件：</label>
    <div class="layui-input-inline">
      <select name="Final" id="EPS" lay-filter="editable(this)">
      
      <option value="0.0001">0.0001</option>
        <option value="0.001">0.001</option>
        <option value="0.01">0.01</option>
        <option value="">请输入</option> 
      </select>
      </div>
      </div>
      <div class="layui-inline">
    <label class="layui-form-label">惩罚系数：</label>
    <div class="layui-input-inline">
      <select name="Punish" id="C">
      
      <option value="1">1</option>
        <option value="2">2</option>
        <option value="1.5">1.5</option>
        <option value="">请输入</option> 
      </select>
      </div>
       <button class="layui-btn" style="float:right;" onclick="runSvm()" id="svmTrain"><i class="layui-icon" >训练</i></button>
      </div>
</div>
</form>
</div>
<fieldset class="layui-elem-field layui-field-title">
  <legend>训练结果</legend>
</fieldset>
<div class="layui-btn-group" style="text-align: right;">
    <button class="layui-btn" onclick="del();">删除</button>
     <button class="layui-btn" onclick="selfadapt();" id = "adapt">自适应参数</button>
    
  </div>
<table class="layui-table">
	<thead class="tr_1">
    <tr>
      <th style="width:20px;"><input type="checkbox" onclick="selectAll(this);" /></th>
      <th>算法名称</th>
      <th>设备名称</th>
      <th>SVM类型</th>
      <th>核函数</th>
      <th>缓存大小</th>
      <th>终止条件</th>
      <th>惩罚系数</th>
      <th>训练时间</th>
      <th>训练精度</th>
    </tr> 
  </thead>
</table>
<div id="page" style="margin-top: -20px"></div>
</div>
<div class="dddd" id="INPUT_con2" style="display: none">
 <fieldset class="layui-elem-field layui-field-title">
  <legend>参数设置</legend>
</fieldset>
<div style="text-align: left;">
<form class="layui-form" action="">
<div class="layui-form-item">
  <div class="layui-inline">
    <label class="layui-form-label">数据选择：</label>
    <div class="layui-input-inline">
      <select id="DataSelectSoftMax">
      </select>
    </div>
  </div>
  <div class="layui-inline">
    <label class="layui-form-label">迭代次数：</label>
    <div class="layui-input-block">
      <input class="layui-input" id="Iteration"/>
    </div>
  </div>
  <div class="layui-inline">
    <label class="layui-form-label">学习率：</label>
    <div  class="layui-input-block">
      <input class="layui-input" id="LearnRate"/>
    </div>
      
  </div>
   <input class="layui-btn" style="float:right;width:100px;" id="SoftMaxTrain" onclick="runSoftmax()" value="训练"/>
</div>
</form>
</div>
<fieldset class="layui-elem-field layui-field-title">
  <legend>选择结果</legend>
</fieldset>

<table class="layui-table">
	<thead class="tr_2">
    <tr>
      <th style="width:20px;"><input type="checkbox" onclick="selectAll(this);" /></th>
      <th>算法名称</th>
      <th>设备名称</th>
      <th>迭代次数</th>
      <th>学习率</th>
      <th>训练时间</th>
      <th>训练精度</th>
    </tr> 
  </thead>
</table>
<div id="pagesoftmax" style="margin-top: -20px"></div>
</div>
<div class="dddd" id="INPUT_con3" style="display: none">
 <fieldset class="layui-elem-field layui-field-title">
  <legend>参数设置</legend>
</fieldset>
<div style="text-align: left;">
<form class="layui-form" action="">
<div class="layui-form-item">
<div class="layui-inline">
    <label class="layui-form-label">数据选择：</label>
      <div class="layui-input-inline">
       <select id="DataSelectBayes">
       </select>
      </div>
      <input class="layui-btn" style="float:right;width:100px;" id="BayesTrain" onclick="runBayes()" value="训练"/>
      </div>
</div>
</form>
</div>
<fieldset class="layui-elem-field layui-field-title">
  <legend>选择结果</legend>
</fieldset>
<table class="layui-table">
	<thead class="tr_3">
    <tr>
      <th style="width:20px;"><input type="checkbox" onclick="selectAll(this);" /></th>
      <th>算法名称</th>
      <th>设备名称</th>
      <th>标准偏差</th>
      <th>训练时间</th>
      <th>训练精度</th>
    </tr> 
  </thead>
</table>
<div id="pagebayes" style="margin-top: -20px"></div>
</div>
<div class="dddd" id="INPUT_con4" style="display: none">
 <fieldset class="layui-elem-field layui-field-title">
  <legend>参数设置</legend>
</fieldset>
<div style="text-align: left;">
<form class="layui-form" action="">
<div class="layui-form-item">
<div class="layui-inline">
    <label class="layui-form-label">数据选择：</label>
    <div class="layui-input-inline">
      <select name="DataSelect" >
        <option value="">请选择</option>
        <option value="1">数据1</option>
        <option value="2">数据2</option>
        <option value="3">数据3</option>
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
      </div>
	<div class="layui-inline">
    <label class="layui-form-label">贡献度：</label>
    <div class="layui-input-inline">
      <select name="Conti" >
        <option value="">0.9</option>
        <option value="1">0.85</option>
        <option value="2">0.8</option>
      </select>
      </div>
       <button class="layui-btn" style="float:right;"><i class="layui-icon" >选择</i></button>
      </div>
</div>
</form>
</div>
<fieldset class="layui-elem-field layui-field-title">
  <legend>选择结果</legend>
</fieldset>
</div>
<script type="text/javascript">
//选中样式
$(function(){
	var jsonData = {id:1};
	clickNode(jsonData);
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
//var form;
//必须有 否则CheckBox跟select框无样式
layui.use('form', function(){
	 var  form = layui.form;
	   form.on('select(editable(select1))', function(data)
			   {
		   console.log("run");
			      if(select1.value == "")
			      {  
			         var newvalue = prompt("请输入","");  
			         if(newvalue)
			         {  
			            addSelected(select1,newvalue,newvalue);  
			         }  
			      } 
			   });
});

var page1=1;
function setTab(name, cursel, n) //tab页点击脚本
{
	for (i = 1; i <= n; i++) {
		d=document.getElementsByTagName('li')
		for(p=d.length;p--;){
		if(d[p].id==cursel){
			d[p].style.backgroundColor="#7FFFD4"; /* 点击 #7FFFD4 #20B2AA*/
			
		}
		else{
			d[p].style.backgroundColor="#20B2AA" /*其他 #20B2AA*/
			} 
		}
		page1 = cursel;
		var menu = document.getElementById(name  + "_tab" + i); //提取tab页名
		var con = document.getElementById(name  + "_con" + i); //提取内容名
		menu.className = i == cursel ? "layui-this" : ""; //把当前点击的tab页class改为"hover",其他的消除
		con.style.display = i == cursel ? "block" : "none"; //把当前点击的内容显示，其他的消除
	    var jsonData = {id:parentId};
		clickNode(jsonData);
		//getdataselect(jsonData);
		//alert(d+" "+menu +" "+ com);
	}

}


var parentId;
var flag;

function runSvm()
{
	
	var dataSelect=document.getElementById("dataSelect").value; 
	var SVM_TYPE=document.getElementById("SVM_TYPE").value;
	var KERNEL_TYPE=document.getElementById("KERNEL_TYPE").value;
	var CACHE_SIZE=document.getElementById("CACHE_SIZE").value;
	var EPS=document.getElementById("EPS").value;
	var C=document.getElementById("C").value;
	var formData = new FormData();
	formData.append("dataSelect",dataSelect);
	formData.append("SVM_TYPE",SVM_TYPE);
	formData.append("KERNEL_TYPE",KERNEL_TYPE);
	formData.append("CACHE_SIZE",CACHE_SIZE);
	formData.append("EPS",EPS);
	formData.append("C",C);
	formData.append("equipid",parentId);
	console.log(dataSelect);
	//alert(equipSelect);
	var index1;
	$.ajax({
		  type: 'POST',
		  url: 'SVM',
		  data: formData,
		  beforeSend:function(){
			  $("#svmTrain").html("训练中");
			  layui.use('layer', function(){ //独立版的layer无需执行这一句
				  var layer = layui.layer;
				  index1 = layer.load(0, {
					  shade: [0.5,'#fff'] //0.1透明度的白色背景
					});
			  });
		  },
		  processData : false, 
		  contentType : false,
		  success: function (result){
			  datas=result.code;
			  var jsonData = {id:parentId};
			  clickNode(jsonData);
			  
			  layer.msg('加载成功！', {icon: 1,time: 1000,
					end:function(){
						layer.close(index1); 
						$("#svmTrain").html("训练");
					}
				});
			  
		  }
		  ,error: function(){
            //请求出错处理
			  layer.msg('出错了', {icon: 2,time: 1000});
			  layer.close(index1);
        }  
		});
	//alert(equipSelect);
}

function runSoftmax()
{
	var formData = new FormData();
	var DataSelectSoftMax=document.getElementById("DataSelectSoftMax").value; 
	var Iteration=document.getElementById("Iteration").value;
	var LearnRate=document.getElementById("LearnRate").value;
	formData.append("DataSelectSoftMax",DataSelectSoftMax);
	formData.append("Iteration",Iteration);
	formData.append("LearnRate",LearnRate);
	formData.append("equipid",parentId);
	var index1;
	$.ajax({
		  type: 'POST',
		  url: 'Softmax',
		  data: formData,
		  beforeSend:function(){
			  $("#DataSelectSoftMax").html("训练中");
			  layui.use('layer', function(){ //独立版的layer无需执行这一句
				  var layer = layui.layer;
				  index1 = layer.load(0, {
					  shade: [0.5,'#fff'] //0.1透明度的白色背景
					});
			  });
		  },
		  processData : false, 
		  contentType : false,
		  success: function (result){
			  datas=result.code;
			  console.log(page1+"in the softmax");
			  var jsonData = {id:parentId};
			  clickNode(jsonData);
			  layer.msg('加载成功！', {icon: 1,time: 1000,
					end:function(){
						layer.close(index1); 
						$("#DataSelectSoftMax").html("训练");
					}
				});
			  
		  }
		  ,error: function(){
            //请求出错处理
			  layer.msg('出错了', {icon: 2,time: 1000});
			  layer.close(index1);
        }  
		});
}


function runBayes()
{
	var formData = new FormData();
	var DataSelectSoftMax=document.getElementById("DataSelectBayes").value; 
	formData.append("DataSelectSoftMax",DataSelectSoftMax);
	formData.append("equipid",parentId);
	var index1;
	$.ajax({
		  type: 'POST',
		  url: 'Bayes',
		  data: formData,
		  beforeSend:function(){
			  $("#DataSelectBayes").html("训练中");
			  layui.use('layer', function(){ //独立版的layer无需执行这一句
				  var layer = layui.layer;
				  index1 = layer.load(0, {
					  shade: [0.5,'#fff'] //0.1透明度的白色背景
					});
			  });
		  },
		  processData : false, 
		  contentType : false,
		  success: function (result){
			  datas=result.code;
			  console.log(page1+"in the bayes");
			  var jsonData = {id:parentId};
			  clickNode(jsonData);
			  layer.msg('加载成功！', {icon: 1,time: 1000,
					end:function(){
						layer.close(index1); 
						$("#DataSelectBayes").html("训练");
					}
				});
			  
		  }
		  ,error: function(){
            //请求出错处理
			  layer.msg('出错了', {icon: 2,time: 1000});
			  layer.close(index1);
        }  
		});
}

var currentPage;
var pageSize;
var loadingFlag=0;
var algortype;
var tableelem;
//点击节点触发
function clickNode(node){
	parentId = node.id;
	
	var formData = new FormData();
	switch(page1)
	{
	case 1:
		algortype = "showsvmmodel";
		tableelem = "page";
		break;
	case 2:
		algortype = "showsoftmaxmodel";
		tableelem = "pagesoftmax";
		break;
	case 3:
		algortype = "showbayesmodel";
		tableelem = "pagebayes";
		break;
	case 4:
		algortype = "showdeeplearningmodel";
		break;
	}
	
	formData.append("current",!node.current ? "1" : node.current);
	formData.append("limit",!node.limit ? "5" : node.limit);
	formData.append("equipId",parentId);
	var index1;
	$.ajax({
		  type: 'POST',
		  url: algortype,
		  data: formData,
		  processData : false, 
		  contentType : false,
		  beforeSend:function(){
			  layui.use('layer', function(){ //独立版的layer无需执行这一句
				  var layer = layui.layer;
				  index1 = layer.load(0, {
					  shade: [0.5,'#fff'] //0.1透明度的白色背景
					});
			  });
		  },
		  success: function (result){
			  $("tr[name='datas']").remove();
			  var datas = result.data;
			  var count = result.count;
			  layui.use(['laypage', 'layer'], function(){
				  var laypage = layui.laypage
				  laypage.render({
					    elem: tableelem
					    ,count: count
					    ,curr:!node.current ? "1" : node.current
					    ,layout: ['prev', 'page', 'next','count','limit']
						  ,limit:!node.limit ? "5" : node.limit
						  ,limits:[5,10,20,30,50]
						  ,jump: function(obj, first){
							  if(!first){
								  currentPage = obj.curr;
								  pageSize = obj.limit;
								  var jsonData = {id:parentId,current:obj.curr,limit:obj.limit};
								   clickNode(jsonData);
							  }
						  }	  
					  });
				});
			  var x;
			  switch(page1)
			  {
			  case 1:
				  {
				  var jsonData = {id:parentId};
				  getdataselect(jsonData);
				  for(x in datas){
					  var accuracy = "";
					  $tr = "<tr name='datas' onmousemove='hover(this)' onmouseout='outHover(this)'><td style='width:20px;'><input type='checkbox' name='box' id='"+datas[x].id+"'/></td>"
					  //$tr = "<tr name='datas'><td style='width:20px;'><input type='checkbox' name='box' id='"+datas[x].id+"'/></td>"
					  $tr += "<td name='algorithm_name'>"+datas[x].algorithm_name+"</td>";
					  $tr += "<td name='equip_name'>"+datas[x].equip_name+"</td>";
					  $tr += "<td name='svm_type'>"+datas[x].svm_type+"</td>";
					  $tr += "<td name='kernel_type'>"+datas[x].kernel_type+"</td>";
					  $tr += "<td name='cache_size'>"+datas[x].cache_size+"</td>";
					  $tr += "<td name='eps'>"+datas[x].eps+"</td>";
					  $tr += "<td name='C'>"+datas[x].c+"</td>";
					  $tr += "<td name='time'>"+datas[x].time+"</td>";
					  accuracy = datas[x].accuracy;
					  $tr += "<td>"+accuracy+"</td></tr>";
					  $(".tr_1").after($tr);
					  }
					  layui.use('layer', function(){
						  var layer = layui.layer;
						  layer.close(index1);
					  });
				  
					  
				  }
				  break;
			  case 2:
				  {
				  var jsonData = {id:parentId};
				  getdataselect(jsonData);
				  
				  for(x in datas){
					  //console.log(datas[x].param);
					  var temp = new Array();
			          temp=datas[x].param.split(",");
					  var accuracy = "";
					  $tr = "<tr name='datas' onmousemove='hover(this)' onmouseout='outHover(this)'><td style='width:20px;'><input type='checkbox' name='box' id='"+datas[x].id+"'/></td>"
					  //$tr = "<tr name='datas'><td style='width:20px;'><input type='checkbox' name='box' id='"+datas[x].id+"'/></td>"
					  $tr += "<td name='algorithm_name'>"+datas[x].modeltype+"</td>";
					  $tr += "<td name='equip_name'>"+temp[4]+"</td>";
					  $tr += "<td name='interation'>"+temp[0]+"</td>";
					  $tr += "<td name='learnrate'>"+temp[1]+"</td>";
					  $tr += "<td name='time'>"+temp[3]+"</td>";
					  accuracy = temp[2];
					  $tr += "<td>"+accuracy+"</td></tr>";
					  $(".tr_2").after($tr);
					  }
					  layui.use('layer', function(){
						  var layer = layui.layer;
						  layer.close(index1);
					  });
				  }
				  break;
			  case 3:
				  {
					  var jsonData = {id:parentId};
					  getdataselect(jsonData);
					  for(x in datas){
						  //console.log(datas[x].param);
						  var temp = new Array();
				          temp=datas[x].param.split(",");
						  var accuracy = "";
						  $tr = "<tr name='datas' onmousemove='hover(this)' onmouseout='outHover(this)'><td style='width:20px;'><input type='checkbox' name='box' id='"+datas[x].id+"'/></td>"
						  //$tr = "<tr name='datas'><td style='width:20px;'><input type='checkbox' name='box' id='"+datas[x].id+"'/></td>"
						  $tr += "<td name='algorithm_name'>"+datas[x].modeltype+"</td>";
						  $tr += "<td name='equip_name'>"+temp[3]+"</td>";
						  $tr += "<td name='dev'>"+temp[1]+"</td>";
						  $tr += "<td name='time'>"+temp[2]+"</td>";
						  accuracy = temp[0];
						  $tr += "<td>"+accuracy+"</td></tr>";
						  $(".tr_3").after($tr);
						  }
						  layui.use('layer', function(){
							  var layer = layui.layer;
							  layer.close(index1);
						  });
				  }
				  break;
			  }
			  
		  }
		  ,error: function(){
              //请求出错处理
			  layer.msg('出错了', {icon: 2,time: 1000});
          }  
		});
}


function getdataselect(node){
	var parentId=node.id;
	var formData = new FormData();
	formData.append("equipId",parentId);

	$.ajax({
		  type: 'POST',
		  url: 'getdataselect',
		  data: formData,
		  processData : false, 
		  contentType : false,
		  success: function(result) {
			  var data=result.data;
			  console.log(data);
			  console.log(page1);
              switch(page1)
              {
              case 1:
            	  var root = document.getElementById("dataSelect");
                  $("#dataSelect").empty();
                  break;
              case 2:
            	  var root = document.getElementById("DataSelectSoftMax");
            	  $("#DataSelectSoftMax").empty();
            	  break;
              case 3:
            	  var root = document.getElementById("DataSelectBayes");
            	  console.log("run3");
                  $("#DataSelectBayes").empty();
                  break;
              case 4:
            	  var root = document.getElementById("dataSelect");
                  $("#dataSelect").empty();
                  break;
              }
	           for(var i = 0;i<data.length;i++)
	           {

		        	   var option=document.createElement("option");
		        	   option.setAttribute("value",data[i].id);
		        	   option.innerText=data[i].dataName;
		        	   root.appendChild(option);
		        	   layui.use('form', function(){
			        	    var  form = layui.form;
					        form.render('select'); 
	        	        });
	           }
		    },
		  error: function(){
            //请求出错处理
			  layer.msg('出错了', {icon: 2,time: 1000});
        }  
		});
}

/*
function editable(select1){
	alert("asdf"); 
   console.log("asdf" + select1);
   if(select1.value == ""){  
      var newvalue = prompt("请输入","");  
      if(newvalue){  
         addSelected(select1,newvalue,newvalue);  
      }  
   }  
} */
function addSelected(fld1,value1,text1){  
    if (document.all)    {  
            var Opt = fld1.document.createElement("OPTION");  
            Opt.text = text1;  
            Opt.value = value1;  
            fld1.options.add(Opt);  
            Opt.selected = true;  
    }else{  
            var Opt = new Option(text1,value1,false,false);  
            Opt.selected = true;  
            fld1.options[fld1.options.length] = Opt;  
    }  
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

//删除
function del(){
	var data = $(".layui-table").find("input[name='box']:checkbox:checked");
	if(!data || data.length < 1){
		layer.msg('请至少选择一条数据！', {icon: 7,time: 1000});
		return;
	}
	var dataIds="";
	for(var x = 0; x < data.length; x++){
		dataIds = dataIds + data[x].id+",";
	}
	layui.use('layer', function(){
		var layer = layui.layer;
		layer.confirm('您确定要删除吗？', {
			  btn: ['确定','取消'] //按钮
			  ,offset : ['30%' , '30%']
			}, function(){
				dele(dataIds);
			});
	});
	console.log(dataIds);
	function dele(dataIds){
		var index;
		$.ajax({
			  type: 'POST',
			  url: 'deleteModelData',
			  beforeSend:function(){
				  index = layer.load(0, {
					  shade: [0.1,'#fff'] //0.1透明度的白色背景
					});
			  },
			  data: {dataIds:dataIds},
			  success: function (result){
				  layer.msg('删除成功！', {icon: 1,time: 1000,
						end:function(){
							var jsonData = {id:parentId,current:currentPage,limit:pageSize};
							clickNode(jsonData);
							layer.close(index); 
						}
					});
			  }
			  ,error: function(){
	          //请求出错处理
	          layer.close(index); 
				  layer.msg('出错了', {icon: 2,time: 1000});
	           }  
			});
	}	
}

function selfadapt(){
	var formData = new FormData();
	formData.append("dataSelect",dataSelect);
	formData.append("equipId",parentId);
	var index1;
	$.ajax({
		  type: 'POST',
		  url: 'selfadapt',
		  data: formData,
		  beforeSend:function(){
			  $("#adapt").html("训练中");
			  layui.use('layer', function(){ //独立版的layer无需执行这一句
				  var layer = layui.layer;
				  index1 = layer.load(0, {
					  shade: [0.5,'#fff'] //0.1透明度的白色背景
					});
			  });
		  },
		  processData : false, 
		  contentType : false,
		  success: function (result){
			  datas=result.code;
			  var jsonData = {id:parentId};
			  clickNode(jsonData);
			  
			  layer.msg('加载成功！', {icon: 1,time: 1000,
					end:function(){
						layer.close(index1); 
						$("#adapt").html("自适应");
					}
				});
			  
		  }
		  ,error: function(){
            //请求出错处理
			  layer.msg('出错了', {icon: 2,time: 1000});
			  layer.close(index1);
        }  
		});
	
	
}
</script>       
</body>
</html>