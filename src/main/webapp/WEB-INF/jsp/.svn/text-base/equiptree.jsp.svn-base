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
<style type="text/css">
body .layui-tree-skin-shihuang .layui-tree-branch{color: #EDCA50;}
.asasa {width:100%;height:100%;overflow: auto;}
.layui-nav {
	width:100%;height:100%;float: left;
    overflow: auto;
    white-space: nowrap;
    background-color: #FFFFFF;
    color: #fff;
    border-radius: 2px;
    box-sizing: inherit !important;
}
.ztree_a { background-color: #ddd !important;}

#equipmentList > li{
	position: relative;
    display: inline-block;
    
}
</style>
</head>

<body>
	<div class="asasa">
		<div id="equipmentList" class="layui-nav layui-nav-tree"></div>
	</div>
<script type="text/javascript">
//树
layui.use('tree', function(){
	var datas = ${datas};
	layui.tree({
		  elem: '#equipmentList' //传入元素选择器
		  ,skin:"shihuang"
		  ,nodes: datas
		  ,click: function(node){
			  //调用父页面的clicknode方法
			   parent.clickNode(node);
			}
		});
	});
	
	//选中样式
function selected(dom) {
    var collection = $(".layui-nav li a");
    $.each(collection, function () {
        $(this).removeClass("ztree_a");
    });
    $(dom).addClass("ztree_a");
}

</script>

</body>
</html>
