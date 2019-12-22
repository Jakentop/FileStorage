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
                <el-button @click="registerChild">新建</el-button>
                <el-button @click="view">查看</el-button>
                <el-button @click="modifyChild">修改</el-button>
                <el-button @click="delChild">删除</el-button>
            </el-row>
        </div>

<%--        表格--%>
    <el-table
            ref="table"
            :data="childData"
            height="400"
            stripe
            highlight-current-row
            @current-change="handleCurrentChange"
            style="width: 100%">
        <el-table-column
                prop="name"
                label="子账号名">
        </el-table-column>
        <el-table-column
                prop="logicanodeName"
                label="逻辑节点">
        </el-table-column>
        <el-table-column
                prop="e_mail"
                label="电子邮件">
        </el-table-column>
    </el-table>
    </div>
</div>


<%--查看子账号面板--%>
<div id="view" class="view">
    <el-dialog
            title="查看信息"
            :visible.sync="dialogVisible"
            width="35%">
    <div class="tr">
        <div class="left">用户名：</div>
        <div class="right">{{UserName}}</div>
    </div>
    <div class="tr">
        <div class="left">邮箱：</div>
        <div class="right">{{E_mail}}</div>
    </div>
    <div class="tr">
        <div class="left">逻辑节点</div>
        <div class="right">{{Logic}}</div>
    </div>
    </el-dialog>
</div>

<%--注册面板--%>
<div id="register" class="child-panel">
    <el-dialog :title="register_title" :visible.sync="register">
        <div class="table">
            <div class="tr">
                <div class="left">用户名：</div>
                <div class="right">
                    <el-input v-model="username" placeholder="请输入用户名"></el-input>
                </div>
            </div>
            <div class="tr">
                <div class="left">{{passtitle}}：</div>
                <div class="right">
                    <el-input placeholder="请输入密码" v-model="password" show-password></el-input>
                </div>
            </div>
            <div class="tr">
                <div class="left">重复{{passtitle}}：</div>
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
                    <el-button @click="choise">选择</el-button>
                    <div style="display: inline-block;" v-if="NodeName!=''">选择了：{{NodeName}}</div>
                </div>
            </div>
        </div>
        <div>
            <el-button @click="submit">确认</el-button>
            <el-button @click="cancel">取消</el-button>
        </div>
    </el-dialog>
</div>

<%--树形结构选择--%>
<div id="tree">
    <el-dialog  title="选择" :visible.sync="dialogVisible" width="45%">
        <div style="text-align: center;">请选择目录</div>
        <div style="width: 90%;height: 35%;overflow:auto;">
            <el-tree
                    ref="tree"
            <%--                        @check-change="changecheck"--%>
                    :props="props"
                    :load="loadNode"
                    lazy>
            </el-tree>
        </div>
        <div style="margin-top:10px;">
            <el-button @click="submit">确认</el-button>
            <el-button @click="cancel">取消</el-button>
        </div>
    </el-dialog>
</div>

<%--删除子账号--%>
<div id="delChild">
    <el-dialog
            title="提示"
            :visible.sync="dialogVisible"
            width="30%"
            >
        <div>是否要删除{{UserName}}</div>
        <div>
            <div>请输入管理员密码：</div>
            <div><el-input placeholder="请输入密码" v-model="password" show-password></el-input></div>
        </div>
        <div>
            <el-button @click="submit">确认</el-button>
            <el-button @click="cancel">取消</el-button>
        </div>
    </el-dialog>
</div>

</body>

<script src="js/child.js"></script>
</html>
