<%--
  Created by IntelliJ IDEA.
  User: zyz
  Date: 2019/12/22
  Time: 15:05
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
    <link rel="stylesheet" href="css/index.css">

    <script src="js/StatusSearch.js"></script>
</head>
<body>
    <div id="tree">
        <el-dialog  title="选择" :visible.sync="dialogVisible" width="45%" :before-close="handleClose">
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
</body>
<script>


    // 用户登录
    //Post请求
    axios({
        method: 'post',
        url: "user/login",
        data: Qs.stringify({name:'zyz', password: '1234567890'})
    })
        .then(
            function(response)
            {

                tree.$message(response.data.status==200?"登录成功":"用户名或密码错误")
                console.log(response)
            }
        )
        .catch(function (error) { // 请求失败处理
            tree.$message.error(error.message);
            console.log(error);
        });

    var tree=new Vue({
        el:"#tree",
        data() {
            return {
                dialogVisible:true,
                res:"",
                props: {
                    //tree
                    label: 'NodeName',
                    isLeaf: 'leaf',
                    children: 'zones',
                },
            };
        },
        methods: {
            //节点懒加载
            loadNode(node, resolve) {
                console.log(node);
                if (node.level === 0) {
                    //首次加载
                    axios.post("dir/dir", Qs.stringify({
                        Deep: 1,
                        Node: 0
                    })).then(function (res) {
                        if (res.data.status == 200) {
                            d = res.data.data;
                            return resolve(d);
                        }
                    });
                }
                if (node.level >= 1) {
//                  后续节点懒加载
                    axios.post("dir/dir", Qs.stringify({
                        Deep: 1,
                        Node: node.data.NodeID
                    })).then(function (res) {
                        if (res.data.status == 200) {
                            d = res.data.data;
                            return resolve(d);
                        }
                    });
                }
            },
            // //节点选择事件
            // changecheck(node) {
            //     tree.res = node;
            // },
            //    确认选择节点
            submit() {
                tree.res = tree.$refs.tree.getCurrentNode();
                tree.dialogVisible = false;
            },
            //    取消选择节点
            cancel() {
                tree.dialogVisible = false;
            }


        }
    })
</script>
</html>
