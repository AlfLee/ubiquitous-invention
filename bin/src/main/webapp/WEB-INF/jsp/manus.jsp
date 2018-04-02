<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理端</title>
<c:import url="global.jsp"></c:import> 
    <script>
        var message;
        layui.config({
            base: 'build/js/'
        }).use(['app', 'message'], function() {
            var app = layui.app,
                $ = layui.jquery,
                layer = layui.layer;
            //将message设置为全局以便子页面调用
            message = layui.message;
            //主入口
            app.set({
                type: 'iframe'
            }).init();
            $('#pay').on('click', function() {
                layer.open({
                    title: false,
                    type: 1,
                    content: '<img src="/build/images/pay.png" />',
                    area: ['500px', '250px'],
                    shadeClose: true
                });
            });
        });
    </script>
</head>
<body>
<div class="layui-layout layui-layout-admin kit-layout-admin">
        <div class="layui-header">
            <div class="layui-logo">软硬件系统可靠性分析平台</div>
           <!--  <ul class="layui-nav layui-layout-left kit-nav">
                <li class="layui-nav-item"><a href="javascript:;">控制台</a></li>
                <li class="layui-nav-item"><a href="javascript:;">商品管理</a></li>
                <li class="layui-nav-item"><a href="javascript:;" id="pay"><i class="fa fa-gratipay" aria-hidden="true"></i> 捐赠我</a></li>
                <li class="layui-nav-item">
                    <a href="javascript:;">其它系统</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;">邮件管理</a></dd>
                        <dd><a href="javascript:;">消息管理</a></dd>
                        <dd><a href="javascript:;">授权管理</a></dd>
                    </dl>
                </li>
            </ul> -->
            <ul class="layui-nav layui-layout-right kit-nav">
                <li class="layui-nav-item">
                    <a href="javascript:;">
                        <img src="http://m.zhengjinfan.cn/images/0.jpg" class="layui-nav-img"> ${username }
                    </a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;">基本资料</a></dd>
                        <dd><a href="javascript:;">安全设置</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item"><a href="index"><i class="fa fa-sign-out" aria-hidden="true"></i> 注销</a></li>
            </ul>
        </div>

        <div class="layui-side layui-bg-black kit-side">
            <div class="layui-side-scroll">
                <div class="kit-side-fold"><i class="fa fa-navicon" aria-hidden="true"></i></div>
                <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
                <ul class="layui-nav layui-nav-tree" lay-filter="kitNavbar" kit-navbar>
                    <li class="layui-nav-item">
                        <a class="" href="javascript:;"><i class="fa fa-plug" aria-hidden="true"></i><span> 数据配置</span></a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="javascript:;" kit-target data-options="{url:'datainput',icon:'&#xe658;',title:'数据导入',id:'1'}">
                                    <i class="layui-icon">&#xe658;</i><span>数据导入</span></a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="" data-icon="&#xe658;" data-title="离散化" kit-target data-id='2'><i class="layui-icon">&#xe658;</i><span>离散化</span></a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="index" data-icon="&#xe658;" data-title="量化" kit-target data-id='3'><i class="layui-icon">&#xe658;</i><span>量化</span></a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="dataint" data-icon="&#xe658;" data-title="归一化" kit-target data-id='4'><i class="layui-icon">&#xe658;</i><span>归一化</span></a>
                            </dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item"><!--  layui-nav-itemed默认展开 -->
                        <a href="javascript:;"><i class="fa fa-plug" aria-hidden="true"></i><span>特征分解</span></a>
                        <dl class="layui-nav-child">
                            <dd><a href="javascript:;" kit-target data-options="{url:'',icon:'&#xe658;',title:'时域分解',id:'6'}"><i class="layui-icon">&#xe658;</i><span> 时域分解</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'',icon:'&#xe658;',title:'频域分解',id:'7'}"><i class="layui-icon">&#xe658;</i><span> 频域分解</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'',icon:'&#xe658;',title:'时频域分解',id:'50'}"><i class="layui-icon">&#xe658;</i><span> 时频域分解</span></a></dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:;" data-url="" data-name="table" kit-loader><i class="fa fa-plug" aria-hidden="true"></i><span> 特征选择</span></a>
                    	<dl class="layui-nav-child">
                            <dd><a href="javascript:;" kit-target data-options="{url:'',icon:'&#xe658;',title:'多目标筛选',id:'8'}"><i class="layui-icon">&#xe658;</i><span> 多目标筛选</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'',icon:'&#xe658;',title:'PCA',id:'9'}"><i class="layui-icon">&#xe658;</i><span> PCA</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'',icon:'&#xe658;',title:'ICA',id:'10'}"><i class="layui-icon">&#xe658;</i><span> ICA</span></a></dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:;" data-url="" data-name="form" kit-loader><i class="fa fa-plug" aria-hidden="true"></i><span> 模型训练</span></a>
                    	<dl class="layui-nav-child">
                            <dd><a href="javascript:;" kit-target data-options="{url:'',icon:'&#xe658;',title:'分类',id:'8'}"><i class="layui-icon">&#xe658;</i><span>分类</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'',icon:'&#xe658;',title:'回归',id:'9'}"><i class="layui-icon">&#xe658;</i><span> 回归</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'',icon:'&#xe658;',title:'随机过程',id:'10'}"><i class="layui-icon">&#xe658;</i><span> 随机过程</span></a></dd>
                        </dl>
                    </li>
                     <li class="layui-nav-item">
                        <a href="javascript:;" data-url="" data-name="table" kit-loader><i class="fa fa-plug" aria-hidden="true"></i><span> 故障诊断</span></a>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:;" data-url="" data-name="form" kit-loader><i class="fa fa-plug" aria-hidden="true"></i><span> 剩余寿命预测</span></a>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:;" data-url="" data-name="form" kit-loader><i class="fa fa-plug" aria-hidden="true"></i><span> 系统管理</span></a>
                   		<dl class="layui-nav-child">
                            <dd><a href="javascript:;" kit-target data-options="{url:'equipment',icon:'&#xe658;',title:'设备管理',id:'8'}"><i class="layui-icon">&#xe658;</i><span>设备管理</span></a></dd>
                        </dl>
                    </li>
                </ul>
            </div>
        </div>
        <div class="layui-body" id="container">
            <!-- 内容主体区域 -->
            <div style="padding: 15px;">主体内容加载中,请稍等...</div>
        </div>

        <div class="layui-footer">
           <!--  底部固定区域 -->
            2018 &copy;
            <!-- <a href="http://kit.zhengjinfan.cn/">kit.zhengjinfan.cn/</a> --> MIT license

        </div> 
    </div>

</body>
</html>