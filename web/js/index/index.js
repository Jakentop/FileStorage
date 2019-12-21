
logo=new Vue({
    el:"#logo",
    data:{
        url:"img/logo.png",
        fit:"contain"
    }
})

header_btn=new Vue({
    el:"#header_btn",
    data:{
    },
    methods:{
        login:function () {
            login.dialog=!login.dialog;
        },
        register: function () {
            register.dialog = !register.dialog;

        }
    }
})

//用户登录
var login=new Vue({
    el:"#login",
    data:{
        UserName:"",
        Password:"",
        dialog:false
    },
    methods: {
        submit:function () {
        //    验证
            if(login.UserName!=""&&login.Password!="")
            {
            //    发起登录请求
                axios.post('user/login',Qs.stringify({
                    name:login.UserName,
                    password:login.Password
                })).then(function (res) {
                    if(res.data.status==200)
                    {
//                      登录成功
                        login.dialog=false;
                        login.$message({
                            message:"登录成功",
                            type:"success"
                        });
                        setTimeout(function () {
                            window.location.href = "main.jsp"
                        }, 1000);
                    }
                    else if(res.data.status==501)
                    {
                    //用户名密码错误
                        login.$message.error("用户名或密码错误");
                        login.Password="";
                    }
                })
                  .catch(function () {
                      login.$message.error("网络错误");
                  })
            }
        },
        closedialog:function () {
            login.dialog=false;
        }
    }
})

//注册
var register=new Vue({
    el:"#register",
    data:{
        UserName:"",
        Password:"",
        PasswordRetry:"",
        E_mail:"",
        loading:false,
        subtype:"primary",
        subinfo:"提交",
        dialog:false
    },
    methods:
        {
            closedialog: function () {
                register.dialog=false;
            },
            submit: function () {
            //    验证
                data={
                    name:register.UserName,
                    password: register.Password,
                    e_mail:register.E_mail,
                    type:0
                };
                if(data.password!=register.Password)
                {
                    register.$message.error("两次密码不一致");
                }
                else if(data.password.length<8)
                    register.$message.error("密码应大于8位");
                else if(!/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(data.e_mail))
                    register.$message.error("邮箱格式错误");
                else
                {
                //    界面动画
                    register.loading=true;
                //    执行POST请求
                    axios.post('user/register', Qs.stringify(data))
                        .then(function (res) {
                            if (res.data.status == 200) {
                                register.loading = false;
                                register.subtype = "success";
                                register.$message({
                                    type: "success",
                                    message: "注册成功"
                                });
                                register.dialog = false;
                            } else {
                                register.loading = false;
                                register.subtype = "danger";
                                if (res.data.status == 501)
                                    register.$messag.error("用户名或邮箱重复");
                                if (res.data.status == 555)
                                    register.$message.error("未知错误");
                            }
                        })
                        .catch(function () {
                            register.loading = false;
                            register.subtype = "danger";
                            register.$message.error("位置错误");
                        });
                }

            }

        }
})
