<%--
  Created by IntelliJ IDEA.
  User: zyz
  Date: 2019/12/22
  Time: 21:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>组件</title>
    <%--    引入js--%>
    <script src="js/vue/vue.js"></script>
    <script src="js/element/element.js"></script>
    <%--    引入ajax请求--%>
    <!-- ajax请求 -->
    <script src="js/vue/axios.min.js"></script>
    <script src="js/vue/sq.min.js"></script>
    <%--    引入样式--%>
    <link rel="stylesheet" href="css/element/element.css">
</head>
<body>
<div id="file">

    <div @dblclick="doubleCheck" v-on:click="onCheck"  style="width: 100px;height: 120px;text-align: center; border:1px solid rgba(0,0,0,0.1);
                                        position: relative; display: inline-block;margin: 10px;">

        <el-image style="width: 60px; height: 80px" :src="url" :fit="contain"></el-image>

        <div style="font-size: 10px;">{{name}}</div>

    <el-checkbox v-if="checked" v-model="checked" style="position:absolute;right: 2px;bottom:2px;"></el-checkbox>

    </div>
<node-item ref="node1"
            name="这是一个测试"
            NodeID=10
            Type=1></node-item>
    <node-item ref="node2"
               name="这是一个测试1"
               NodeID=11
               Type=0></node-item>
</div>
</body>
<script>

<%--    注册一个组件--%>
    Vue.component('node-item',{
        props:['name','NodeID','Type'],
        data: function () {
            return {
                checked:false
            }
        },
        computed:{
            url:function () {
                switch (this.Type) {
                    case "0":{
                        return 'img/icon/wenjian.png'
                    }break;
                    case "1":{
                        return 'img/icon/wenjian_1.png'
                    }break;
                }
            }
        },
        methods:{
        //    inner
            onCheck:function () {
                this.checked = !this.checked;
            },
            getInfo:function () {
                return this.data()
            },
            doubleCheck:function () {
                console.log(this)
                this.$emit('getid',this.$attrs.nodeid);
            }
        },
        template:"<div @dblclick=\"doubleCheck\" v-on:click=\"onCheck\"  style=\"width: 100px;height: 120px;text-align: center; border:1px solid rgba(0,0,0,0.1);\n" + "                                        position: relative; display: inline-block;margin: 10px;\">\n" + "\n" + "        <el-image style=\"width: 60px; height: 80px\" :src=\"url\" :fit=\"contain\"></el-image>\n" + "\n" + "        <div style=\"font-size: 10px;\">{{name}}</div>\n" + "\n" + "    <el-checkbox v-if=\"checked\" v-model=\"checked\" style=\"position:absolute;right: 2px;bottom:2px;\"></el-checkbox>\n" + "\n" + "    </div>"
    })

    var file= new Vue({
        el:"#file"
    })
</script>
</html>
