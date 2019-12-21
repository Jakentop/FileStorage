<%--
  Created by IntelliJ IDEA.
  User: zyz
  Date: 2019/12/16
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" http-equiv="Access-Control-Allow-Origin" content="*" >
    <!-- import CSS -->
    <link rel="stylesheet" href="css/element/element.css">
    <!-- import Vue before Element -->
    <script src="js/vue/vue.js"></script>
    <!-- import JavaScript -->
    <script src="js/element/element.js"></script>
    <!-- ajax请求 -->
    <script src="js/vue/axios.min.js"></script>
    <script src="js/vue/sq.min.js"></script>
</head>
<body>
<div style="text-align: center;margin-top:40px; font-size: 28px;">
    GET & POST interface test
</div>
<!-- 请求地址 -->
<div id="url" style="padding:20px;">
    <el-input style="display: inline-block;width:48%;" v-model="host" placeholder="请输入请求根"></el-input>
    <el-input style="display: inline-block;width:48%;" v-model="url" placeholder="请输入请求路径"></el-input>
</div>
<!-- 请求类型 -->
<div id='but'>
    <div>
        GET：<el-switch v-model="get"></el-switch>
        POST：<el-switch v-model="post" ></el-switch>
    </div>
    <!-- 参数 -->
    <div>
        <div v-if="post" style="margin-top:30px;padding: 20px;">POST参数：</div>
        <div v-if="get" style="margin-top:30px;padding: 20px;">GET参数</div>

        <div style="margin-top:10px;margin-left:5%;" v-for="(item,index) in items">
            <el-input placeholder="key"  v-model="item.key" style="display: inline-block;width: 40%;">
            </el-input>
            <el-input placeholder="value" v-model="item.value"style="display: inline-block;width: 40%;">
            </el-input>
            <el-button  v-on:click="delitem(index)" type="danger" icon="el-icon-delete" circle></el-button>
        </div>
        <div style="text-align: right;margin-top:20px;display: inline-block; width: 85%;">
            <el-button type="primary" v-on:click="additem" >添加</el-button>
        </div>
    </div>
</div>
</div>

<%--用户登录前置条件--%>
<div id="user">
    <div style="margin-top:15px;margin-bottom: 5px;">需要用户登录：<el-switch
            v-model="islogin"
            active-color="#13ce66"
            v-on:click="!islogin"
            inactive-color="#ff4949">
    </el-switch></div>
    <div v-if="islogin" style="margin-top: 15px;padding-left:20px; ">
        <div style="display: inline-block;width: 48%;">用户名：<el-input style="display: inline-block;width: 80%;" v-model="username" placeholder="请输入内容"></el-input></div>
        <div style="display: inline-block;width: 48%;">密码：<el-input style="display: inline-block;width: 80%;" v-model="password" placeholder="请输入内容"></el-input></div>
    </div>
</div>

<!-- 提交 -->
<div id="sub" style="text-align: center;">
    <el-button style="margin-top:50px; width:25%" v-on:click="submit" type="primary" :loading="status">{{text}}</el-button>
</div>

<%--结果显示--%>
<div>
    <div id="result">
        <el-timeline reverse="reverse" v-for="item in items">
<%--                标签--%>
            <el-timeline-item :timestamp="item.time" placement="top">
                <el-card style="padding:20px;">
                    <div>request:</div>
                    <div style="margin-left:15px;">url:{{item.url}}</div>
                    <div style="margin-left:15px;">params:{{item.param}}</div>
                    <div>response:</div>
                    <div style="margin-left:15px;">{{item.response}}</div>
                </el-card>
            </el-timeline-item>
        </el-timeline>
    </div>
</div>
</body>

