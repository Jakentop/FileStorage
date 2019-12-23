
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
                case 0:{
                    return 'img/icon/wenjian.png'
                }break;
                case 1:{
                    return 'img/icon/wenjian_1.png'
                }break;
            }
        }
    },
    methods:{
        //    inner
        onCheck:function () {
            this.checked = !this.checked;

                var d={"name":this.$props.name,"id":this.$attrs.nodeid,'type':this.$props.Type};
                this.$emit('getcheck',d,this.checked?1:0);



        },
        getInfo:function () {
            return this.data()
        },
        doubleCheck:function () {

            var d={"name":this.$props.name,"id":this.$attrs.nodeid};
            this.$emit('getid',d);
        }
    },
    template:"<div @dblclick=\"doubleCheck\" v-on:click=\"onCheck\"  style=\"width: 100px;height: 120px;text-align: center; border:1px solid rgba(0,0,0,0.1);\n" + "                                        position: relative; display: inline-block;margin: 10px;\">\n" + "\n" + "        <el-image style=\"width: 60px; height: 80px\" :src=\"url\" ></el-image>\n" + "\n" + "        <div style=\"font-size: 10px;\">{{name}}</div>\n" + "\n" + "    <el-checkbox v-if=\"checked\" v-model=\"checked\" style=\"position:absolute;right: 2px;bottom:2px;\"></el-checkbox>\n" + "\n" + "    </div>"
})