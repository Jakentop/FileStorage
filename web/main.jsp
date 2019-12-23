<%@ page import="Model.User" %><%--
  Created by IntelliJ IDEA.
  User: zyz
  Date: 2019/12/19
  Time: 19:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!User loginUser;%>
<%
    loginUser = (User) session.getAttribute("user");
    if (loginUser == null) {
//        跳转
        response.sendRedirect("index.jsp");
    }
%>
<html>
<head>
    <title>Title</title>
    <script src="js/vue/vue.js"></script>
    <script src="js/element/element.js"></script>
    <link rel="stylesheet" href="css/element/element.css">
    <link rel="stylesheet" href="css/main.css">
<%--    导入路由js--%>
    <script src="js/vue/vue-router.js"></script>
<%--    引入css--%>
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/Animate/anmiate.css">
</head>
<body>
<div id="route_main" style="position: fixed;width:100%;height: 100%;">
    <el-container style="height: 100%;">
<%--        顶部--%>
        <el-header style="padding: 0px; height: 10%;">
            <div class="main-header">
                <el-image v-on:click="toindex" style="height: 100%; display: inline-block;" fit="contain" src="img/logo_main.png" ></el-image>
                <div class="right" class="right">
<%--                    用户信息显示--%>
                    <div style=" font-size: 16px; color: white;margin-top:4%;">
                        Welcome&nbsp;&nbsp; <%=loginUser!=null?loginUser.getUsername():""%>
                    </div>
                </div>
            </div>
        </el-header>
        <el-container class="container-main">
<%--            左边侧栏--%>
            <el-aside style="padding: 0px;width:15%;">
                <div>
                <div style="margin-top: 30px;"></div>
                    <el-menu default-active="1"
                             @select="open_main"
                             class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" :collapse="isCollapse">

                        <el-menu-item index="0">
                            <i class="el-icon-menu"></i>
                            <span slot="title">首页</span>
                        </el-menu-item>
                        <el-menu-item index="1">
                            <i class="el-icon-menu"></i>
                            <span slot="title">文件</span>
                        </el-menu-item>
                        <el-menu-item index="2">
                            <i class="el-icon-document"></i>
                            <span slot="title">外链管理</span>
                        </el-menu-item>
                        <el-menu-item index="3">
                            <i class="el-icon-setting"></i>
                            <span slot="title">子账号管理</span>
                        </el-menu-item>
                        <el-menu-item index="4">
                            <i class="el-icon-setting"></i>
                            <span slot="title">个人信息</span>
                        </el-menu-item>
                        <el-menu-item index="5">
                            <i class="el-icon-setting"></i>
                            <span slot="title">仪表盘</span>
                        </el-menu-item>
                        <el-menu-item index="6">
                            <i class="el-icon-setting"></i>
                            <span slot="title">Interface Test</span>
                        </el-menu-item>
                    </el-menu>
                </div>
            </el-aside>
<%--            主要区域--%>
            <el-main style="padding: 0px;width:80%;">
                <iframe  width="100%" frameborder="0" scrolling="auto" height="100%" :src="url"></iframe>
            </el-main>

</div>
</body>
<script src="js/main/main.js"></script>
</html>
