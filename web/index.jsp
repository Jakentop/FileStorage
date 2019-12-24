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
    <link rel="stylesheet" href="css/Animate/anmiate.css">

</head>

<body>

<%--标题--%>
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
            <c:if test="${loginUser!=null}">
                <div style="display: inline;">welcome <%=loginUser.getUsername()%></div>
                <el-link href="user/logout" type="info"><i class="el-icon-back"></i>退出</el-link>
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
        </div>
    </transition>

</div>
</c:if>


<%--视频背景--%>
<video autoplay muted loop>
    <source src="img/1.mp4">
</video>

<div class="main">
    <div style="width:100%;height: 100%;">
        <img style="width: 100%;height: 100%;" src="img/bglogo.png" style="display: block;">

        <%--架构描述--%>
        <div class="contain slideInDown" >
            <div class="title">开发框架</div>
            <div class="brief">
            <div>
                Spring+SpringMVC+MyBatis
            </div>
            <div>
                SqlServer
            </div>
            <div>
                Vue.js+Element
            </div>
            </div>
        </div>

<%--        功能图--%>
        <div style="width: 100%;padding:20px;padding-top:100px; background-color: #fff;">
            <div style="margin-left:50px; color:rgb(116,116,116);font-size: 38px;">模&nbsp;&nbsp;块</div>
            <div style="text-align: center;"><img  src="img/logicNode.jpg" alt=""></div>

        </div>

<%--        数据库--%>
        <div style="width: 100%;padding:20px;padding-top:200px; background-color: #fff;">
            <div style="text-align: center;font-size:30px;margin-top:50px;color:rgb(117,117,117);">数据库表关系</div>
            <div style="width: 100%;margin-top:40px;text-align: center;"><img src="img/sqlpic.png" alt=""></div>

        </div>
<%--        需求分析--%>
        <div style="width: 100%;padding:20px;padding-top:200px; background-color: #fff;">
            <div style="text-align:center; vertical-align: bottom; line-height: 800px; display: inline-block; width:40%; font-size:58px;color:black;">需求分析</div>
            <div style="width:50%;margin-top:40px; display: inline-block; text-align: center;"><img src="img/interface.jpg" alt="">
            </div>

    </div>

        <%--        网站内容--%>
</div>

</body>
<script src="js/index/index.js"></script>
</html>