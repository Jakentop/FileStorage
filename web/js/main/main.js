

//route
var path = {
    "0": "",
    "1": "file.jsp",
    "2": "chain.jsp",
    "3": "child.jsp",
    "6": "tool.jsp"
};

var route_main= new Vue({
    el: "#route_main",
    data:{
        //当前连接
        url:"file.jsp",
        isCollapse: false
    },
    methods:{
        toindex: function () {
            window.location.href = 'index.jsp';
        },
        open_main: function (index) {
            if (index == 0) {
                window.location.href='index.jsp'
            }
            console.log(index);
            route_main.url = path[index];
        }
    }
});