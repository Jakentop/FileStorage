package Controllor.Dir;

import Dao.ChildNodeMapper;
import Dao.NodeMapper;
import Function.Msg;
import Model.ChildNode;
import Model.Node;
import Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.xml.transform.Templates;
import java.lang.reflect.Array;
import java.util.*;
import java.util.prefs.PreferenceChangeEvent;

@Controller
@RequestMapping(value = "/dir")
public class DirControllor {

// region dependiency 依赖
    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private ChildNodeMapper childNodeMapper;
// endregion

    /**
     * 获取当前节点下指定层次的目录，注意Node如果为0标识获取当前用户根目录
     * @param Deep
     * @param Node
     * @param session
     * @return
     */
    @RequestMapping(value = "/dir", method = RequestMethod.POST)
    public @ResponseBody
    String dir(@RequestParam(defaultValue = "1") int Deep,
               int Node,
               HttpSession session) {

//        验证
        try {
//            验证空
            if((Integer)Node==null) throw new Exception(((Integer) Msg.ERR).toString());
            User loginUser = (User) session.getAttribute("user");
            if(Node==0)
            Node=loginUser.getLogicnode();
//            验证当前节点是否存在
            if(nodeMapper.selectByPrimaryKey(Node)==null) throw new Exception(((Integer) Msg.ERR).toString());
//            验证当前节点是否有权限
            if (!AuthCheck(Node, loginUser)) {
                throw new Exception(((Integer) Msg.NoAuth).toString());
            }

        } catch (Exception e) {
            return Msg.ParseMap(Integer.parseInt(e.getMessage()), "/dir/dir", null);
        }

//        处理
        List<Map> res = new ArrayList<Map>();
        Stack<Integer> Nodequeue = new Stack<Integer>();
        Nodequeue.push(Node);//推入初始节点
        for (int i = 0; i < Deep; i++) {
            ArrayList<Integer> childID = new ArrayList<Integer>();
            ArrayList<Integer> childID_all = new ArrayList<Integer>();
            while(!Nodequeue.empty())
            {
                int curNodeID = Nodequeue.pop();
                Node cur = nodeMapper.selectByPrimaryKey(curNodeID);
//                获取当前节点下所有子节点
                List<Node> t = nodeMapper.selectAllChildNode(curNodeID);
                for (Node index : t) {
                    childID_all.add(index.getId());
                    childID.add(index.getId());
                }
//              将当前节点和子节点信息压入返回结果中
                res.add(tool.resMap(cur.getId(), cur.getName(), childID_all, cur.getParentnode()));
            }
//          保存下一层级子元素
            Nodequeue.addAll(childID);
        }

//      映射结果集，返回
        return Msg.ParseList(Msg.OK, "/dir/dir", res);
    }


    /**
     * 新建一个目录
     * @param CurNodeID
     * @param NewNodeName
     * @param session
     * @return
     */
    @RequestMapping(value = "/newdir", method = RequestMethod.POST)
    public @ResponseBody
    String newdir(Integer CurNodeID,
                  String NewNodeName,
                  HttpSession session) {
//        验证
        User loginUser = (User) session.getAttribute("user");
        try {
//            验证空
            if (CurNodeID == null || NewNodeName == null) throw new Exception(Msg.ERR.toString());

//            验证用户
            if (!AuthCheck(CurNodeID,loginUser)) throw new Exception(Msg.NoAuth.toString());

//            验证重名
            if(nodeMapper.selectChildByNodeIDAndNodeName(CurNodeID,NewNodeName)!=null)
                throw new Exception("501");

        } catch (Exception e) {
            return Msg.ParseMap((Integer.parseInt(e.getMessage())), "/dir/newdir", null);
        }
//        处理
        Node node = new Node();
        node.setUserid(loginUser.getParentid() == 0 ? loginUser.getId() : loginUser.getParentid());
        node.setName(NewNodeName);
        node.setParentnode(CurNodeID);
//        开始事务
        nodeMapper.insert(node);
        ChildNode childNode = new ChildNode();
        childNode.setParentid(CurNodeID);
        childNode.setChildid(node.getId());
        childNodeMapper.insert(childNode);

//        返回
        Map res = new HashMap();
        res.put("NodeID", node.getId());
        res.put("NodeName", node.getName());
        return Msg.ParseMap(Msg.OK, "/dir/newdir", res);
    }


