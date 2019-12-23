<%--
  Created by IntelliJ IDEA.
  User: zyz
  Date: 2019/12/23
  Time: 19:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>外链管理</title>
    <%--    引入js--%>
    <script src="js/vue/vue.js"></script>
    <script src="js/element/element.js"></script>
    <%--    引入ajax请求--%>
    <!-- ajax请求 -->
    <script src="js/vue/axios.min.js"></script>
    <script src="js/vue/sq.min.js"></script>
    <%--    引入样式--%>
    <link rel="stylesheet" href="css/element/element.css">
    <link rel="stylesheet" href="css/chain.css">
</head>
<body>
<div class="chain">

    <div id="title">
        <el-page-header title="" content="外链信息"></el-page-header>
    </div>
    <div id="button" style="margin:20px 0px 20px 25px;">
        <el-button  :loading.sync="flag" @click="reflush">刷新</el-button>
    </div>
<%--    外链表格--%>
    <div id="table">
        <el-table
                :data="tableData"
                style="width: 100%">
            <el-table-column
                    type="index"
                    width="50">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="外链标识"
                    width="400">
            </el-table-column>
            <el-table-column
                    prop="password"
                    label="外链密码"
                    width="250">
            </el-table-column>
            <el-table-column
                    prop="endtime"
                    label="外链截止日期"
                    width="250">
            </el-table-column>
        </el-table>
    </div>
</div>
</body>
<script src="js/StatusSearch.js"></script>
<script src="js/chain/chain.js"></script>

</html>
