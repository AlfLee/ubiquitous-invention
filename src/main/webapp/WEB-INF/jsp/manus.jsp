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
                        <a class="" href="javascript:;"><i class="fa fa-joomla" aria-hidden="true"></i><span> 数据配置</span></a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="javascript:;" kit-target data-options="{url:'datainput',icon:'fa fa-caret-square-o-up',title:'数据导入',id:'1'}">
                                    <i class="fa fa-caret-square-o-up"></i><span> &nbsp;数据导入</span></a>
                            </dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'quantization',icon:'&#xe658;',title:'量化处理',id:'2'}">
                                    <i class="fa fa-slack"></i><span> &nbsp;量化处理</span></a></dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item"><!--  layui-nav-itemed默认展开 -->
                        <a href="javascript:;"><i class="fa  fa-xing-square" aria-hidden="true"></i><span> 特征分解</span></a>
                        <dl class="layui-nav-child">
                            <dd><a href="javascript:;" kit-target data-options="{url:'timeDomain',icon:'fa fa-puzzle-piece',title:'时域分解',id:'3'}"><i class="fa fa-puzzle-piece"></i><span> &nbsp;时域分解</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'frequencyDomain',icon:'fa fa-rebel',title:'频域分解',id:'4'}"><i class="fa fa-rebel"></i><span> &nbsp;频域分解</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'timeFrequency',icon:'fa fa-stumbleupon',title:'时频分解',id:'5'}"><i class="fa fa-stumbleupon"></i><span> &nbsp;时频分解</span></a></dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:;" kit-target data-options="{url:'featureSelect',icon:'fa fa-wordpress',title:'特征选择',id:'6'}" kit-loader><i class="fa  fa-youtube-square" aria-hidden="true"></i><span> 特征选择</span></a>
                    	<!-- <dl class="layui-nav-child">
                            <dd><a href="javascript:;" kit-target data-options="{url:'mulTarget',icon:'fa fa-legal (别称)',title:'多目标',id:'8'}"><i class="fa fa-legal (别称)"></i><span> &nbsp;多目标</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'PCA',icon:'fa fa-life-bouy (别称)',title:'PCA',id:'9'}"><i class="fa fa-life-bouy (别称)"></i><span> &nbsp;PCA</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'ICA',icon:'fa fa-paper-plane-o',title:'ICA',id:'10'}"><i class="fa fa-paper-plane-o"></i><span> &nbsp;ICA</span></a></dd>
                        </dl> -->
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:;" kit-target data-options="{url:'modelTrain',icon:'fa fa-wordpress',title:'模型训练',id:'7'}" kit-loader><i class="fa fa-user-md" aria-hidden="true"></i><span> 模型训练</span></a>
                    	<!-- <dl class="layui-nav-child">
                            <dd><a href="javascript:;" kit-target data-options="{url:'classify',icon:'fa fa-puzzle-piece',title:'分类',id:'8'}"><i class="fa fa-puzzle-piece"></i><span> &nbsp;分类</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'regression',icon:'fa fa-tachometer',title:'回归',id:'9'}"><i class="fa fa-tachometer"></i><span> &nbsp; 回归</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'stochastic',icon:'fa fa-turkish-lira (别称)',title:'随机过程',id:'10'}"><i class="fa fa-turkish-lira (别称)"></i><span>  &nbsp;随机过程</span></a></dd>
                        </dl> -->
                    </li>
                     <li class="layui-nav-item">
                        <a href="javascript:;" kit-target data-options="{url:'analysis',icon:'fa fa-wordpress',title:'故障诊断',id:'8'}" ><i class="fa fa-wordpress" aria-hidden="true"></i><span> 故障诊断</span></a>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:;" kit-target data-options="{url:'predict',icon:'fa fa-spotify',title:'寿命预测',id:'9'}" kit-loader><i class="fa fa-spotify" aria-hidden="true"></i><span> 寿命预测</span></a>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:;" data-url="" data-name="form" kit-loader><i class="fa fa-plug" aria-hidden="true"></i><span> 系统管理</span></a>
                   		<dl class="layui-nav-child">
<<<<<<< .mine
                            <dd><a href="javascript:;" kit-target data-options="{url:'equipment',icon:'fa fa-paragraph',title:'设备管理',id:'10'}"><i class="fa fa-paragraph"></i><span> &nbsp;设备管理</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'user',icon:'fa fa-tachometer',title:'用户管理',id:'11'}"><i class="fa fa-tachometer"></i><span> &nbsp;用户管理</span></a></dd>
=======
                            <dd><a href="javascript:;" kit-target data-options="{url:'equipment',icon:'fa fa-paragraph',title:'设备管理',id:'9'}"><i class="fa fa-paragraph"></i><span> &nbsp;设备管理</span></a></dd>
                            <dd><a href="javascript:;" kit-target data-options="{url:'user',icon:'fa fa-tachometer',title:'用户管理',id:'8'}"><i class="fa fa-tachometer"></i><span> &nbsp;用户管理</span></a></dd>
>>>>>>> .r18837
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