<%--
  Created by IntelliJ IDEA.
  User: zyz
  Date: 2019/12/21
  Time: 11:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%--    引入js--%>
    <script src="js/vue/vue.js"></script>
    <script src="js/element/element.js"></script>
    <%--    引入ajax请求--%>
    <!-- ajax请求 -->
    <script src="js/vue/axios.min.js"></script>
    <script src="js/vue/sq.min.js"></script>
    <%--    引入样式--%>
    <link rel="stylesheet" href="css/element/element.css">

    <script src="js/component/dirselect.js"></script>
</head>
<body>
<div id="temp">
<el-upload
        class="upload-demo"
        ref="upload"
        action="transfer/upload"
        multiple="true"
        :data="uploadparams"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :file-list="fileList"
        :auto-upload="false">
    <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
    <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
</el-upload>
</div>

<div id="two">
    <dirselect></dirselect>
</div>
</body>
<script>
    var two=new Vue({
        el: "#two"
    })
    var temp=new Vue({
        el:"#temp",
        data() {
            return {
                fileList: [],
                uploadparams:{
                    NodeID:24
                }
            };
        },
        methods: {
            submitUpload() {
                this.$refs.upload.submit();
            },
            handleRemove(file, fileList) {
                console.log(file, fileList);
            },
            handlePreview(file) {
                console.log(file);
            }
        }
    })
</script>
</html>
