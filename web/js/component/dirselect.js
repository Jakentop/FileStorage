
Vue.component('dirselect', {
  data() {
    return {
      dialogVisible:true,
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
    //节点选择事件
    changecheck(node) {
      this.res = node;
    },
    //    确认选择节点
    submit() {
      this.dialogVisible = false;
    },
    //    取消选择节点
    cancel() {
      this.dialogVisible = false;
    }
  },
  template: "    <div>\n" +
      "        <el-dialog  file=\"选择\" :visible.sync=\"dialogVisible\" width=\"45%\" :before-close=\"handleClose\">\n" +
      "                <div style=\"width: 90%;height: 35%;overflow:auto;\">\n" +
      "                <el-tree\n" +
      "                        @check-change=\"changecheck\"\n" +
      "                        :props=\"props\"\n" +
      "                        :load=\"loadNode\"\n" +
      "                        show-checkbox\n" +
      "                        lazy>\n" +
      "                </el-tree>\n" +
      "                </div>\n" +
      "            <div style=\"margin-top:10px;\">\n" +
      "                <el-button @click=\"submit\">确认</el-button>\n" +
      "                <el-button @click=\"cancel\">取消</el-button>\n" +
      "            </div>\n" +
      "        </el-dialog>\n" +
      "    </div>"
})