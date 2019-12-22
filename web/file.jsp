<%--
  Created by IntelliJ IDEA.
  User: zyz
  Date: 2019/12/22
  Time: 21:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>file</title>
    <%--    引入js--%>
    <script src="js/vue/vue.js"></script>
    <script src="js/element/element.js"></script>
    <%--    引入ajax请求--%>
    <!-- ajax请求 -->
    <script src="js/vue/axios.min.js"></script>
    <script src="js/vue/sq.min.js"></script>
    <%--    引入样式--%>
    <link rel="stylesheet" href="css/element/element.css">
    <link rel="stylesheet" href="css/file.css">
</head>
<body>
<div class="file">
<div id="title">
    <el-page-header title="" content="文件管理">
    </el-page-header>
</div>

    <%--    toolbar--%>
    <div id="tool" class="toolbar">
        <el-button class="button" @click="Create">新建</el-button>
        <el-button class="button" @click="Rename">重命名</el-button>
        <el-button class="button" @click="Copy">复制</el-button>
        <el-button class="button" @click="Remove">移动</el-button>
        <el-button class="button" @click="Delete">删除</el-button>
        <el-button class="button" @click="Upload" type="primary" style="margin-left:150px;">上传</el-button>
        <el-button class="button" @click="Download" type="success">下载</el-button>
    </div>
    <%--    filemain--%>
    <div>
<%--        这是一个文件--%>
        <div>

        </div>
    </div>

</div>
</body>
<script src="js/file/file.js"></script>
</html>