    /**
     * 修改节点名称
     * @param NodeID
     * @param NodePreName
     * @param NodeNewName
     * @param session
     * @return
     */
    @RequestMapping(value = "/modifydirname", method = RequestMethod.POST)
    public @ResponseBody
    String modifydirname(Integer NodeID,
                         String NodePreName,
                         String NodeNewName,
                         HttpSession session) {
        User loginUser = (User) session.getAttribute("user");

//        验证
        try {
//            验空
            if(NodeID==null||NodePreName==null||NodeNewName==null) throw new Exception(Msg.ERR.toString());

//            节点存在性
            if(nodeMapper.selectByPrimaryKey(NodeID)==null) throw new Exception("501");

//            当前用户没有权限
            if(!AuthCheck(NodeID,loginUser)) throw new Exception(Msg.NoAuth.toString());

//            存在重名
            if(nodeMapper.selectChildByNodeIDAndNodeName(NodeID,NodeNewName)==null)
                throw new Exception("502");


        } catch (Exception e) {
            return Msg.ParseMap(Integer.parseInt(e.getMessage()), "/dir/modifydirname", null);
        }
//        处理

        Node node = new Node();
        node.setId(NodeID);
        node.setName(NodeNewName);
        nodeMapper.updateByPrimaryKeySelective(node);
//        返回
        Map res = new HashMap();
        res.put("NodeID", node.getId());
        res.put("NodeNewName", node.getName());

        return Msg.ParseMap(Msg.OK, "/dir/modifydirname", res);
    }

    /**
     * 移动某个节点的位置
     * @param CurNodeID
     * @param PreFatherNodeID
     * @param NowFatherNodeID
     * @param session
     * @return
     */
    @RequestMapping(value = "/modifydirposition", method = RequestMethod.POST)
    public @ResponseBody
    String modifydirposition(Integer CurNodeID,
                             Integer PreFatherNodeID,
                             Integer NowFatherNodeID,
                             HttpSession session) {
        User loginUser = (User) session.getAttribute("user");
        Node node = nodeMapper.selectByPrimaryKey(CurNodeID);
//        验证
        try {

//            验证空
            if (CurNodeID == null || PreFatherNodeID == null || NowFatherNodeID == null) {
                throw new Exception(Msg.ERR.toString());
            }

//            验证一致性
            if (node.getParentnode() != PreFatherNodeID) {
                throw new Exception(Msg.ERR.toString());
            }

//            验证节点是否存在
            if(nodeMapper.selectByPrimaryKey(CurNodeID)==null||
               nodeMapper.selectByPrimaryKey(PreFatherNodeID)==null||
               nodeMapper.selectByPrimaryKey(NowFatherNodeID)==null)
                throw new Exception("501");

//            权限验证
            if(!AuthCheck(CurNodeID,loginUser)||!AuthCheck(PreFatherNodeID,loginUser)||!AuthCheck(NowFatherNodeID,loginUser))
                throw new Exception(Msg.NoAuth.toString());


        } catch (Exception e) {
            return Msg.ParseMap(Integer.parseInt(e.getMessage()), "/dir/modifydirposition", null);
        }
//        处理
//          更新父节点记录
        node.setParentnode(NowFatherNodeID);
        nodeMapper.updateByPrimaryKey(node);
//          更新子节点记录
        ChildNode childNode = childNodeMapper.selectByParentChild(PreFatherNodeID, CurNodeID);
        childNode.setParentid(NowFatherNodeID);
        childNodeMapper.updateByPrimaryKey(childNode);
//        返回
        Map res = new HashMap();
        res.put("NowFatherNodeID", childNode.getParentid());

        return Msg.ParseMap(Msg.OK, "/dir/modifydirposition", res);


    }


// region tool 工具
    /**
     * 判断当前账号是否拥有节点权限
     * @param nodeID
     * @param loginUser
     * @return ture,有权限 false，没有权限
     *
     */
    public boolean AuthCheck(int nodeID,User loginUser)
    {
        //账号为子账号
        if(loginUser.getParentid()!=0)
        {   //验证主账号是否有权限
            if(nodeMapper.selectNodeByNodeIDAndUserID(nodeID,loginUser.getParentid())==null)
                return true;
//            递归验证子账号是否有权限
            Node Curnode = nodeMapper.selectByPrimaryKey(nodeID);
            Node t=Curnode;
            do{
                t= nodeMapper.selectByPrimaryKey(t.getParentnode());
            }
            while (t.getId()!=loginUser.getLogicnode()&&t.getParentnode()!=loginUser.getLogicnode());
//            考虑如果子账号获取主账号所有权限时
            if(t.getParentnode()==loginUser.getLogicnode() && t.getId()!=loginUser.getLogicnode() )
                return false;
            else
                return true;
        }
        //账号为主账号
        else
        {
            return !(nodeMapper.selectNodeByNodeIDAndUserID(nodeID,loginUser.getId())==null);
        }
    }

// endregion


}
