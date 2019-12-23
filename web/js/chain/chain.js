var title=new Vue({
    el:"#title"
})

var button=new Vue({
    el:"#button",
    data:{
      flag:false
    },
    methods: {
        reflush:function () {
            button.flag=true;
            toolbar.getdata();
        }
    }
})

var toolbar = new Vue({
    el: "#table",
    data: {
        tableData: []
    },
    methods: {
        getdata: function () {
            axios.post("chain/getall")
                .then(function (res) {
                    if (res.data.status != 200) {
                        StatusSearch(toolbar, res.data.status, '/chain/getall');
                    } else {
                        //    绑定值
                        toolbar.tableData = res.data.data.concat();
                    }

                }).catch(function () {
                toolbar.$message.error("网络错误");
            })
                .finally(function () {
                button.flag=false;
            })
        }
    }
});

toolbar.getdata();