<script>

    //获取浏览器地址前缀

    //  地址输入框
    var input= new Vue({
        el: '#url',
        data: {
            host:window.location.href.replace("tool.jsp",""),
            url:""
        }
    })
    //用户登录
    var userlog=new Vue({
        el:"#user",
        data:{
            islogin:true,
            username:"",
            password:""
        }
    })
    // 按钮与参数
    var button=new Vue({
        el:"#but",
        data:{
            get:false,
            param:"",
            items:[
                {key:"",value:""}

            ]
        },
        computed:{
            post:{
                get:function(){return !this.get},
                set:function(newValue){this.get=!newValue}
            }
        },
        methods:{
            delitem:function(index){
                this.items.splice(index,1)
            },
            additem:function()
            {
                this.items.push({key:"",value:""})

            }
        }
    })
    //结果显示
    var result=new Vue({
        el:"#result",
        data:{
            items:[]
        }
    });
    //提交
    var sub=new Vue({
        el:"#sub",
        data:{
            status:false,
            text:"提交"
        },
        methods:{
            submit:function(){
                // 获取数据
                var type=button.get
                var url=input.host+input.url;
                var list=button.items
                data={}
                list.forEach(function(i,index){
                    data[i.key]=i.value
                })
                var flag=(data!={"":""})
                // console.log(flag)
                // console.log(data)
                // console.log(url)
                //判断是否需要预处理用户登录状态
                if (userlog.islogin == true) {
                    //Post请求
                    axios({
                        method: 'post',
                        url: input.host+"user/login",
                        data: Qs.stringify({name:userlog.username, password: userlog.password})
                    })
                        .then(
                            function(response)
                            {
                                result.res=response.data;

                                result.$message(response.data.status==200?"登录成功":"用户名或密码错误")
                                console.log(response)
                            }
                        )
                        .catch(function (error) { // 请求失败处理
                            result.$message.error(error.message);
                            console.log(error);
                        });
                }
                // 开始处理
                if(flag==true)//判断是需要带参数
                {
                if(type==true)
                {//Get请求
                    axios
                    ({
                        method: 'get',
                        url: url,
                        data: Qs.stringify(data)
                    })
                    .then(
                        function(response)
                        {
                            result.res=response.data;
                            addresultitem(url,Qs.stringify(data),response.data)
                            console.log(response)
                        }
                    )
                    .catch(function (error) { // 请求失败处理
                    console.log(error);
                    result.$message.error(error.message);
                    });
                }
                else{
                    //Post请求
                    axios({
                        method: 'post',
                        url: url,
                        data: Qs.stringify(data)
                    })
                    .then(
                        function(response)
                        {
                            result.res=response.data;
                            addresultitem(url,Qs.stringify(data),response.data)
                            console.log(response)

                        }
                    )
                    .catch(function (error) { // 请求失败处理
                        result.$message.error(error.message);
                    console.log(error);
                    });
                }
                    }
                else{//无参数请求
                    if(type==true)
                {//Get请求
                    axios
                    ({
                        method: 'get',
                        url: url
                    })
                    .then(
                        function(response)
                        {
                            result.res=response.data;
                            addresultitem(url,"null",response.data)
                            console.log(response)
                        }
                    ).catch(function (error) { // 请求失败处理
                        result.$message.error(error.message);
                        console.log(error);
                    });
                }
                else{
                    //Post请求
                    axios.post(url).then(
                        function(response)
                        {
                            result.res=response.data;
                            addresultitem(url,"null",response.data)
                            console.log(response)
                        }
                    ).catch(function (error) { // 请求失败处理
                        result.$message.error(error.message);
                        console.log(error);
                    });
                }
                }

            }
        }
    })

    function addresultitem(url,param,response) {
        var day2 = new Date();
        var s2 = day2.getFullYear()+"-" + (day2.getMonth()+1) + "-" + day2.getDate()+'-'+day2.getHours()+":"+day2.getMinutes()+":"+day2.getSeconds();
        t={
            url,
            param,
            response,
            time:s2
        }
        result.items.splice(0,0,t)

    }
</script>
</html>