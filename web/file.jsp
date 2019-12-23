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
        <el-button class="button" v-on:click="Create">新建</el-button>
        <el-button class="button" @click="CreateDir">新建目录</el-button>
        <el-button class="button" @click="Rename">重命名</el-button>
        <el-button class="button" @click="Copy">{{copy}}</el-button>
        <el-button class="button" @click="Remove">{{move}}</el-button>
        <el-button class="button" @click="Delete">删除</el-button>
        <el-button class="button" @click="Upload" type="primary" style="margin-left:150px;">上传</el-button>
        <el-button class="button" @click="Download" type="success">下载</el-button>
    </div>

<%--    面包屑--%>
    <div id="navigation">
<%--        主体--%>
<div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
        <template v-for="item in Dirs">
            <el-breadcrumb-item  >
                <a :id="item.id" href="#" @click="ret($event)">{{item.name}}</a>
            </el-breadcrumb-item>
        </template>
    </el-breadcrumb>
</div>
<%--        上一级按钮--%>
    <el-button type="primary" icon="el-icon-back" @click="back">
        上一级</el-button>
    </div>

    <%--    filemain--%>
    <div id="filemain">
        <template v-for="(item, index)  in FileList">
            <span>
            <node-item
                    @getid="loadnext"
                    @getcheck="check"
                    :name="item.NodeName"
                        :NodeID="item.NodeID"
                        :Type="item.Type"
                        :ref="index"></node-item>
                </span>
        </template>
    </div>

<%--    新建框--%>
    <div id="newfile">
        <el-dialog
                :visible.sync="dialogVisible"
                width="30%">
            <div>请输入{{t}}名称：</div>
            <el-input v-model="filename" placeholder="请输入名称"></el-input>
            <div>
                <el-button @click="cancel" style="margin-left:15px">取消</el-button>
                <el-button @click="submit">提交</el-button>
            </div>
        </el-dialog>
    </div>

<%--    重命名框--%>
    <div id="rename">
        <el-dialog
                :visible.sync="dialogVisible"
                width="30%">
            <div>重命名</div>
            <div>
                <div>旧的名称：</div>
                <div>{{oldname}}</div>
            </div>
            <div>
                <div>新的名称：</div>
                <div>
                    <el-input v-model="newname" placeholder="请输入名称"></el-input>
                </div>
            </div>
            <div>
                <el-button @click="cancel" style="margin-left:15px">取消</el-button>
                <el-button @click="submit">提交</el-button>
            </div>

  </span>
        </el-dialog>
    </div>

</div>
</body>
<script src="js/StatusSearch.js"></script>
<script src="js/file/node-item.js"></script>
<script src="js/file/file.js"></script>

</html>
