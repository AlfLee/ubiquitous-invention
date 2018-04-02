<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Document</title>
<c:import url="global.jsp"></c:import>

</head>
<body>
<div class="layui-layout layui-layout-admin kit-layout-admin">

             <ul class="layui-nav  kit-nav">
                <li class="layui-nav-item"><a href="rule;" >规则库</a></li>
                <li class="layui-nav-item"><a href="quantization;">量化</a></li>
            </ul> 
            <fieldset class="layui-elem-field layui-field-title">
              <legend></legend>
           </fieldset>
</div>
  <div >
  
 <!--  <h1  style="color:rgb(0,150,136);font-size:20px ">量化处理</h1><br> -->
  
  <form class="layui-form" action="" method="post">
 
<div  class="layui-form-item">
    <label class="layui-form-label">数据名称：</label>
    <div class="layui-input-inline">
      <input type="text" name="username" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
    </div>
  </div>
  
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">指标名称：</label>
      <div class="layui-input-inline">
        <select name="modules" >
          <option value=""></option>
          <option value="1"></option>
        </select>
      </div>
    </div> 
    </div>
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">量化规则：</label>
      <div class="layui-input-inline">
        <select name="modules" >
          <option value=""></option>
          <option value="1">二值化</option>
          <option value="2"></option>
        </select>
      </div>
    </div> 
    </div>
  
  <button type="button" style="height:35;width:61;background-color:rgb(0,150,136);margin:10px 10px 40px 40px" class="layui-btn layui-btn-normal" onclick="window.location.href='dataConfigure';">确定</button>
  <button type="button" style="height:35;width:61;background-color:rgb(0,150,136);margin:10px 10px 40px 40px" class="layui-btn layui-btn-normal" onclick="window.location.href='addData';">重置</button>
  </form>
</div>    
 <hr> 
<script>
layui.use(['form', 'layedit', 'laydate'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate;
  
  //日期
  laydate.render({
    elem: '#date'
  });
  laydate.render({
    elem: '#date1'
  });
  
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');
 
  //自定义验证规则
  form.verify({
    title: function(value){
      if(value.length < 5){
        return '标题至少得5个字符啊';
      }
    }
    ,pass: [/(.+){6,12}$/, '密码必须6到12位']
    ,content: function(value){
      layedit.sync(editIndex);
    }
  });
  
  //监听指定开关
  form.on('switch(switchTest)', function(data){
    layer.msg('开关checked：'+ (this.checked ? 'true' : 'false'), {
      offset: '6px'
    });
    layer.tips('温馨提示：请注意开关状态的文字可以随意定义，而不仅仅是ON|OFF', data.othis)
  });
  
  //监听提交
  form.on('submit(demo1)', function(data){
    layer.alert(JSON.stringify(data.field), {
      title: '最终的提交信息'
    })
    return false;
  });
  
  
});
</script>
</body>
</html>