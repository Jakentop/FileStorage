<%--
  Created by IntelliJ IDEA.
  User: zyz
  Date: 2019/12/20
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>child</title>
    <%--    引入js--%>
    <script src="js/vue/vue.js"></script>
    <script src="js/element/element.js"></script>
    <%--    引入ajax请求--%>
    <!-- ajax请求 -->
    <script src="js/vue/axios.min.js"></script>
    <script src="js/vue/sq.min.js"></script>
    <%--    引入样式--%>
    <link rel="stylesheet" href="css/element/element.css">
    <link rel="stylesheet" href="css/child.css">

</head>
<body>
<div id="child">
    <el-page-header  title="" content="子账号管理">
    </el-page-header>


    <div class="child-main">
<%--        操作--%>
        <div style="margin: 10px 0px 10px 20px;">
            <el-row>
                <el-button>新建</el-button>
                <el-button>查看</el-button>
                <el-button>修改</el-button>
                <el-button>删除</el-button>
            </el-row>
        </div>

<%--        表格--%>
    <el-table
            :data="childData"
            height="400"
            stripe
            style="width: 100%">
        <el-table-column
                prop="name"
                label="子账号名">
        </el-table-column>
        <el-table-column
                prop="logicnode"
                label="逻辑节点">
        </el-table-column>
        <el-table-column
                prop="e_mail"
                label="电子邮件">
        </el-table-column>
    </el-table>
    </div>
</div>

<%--面板--%>
<div id="register" class="child-panel">
    <el-dialog title="添加子账号" :visible.sync="register">
        <div class="table">
            <div class="tr">
                <div class="left">用户名：</div>
                <div class="right">
                    <el-input v-model="username" placeholder="请输入用户名"></el-input>
                </div>
            </div>
            <div class="tr">
                <div class="left">密码：</div>
                <div class="right">
                    <el-input placeholder="请输入密码" v-model="password" show-password></el-input>
                </div>
            </div>
            <div class="tr">
                <div class="left">重复密码：</div>
                <div class="right">
                    <el-input placeholder="请输入再输入密码" v-model="repassword" show-password></el-input>
                </div>
            </div>
            <div class="tr">
                <div class="left">邮箱：</div>
                <div class="right">
                    <el-input placeholder="请输入邮箱" v-model="e_mail" ></el-input>
                </div>
            </div>
            <div class="tr">
                <div class="left">逻辑节点：</div>
                <div class="right">
<%--                    目录选择组件--%>
                </div>
            </div>
        </div>
    </el-dialog>
</div>

</body>

<script src="js/child.js"></script>
</html>
