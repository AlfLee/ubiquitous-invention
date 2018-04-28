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
      <select name="dataSelect" id="dataSelect">
      </select>
      </div>
      </div>
	<div class="layui-inline">
    <label class="layui-form-label">算法选择：</label>
    <div class="layui-input-inline">
      <select name="algSelect" id="algSelect">
        <option value="SVM">SVM</option>
        <option value="Softmax">Softmax</option>
        <option value="Bayes">Bayes</option>
        <option value="DeepLearning">DeepLearning</option>
      </select>
     
     </div>
</div>
 <input  class="layui-btn" style="width:100px" onclick="algdetailshow()" value="算法详情" id="algdetail"/>
 <button class="layui-btn" style="text-align:right;" onclick="faultpredict()" id="diagnose"><i class="layui-icon">分析</i></button>
</form>

</div>
</div>
<fieldset class="layui-elem-field layui-field-title">
  <legend>诊断结果</legend>
</fieldset>
<table class="layui-table">
	<thead class="tr_1">
    <tr>
      <th style="width:20px;"><input type="checkbox" onclick="selectAll(this);" /></th>
      <th>算法名称</th>
      <th>设备名称</th>
      <th>诊断数据</th>  
      <th>诊断结果</th>
      <th style="width:20px;">状态</th>
      <th>诊断时间</th>
      
    </tr> 
  </thead>
</table>
<div id="page" style="margin-top: -20px"></div>
</div>   
<script type="text/javascript">
var parentId;
var flag;
$(function(){
	var jsonData = {id:1};
	clickNode(jsonData);
});



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


function faultpredict(){
	var formData = new FormData();
	var dataSelect=document.getElementById("dataSelect").value; 
	var Agl_TYPE=document.getElementById("algSelect").value;
	formData.append("dataSelect",dataSelect);
	formData.append("algSelect",Agl_TYPE);
	formData.append("equipid",parentId);
	console.log("12343");
	var index1;
	$.ajax({
		  type: 'POST',
		  url: 'diagnose',
		  data: formData,
		  beforeSend:function(){
			  $("#diagnose").html("诊断中");
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
						$("#diagnose").html("分析");
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
function clickNode(node){
	parentId = node.id;
	var formData = new FormData();
	formData.append("current",!node.current ? "1" : node.current);
	formData.append("limit",!node.limit ? "5" : node.limit);
	formData.append("equipId",parentId);
	var index1;
	$.ajax({
		  type: 'POST',
		  url: 'showfaultresult',
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
					    elem: 'page'
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
	
			  var jsonData = {id:parentId};
			  getdataselect(node);
			  for(x in datas){
				  var accuracy = "";
				  $tr = "<tr name='datas' onmousemove='hover(this)' onmouseout='outHover(this)'><td style='width:20px;'><input type='checkbox' name='box' id='"+datas[x].id+"'/></td>"
				  //$tr = "<tr name='datas'><td style='width:20px;'><input type='checkbox' name='box' id='"+datas[x].id+"'/></td>"
				  $tr += "<td name='algtype'>"+datas[x].algtype+"</td>";
				  $tr += "<td name='equipname'>"+datas[x].equipname+"</td>";
				  
				 // $tr += "<td name='faultdata'>"+datas[x].faultdata+"</td>";
				 var str = (datas[x].id).toString();
				 $tr += "<td name='faultdata'><button name='dataresult' onclick='showdata("+"\""+str+"\""+")' class='layui-btn'>"+"显示数据"+"</button></td>";
				  $tr += "<td name='faultresult'>"+datas[x].faultresult+"</td>";
				  var imgsrc = "./build/images/GetVerfiyCode.png";
				 // if(datas[x].faultresult!=0)
				  //{
				      $tr += "<td name='state'><img src='"+imgsrc+"'></td>";
				  //}
				  time = datas[x].time;
				  $tr += "<td>"+time+"</td></tr>";
				  $(".tr_1").after($tr);
				  }
				  layui.use('layer', function(){
					  var layer = layui.layer;
					  layer.close(index1);
				  });
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
		  url: 'getdataselect',//该方法位于ModelController中
		  data: formData,
		  processData : false, 
		  contentType : false,
		  success: function(result) {
			  var data=result.data;

              
              var root = document.getElementById("dataSelect");
              $("#dataSelect").empty();

	           for(var i = 0;i<data.length;i++)
	           {
	        	   var option=document.createElement("option");
	        	   option.setAttribute("value",data[i].id);
	        	   option.innerText=data[i].dataName;
	        	   root.appendChild(option);
	               layui.use('form', function(){
	            	  var form = layui.form;
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

function showdata(num)
{
	console.log(num);
	var FaultDataId = num;
	layui.use('layer', function(){ //独立版的layer无需执行这一句
		  var layer = layui.layer;
		  var index = layer.open({
			  type: 2,
			  title:'数据详情',
			  offset: 't',
			  area: ['750px', '500px'],
			  fixed: false, //不固定
			  maxmin: true,
			  content:['ShowSimpleData?FaultDataId='+FaultDataId]
			});
	});
}

function getmodelparam(data){
	var formData = new FormData();
	formData.append("modelparam",data.model);
	formData.append("equipId",data.id);
	$.ajax({
		  type: 'POST',
		  url: 'getmodelparam',
		  data: formData,
		  processData : false, 
		  contentType : false,
		  success: function (result){
			  console.log(result+"22222222222222");
				layui.use('layer', function(){ //独立版的layer无需执行这一句
					  var layer = layui.layer;
					  var index1 = layer.open({
						  type: 2,
						  title:'算法详情',
						  offset: 't',
						  area: ['350px', '500px'],
						  fixed: false, //不固定
						  maxmin: true,
						  content:['ShowAlgDetail?PARAM='+result+'&algtype='+data.model]
						});
				});
			  
		  }
		  ,error: function(){
          //请求出错处理
			  layer.msg('模型参数请求出错了', {icon: 2,time: 1000});
			  //layer.close(index1);
          }  
		});
}

function algdetailshow()
{
	var model1=document.getElementById("algSelect").value;
	console.log(model1+"111111111");
	switch(model1)
	{
	case "SVM":		  	
		var jsonDataModelParam = {id:parentId,model:"SVM"};
     	getmodelparam(jsonDataModelParam);
     	break;
	}
}


</script>
</body>
</html>