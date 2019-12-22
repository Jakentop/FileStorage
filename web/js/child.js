


var child=new Vue({
    el:"#child",
    data:{
        childData:[],
        curselect:""
    },
    methods:{
        getdata: function () {
            axios.post("child/getchilds", Qs.stringify({UserName: "zyz"}))
                .then(function (res) {
                    console.log(res);
                    if(res.data.status==200)
                    {
                        child.childData = res.data.data;
                    }
                    else
                        child.$message.error("网络错误");
                });
        },
    //    toolbar按钮事件
        registerChild: function () {
            register.register=true;
            register.register_title = "子账号注册";
            register.type = 'register';
        },
        modifyChild: function () {

        //    获取子账号信息
            if (this.curselect == "") {
                child.$message("请选择一个用户");
            }
            else{
                register.register=true;
                register.type = "modify";
                register.passtitle="主账号密码"
                register.register_title = "子账号修改";
            //    绑定值
                register.username=this.curselect.name;
                register.e_mail = this.curselect.e_mail;
            }


        },
        view: function () {
            //    获取子账号信息
            if (this.curselect == "") {
                child.$message("请选择一个用户");
            }
            else{
                view.dialogVisible=true;
                //    绑定值
                view.UserName=this.curselect.name;
                view.E_mail=this.curselect.e_mail;
                view.Logic = this.curselect.nodeName;
            }
        },
        delChild: function () {
            delChild.UserName=this.curselect.name;
            delChild.Password = "";
            delChild.dialogVisible = true;
        },


        //表格事件
        handleCurrentChange:function (val) {
            this.curselect=val;

        }
    }
})
//子账号注册面板
var register=new Vue({
    el:"#register",
    data:{
        register_title:"子账号注册",
        register:false,
        type:'register',
        username:"",
        password:"",
        repassword:"",
        e_mail:"",
        NodeName:"",
        logicnode:0,
        passtitle:"密码"
    },
    methods:{
        submit: function () {
            //注册
            if (this.type == "register") {
                //    子账号注册事件
                if (this.password == this.repassword && this.password > 8) {
                //    提交注册信息
                    axios.post("user/register", Qs.stringify({
                        name: this.username,
                        password: this.password,
                        e_mail: this.e_mail,
                        type: 1,
                        logicnode: this.logicnode
                    }))
                        .then(function (res) {
                        //    判断请求
                        //    刷新列表
                            child.getdata();
                        //    关闭窗口
                            register.clear();
                            register.register = false;
                        })
                }
                else{
                    register.$message.error("密码长度或两次密码不一致");
                }
            }
            //修改
            else{
                this.passtitle="主账号密码";
                //    提交注册信息
                axios.post("child/modifychildinfo", Qs.stringify({
                    ChildName: this.username,
                    CheckPassword: this.password,
                    E_Mail: this.e_mail,
                    type: 1,
                    LogicNode: this.logicnode
                }))
                    .then(function (res) {
                        //    判断请求
                        //    刷新列表
                        console.log("fsdjklfdsjfkl");
                        child.getdata();
                        //    关闭窗口
                        register.clear();
                        register.register = false;
                    }).catch(function () {
                    child.$message.error("网络错误");
                });
                this.passtitle="密码"

            }
        },
        cancel:function () {
            register.register = false;
            this.clear();
        },
        choise: function () {
            tree.dialogVisible = true;
            tree.type = "register";
        },
        //清空表单
        clear: function () {
            this.username = "";
            this.password = "";
            this.repassword = "";
            this.e_mail = "";
            this.Nodename = "";
            this.logicnode = 0;
        }
    }
})

//查看子账号信息面板
var view=new Vue({
    el: "#view",
    data: {
        UserName: "",
        E_mail: "",
        Logic:"",
        NodeID:"",
        dialogVisible:false
    }
})

//选择目录
var tree=new Vue({
    el:"#tree",
    data() {
    return {
        dialogVisible:false,
        type:"",
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
        switch (this.type) {

            case "register":
            {
                register.logicnode=tree.res.NodeID;
                register.NodeName=tree.res.NodeName;
            }

        }

    },
    //    取消选择节点
    cancel() {
        tree.dialogVisible = false;
    }


}
})

//删除子账号
var delChild=new Vue({
    el: "#delChild",
    data:{
        UserName: "",
        password: "",
        dialogVisible: false
    },
    methods:{
        submit: function () {
            if(this.password=="")
            {
                child.$message.error("请输入密码");
                return ;
            }
            axios.post("child/delchild", Qs.stringify({
                ChildName: this.UserName,
                Password: this.password
            }))
                .then(function (res) {
                    if (res.data.status == 200) {
                        child.$message("删除成功");
                    }
                });
            delChild.dialogVisible = false;
            setTimeout(child.getdata(), 2000);
        }
    }
})

child.getdata();