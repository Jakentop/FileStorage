


//标题
var title=new Vue({
    el: "#title",
    data:{

    },
    mehtods:{

    }
})

//工具条toolbar
var toolbar=new Vue({
    el: "#tool",
    data: {
        copy: "复制",
        move: "移动",
        buffer: [],
        type: 0,//0表示复制，1表示移动
        prenode:0
    },
    methods:{
//    注册按钮事件
//        新建
        Create:function () {
            newfile.dialogVisible=true;
            newfile.t="文件"
        },
    //    新建目录
        CreateDir:function(){
            newfile.dialogVisible=true;
            newfile.t="目录"
        },
    //    重命名
        Rename:function () {
            //获取当前选中的信息
            var t = filemain.CheckList;
            if(t.length==0) filemain.$message("请选择一个文件")
            if (t.length > 1) {
                filemain.$message("只能选中一个文件");
            }
            else{
                rename.oldname = t[0].name;
                rename.id = t[0].id;
                rename.type = t[0].type;
                rename.dialogVisible=true;
            }

        },
    //    复制
        Copy:function () {
            //判断当前功能
            if (this.copy == "复制") {
                if(this.move=="粘贴") this.$message("请先完成当前移动操作");
                else if(filemain.CheckList.length==0)this.$message("请至少选择一个文件");
                else
                {
                    //    开始记录复制信息
                    this.buffer = filemain.CheckList;
                    this.prenode=navigation.Dirs[navigation.Dirs.length - 1].id;
                    //    修改按钮作用
                    this.copy="粘贴"
                }
            }
            else if (this.copy == "粘贴") {
            //    粘贴操作
            //      计算请求参数
                let string=""
                string += this.buffer[0].type + "" + this.buffer[0].id;
                for (i = 1; i < this.buffer.length; i++) {
                    string += "|"+this.buffer[i].type + "" + this.buffer[i].id;
                }
                curfather = navigation.Dirs[navigation.Dirs.length - 1].id;
            //    设置请求参数
                let d={
                    PreNodeID:this.prenode,
                    NewNodeID:curfather,
                    CopyNodes:string
                }
                axios.post("file/copyfile", Qs.stringify(d))
                    .then(function (res) {
                        if (res.data.status != 200)
                            StatusSearch(tool, res.data.status, "/file/copyfile");
                    })
                    .catch(function () {
                        filemain.$message.error("网络错误");
                    });
            }

        },
    //    移动
        Remove:function () {

        },
    //    删除
        Delete:function () {

        },
    //    上传
        Upload:function () {

        },
    //    下载
        Download: function () {


        }

    }
})

//面包屑navigation
var navigation=new Vue({
    el:'#navigation',
    data:{
        Dirs:[]
    },
    methods: {
        //跳转到某层
        ret:function (nodeid) {
            var clickid = nodeid.toElement.id;
            console.log(nodeid);
            var name=nodeid.toElement.text;
        //    根节点特殊
            if (nodeid == 0) {
                filemain.getFileList(0,'/');
            }
        //    开始推Dirs
            while(this.Dirs.pop().id!=clickid){};
            console.log(clickid)
            filemain.getFileList(clickid,name);
        },
        //返回上层
        back:function () {
            if (this.Dirs.length >= 2) {
                this.Dirs.pop();
                var d = this.Dirs.pop();
                console.log(1);
                filemain.getFileList(d.id, d.name);
            }
            else{
                navigation.$message("已经到达根目录");
            }
        },
    //    刷新当前层
        reflush: function () {
            if (this.Dirs.length >= 1) {
                var d = this.Dirs.pop();
                console.log(1);
                filemain.getFileList(d.id, d.name);
            }
        }
    }
})

