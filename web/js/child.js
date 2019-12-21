var child=new Vue({
    el:"#child",
    data:{
        childData:[]
    },
    methods:{
        getdata: function () {
            axios.post("child/getchilds", Qs.stringify({UserName: "zyz"}))
                .catch(function (res) {
                    console.log(res);
                });
        }
    }
})

var register=new Vue({
    el:"#register",
    data:{
        register:true,
        username:"",
        password:"",
        repassword:"",
        e_mail:""
    }
})

child.getdata();