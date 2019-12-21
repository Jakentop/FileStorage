

//route
var path = {
    "1": "tool.jsp",
    "2": "",
    "2-1":"",
    "2-2":"",
    "3": "",
    "4": "child.jsp",
    "5": ""
};

var route_main= new Vue({
    el: "#route_main",
    data:{
        //当前连接
        url:"tool.jsp",
        isCollapse: false
    },
    methods:{
        toindex: function () {
            window.location.href = 'index.jsp';
        },
        open_main: function (index) {
            console.log(index);
            route_main.url = path[index];
        }
    }
});