//文件主类filemain
var filemain=new Vue({
     el:"#filemain",
    data:function () {
        return{
            FileList:[],
            CheckList:[]
        }

    },
    methods:{
        //根据当前层NodeID获取内部目录和文件
        getFileList:function (NodeID,Name) {
            d={"Deep":1};
            if(NodeID!==0)
                d['NodeID']=NodeID;
            axios.post("file/getfiletree", Qs.stringify(d))
                .then(function (res) {
                    //请求成功绑值
                    if (res.data.status == 200) {
                        console.log(res.data.data.Node);
                        filemain.FileList=res.data.data.Node;
                    //    修改当前节点
                        navigation.Dirs.push({"name":Name,"id":NodeID});
                    //    清空当前选择项
                        filemain.CheckList=[]
                    }
                    else {
                        StatusSearch(filemain,res.data.status,"/file/getfiletree")
                    }

                });
        },
    //    元素双击事件，向下加载
        loadnext:function (e) {
            this.getFileList(e.id,e.name);
        },
    //    单击选中事件
        check: function (e,type) {
            if(type==1)
            this.CheckList.push(e);
            else if (type == 0) {
                if(this.CheckList.length==1)this.CheckList=[]
                //移除元素
                else
                this.CheckList=this.CheckList.splice(this.CheckList.indexOf(e),1)
            }
        }
    }
})

//新建对话框
var newfile=new Vue({
    el:"#newfile",
    data:function () {
        return{
            t:"文件",
            filename:"",
            dialogVisible:false
        }
    },
    methods:{
        // 提交事件
        submit: function () {
            if (this.t == "文件") {
                if (this.filename == "") {this.$message("请输入名称");}
                //    准备请求数据
                let id = navigation.Dirs[navigation.Dirs.length - 1].id;
                let d={FileName:this.filename,
                    NodeID:id};
                newfile.filename = "";
                axios.post('file/newfile', Qs.stringify(d))
                    .then(function (res) {
                        if (res.data.status != 200) {
                            StatusSearch(newfile, res.data.status, '/file/newfile');
                        }
                        else
                        {
                            //刷新当前层
                            navigation.reflush();
                            newfile.$message("新建成功");
                            newfile.dialogVisible=false;
                        }
                    })
                    .catch(function () {
                        newfile.$message.error("网络错误")
                    });

            }
            else{
                if (this.filename == "") {this.$message("请输入名称");}
                //    准备请求数据
                let id = navigation.Dirs[navigation.Dirs.length - 1].id;
                let d={NewNodeName:this.filename,
                    CurNodeID:id};
                newfile.filename = "";

                axios.post('dir/newdir', Qs.stringify(d))
                    .then(function (res) {
                        if (res.data.status != 200) {
                            StatusSearch(newfile, res.data.status, '/dir/newdir');
                        }
                        else
                        {
                            //刷新当前层
                            navigation.reflush();
                            newfile.$message("新建成功");
                            newfile.dialogVisible=false;
                        }
                    })
                    .catch(function () {
                        newfile.$message.error("网络错误")
                    });
            }



        },
    //    取消事件
        cancel: function () {
            this.filename = '';
            this.dialogVisible = false;
        }
    }
})

//重命名对话框
var rename=new Vue({
    el:"#rename",
    data:{
        dialogVisible:false,
        oldname: "",
        newname: "",
        id:0,
        type:0
    },
    methods:{
        //关闭清理
        clear: function () {
            this.oldname = "";
            this.newname = "";
            this.id = 0;
        },
        //提交事件
        submit: function () {
            if(this.newname=="") this.$message("请输入文件名称");
            if (this.type == 0) {
                //    发起请求
                let d = {
                    NodeID:this.id,
                    NodePreName:this.oldname,
                    NodeNewName:this.newname
                };
                axios.post("dir/modifydirname", Qs.stringify(d))
                    .then(function (res) {
                        if (res.data.status != 200) {
                            StatusSearch(rename, res.data.status, '/dir/modifydirname');
                        }
                        else{
                            rename.$message("修改成功");
                            rename.dialogVisible = false;
                            navigation.reflush();
                            rename.clear();
                        }

                    })
            }
            else{
                //    文件改名请求
                let d = {
                    FileNode:this.id,
                    FilePreName:this.oldname,
                    FileNewName:this.newname
                };
                axios.post("file/modifyfilename", Qs.stringify(d))
                    .then(function (res) {
                        if (res.data.status != 200) {
                            StatusSearch(rename, res.data.status, '/file/modifyfilename');
                        }
                        else{
                            rename.$message("修改成功");
                            rename.dialogVisible = false;
                            navigation.reflush();
                            rename.clear();
                        }

                    })
            }


        },
        cancel: function () {
            this.dialogVisible = false;
        }
    }
})

filemain.getFileList(0,'/');