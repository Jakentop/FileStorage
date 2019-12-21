<%@ page import="Model.User" %><%--
  Created by IntelliJ IDEA.
  User: zyz
  Date: 2019/12/19
  Time: 18:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%--获取当前登录状态--%>
<%!User loginUser;%>
<%
    loginUser = (User) session.getAttribute("user");
%>
<c:set var="loginUser" value="<%=loginUser%>" scope="page" />
<head>
    <title>DolphinStorage</title>
<%--    引入js--%>
    <script src="js/vue/vue.js"></script>
    <script src="js/element/element.js"></script>
<%--    引入ajax请求--%>
    <!-- ajax请求 -->
    <script src="js/vue/axios.min.js"></script>
    <script src="js/vue/sq.min.js"></script>
<%--    引入样式--%>
    <link rel="stylesheet" href="css/element/element.css">
    <link rel="stylesheet" href="css/index.css">

</head>

<body>

<div class="header">
    <div class="container">
        <div class="left" id="logo">
            <el-image style=" width:150px; height: 60px;" :src="url" :fit="fit"></el-image>
        </div>
        <div class="right" id="header_btn">
            <c:if test="${loginUser==null}">
            <el-button type="primary" v-on:click="login">登录</el-button>
            <el-button type="info" v-on:click="register">注册</el-button>
            </c:if>
        </div>
    </div>
</div>
<%--条件渲染登录内容--%>
<c:if test="${loginUser==null}">
<%--        登录主体--%>
<div id="login">
    <%--            遮盖层--%>
    <transition name="el-fade-in">
        <div v-show="dialog" style="position:fixed;top:0px;left:0px;height: 100%;width:100%;background-color: rgba(0,0,0,0.6);"></div>
    </transition>
    <%--            登录主体--%>
    <transition name="el-zoom-in-top">
        <div v-show="dialog" class="login" >
            <%--            关闭按钮--%>
            <div class="closebtn" v-on:click="closedialog"><i class="el-icon-close"></i></div>
            <div class="title">Dolphin File Storage</div>
            <div>
                <%--        用户名--%>
                <div>
                    <div class="left">用户名：</div>
                    <div class="right"><el-input v-model="UserName" placeholder="UserName"></el-input></div>
                </div>
                <%--        密码--%>
                <div style="margin-top:15px;">
                    <div class="left">密码：</div>
                    <div class="right"><el-input show-password v-model="Password" placeholder="Password"></el-input></div>
                </div>
            </div>
            <%--    按钮--%>
            <div class="button">
                <el-button v-on:click="submit" type="success">登录</el-button>
                <el-button type="info">注册</el-button>
            </div>
        </div>
    </transition>

</div>
<%--        注册主体--%>
<div id="register">
    <%--            遮盖层--%>
    <transition name="el-fade-in">
        <div v-show="dialog" style="position:fixed;top:0px;left:0px;height: 100%;width:100%;background-color: rgba(0,0,0,0.6);"></div>
    </transition>
    <%--            注册主体--%>
    <transition name="el-zoom-in-top">
        <div v-show="dialog" class="register" >
            <%--            关闭按钮--%>
            <div class="closebtn" v-on:click="closedialog"><i class="el-icon-close"></i></div>
            <div class="title">Administrator Register</div>
            <div>
                <%--        用户名--%>
                <div>
                    <div class="left">用户名：</div>
                    <div class="right"><el-input v-model="UserName" placeholder="UserName"></el-input></div>
                </div>
                <%--        密码--%>
                <div style="margin-top:15px;">
                    <div class="left">密码：</div>
                    <div class="right">
                        <el-input show-password v-model="Password" placeholder="Password"></el-input>
                    </div>
                    <%--        重复密码--%>
                    <div style="margin-top:15px;">
                        <div class="left">再输入一次：</div>
                        <div class="right">
                            <el-input show-password v-model="PasswordRetry" placeholder="Password Again"></el-input>
                        </div>
                        <%--用户邮箱--%>
                        <div style="margin-top:15px;">
                            <div class="left">请输入邮箱</div>
                            <div class="right">
                                <el-input v-model="E_mail" placeholder="E_mail"></el-input>
                            </div>
                        </div>
                    </div>
                    <%--    按钮--%>
                    <div class="button">
                        <el-button v-on:click="submit" :loading="loading" :type="subtype">{{ subinfo }}</el-button>
                        <el-button>重置</el-button>
                    </div>
                </div>
            </div>
    </transition>

</div>
</c:if>

<div class="main">
    <div class="container">
<%--        网站内容--%>
    </div>
</div>
</body>
<script src="js/index/index.js"></script>


</html>