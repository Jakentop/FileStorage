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
                    <el-menu default-active="1" @select="open_main" default-openeds="[2]" class="el-menu-vertical-demo" :collapse="isCollapse">
                        <el-menu-item index="1">
                            <template slot="file">
                                <i class="el-icon-location"></i>
                                <span slot="file">文件存储</span>
                            </template>
                        </el-menu-item>

                        <el-submenu index="2">
                            <template slot="file">
                                <i class="el-icon-location"></i>
                                <span slot="file">外链管理</span>
                            </template>
                            <el-menu-item-group>
                                <el-menu-item index="2-1">外链查看</el-menu-item>
                                <el-menu-item index="2-2">外链延期</el-menu-item>
                            </el-menu-item-group>
                        </el-submenu>
                        </el-submenu>

                        <el-menu-item index="3">
                            <template slot="file">
                                <i class="el-icon-location"></i>
                                <span slot="file">个人设置</span>
                            </template>
                        </el-menu-item>

                        <el-menu-item index="4">
                            <template slot="file">
                                <i class="el-icon-location"></i>
                                <span slot="file">子账号设置</span>
                            </template>
                        </el-menu-item>

                        <el-menu-item index="5">
                            <template slot="file">
                                <i class="el-icon-location"></i>
                                <span slot="file">仪表盘</span>
                            </template>
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
