package Controllor.File;

import Function.Msg;
import Model.ChildNode;
import Model.FileNode;
import Model.Node;
import Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/file")
public class ModifyFilePosition extends FileControllerFather {

    /**
     * 移动目录或者文件到一个新的目录下，PS没有实现跨节点移动阻止功能
     * @param UserName
     * @param Nodes
     * @param NewPosisitonNodeID
     * @param PrePositionNodeID
     * @param session
     * @return
     */
    @RequestMapping("/modifyfileposition")
    public @ResponseBody
    String modifyfileposition(String UserName,
                              String Nodes,
                              Integer NewPosisitonNodeID,
                              Integer PrePositionNodeID,
                              HttpSession session) {
        User loginUser = (User) session.getAttribute("user");
        List<innerNodes> NodeLists = new ArrayList<innerNodes>();
//        欺骗
        UserName = loginUser.getUsername();
        if(NewPosisitonNodeID==0) NewPosisitonNodeID = loginUser.getLogicnode();
        if(PrePositionNodeID==0) PrePositionNodeID = loginUser.getLogicnode();
//        验证
        try {
//          验证空
            if (UserName == null || Nodes == null || NewPosisitonNodeID == null || PrePositionNodeID == null) {
                throw new Exception(Msg.ERR.toString());
            }
//            用户登录一致性
            if (UserName != loginUser.getUsername()) {
                throw new Exception(Msg.LoginAuth.toString());
            }
//          预处理需要移动的节点
            for(String t : Nodes.split("\\|"))
            {
                if(t.length()<2) throw new Exception(Msg.ERR.toString());
                NodeLists.add(new innerNodes(Integer.parseInt(t.substring(0, 1)), Integer.parseInt(t.substring(1)),
                        nodeMapper,fileNodeMapper));
            }
//            节点存在性和权限
            boolean flag = true;
            for (innerNodes n : NodeLists) {
                if(n.checkExisit()==false||n.CheckAuth(loginUser)==false)
                {
                    flag=false;break;
                }
            }
            if(flag==false) throw new Exception("502");

        } catch (Exception e) {
            return Msg.ParseMap(Integer.parseInt(e.getMessage()), "/file/modifyfileposition", null);
        }
//        处理
        for (innerNodes inner : NodeLists) {
            if (inner.Type == 0) {
//                移动目录
                Node cur = nodeMapper.selectByPrimaryKey(inner.NodeID);
                childNodeMapper.deleteByParentIDAndChildID(cur.getParentnode(), cur.getId());
                cur.setParentnode(NewPosisitonNodeID);
                nodeMapper.updateByPrimaryKeySelective(cur);
                ChildNode childNode = new ChildNode();
                childNode.setChildid(cur.getId());
                childNode.setParentid(NewPosisitonNodeID);
                childNodeMapper.insert(childNode);
            }else
            {
                FileNode cur = fileNodeMapper.selectByPrimaryKey(inner.NodeID);
                cur.setNodeid(NewPosisitonNodeID);
                cur.setModifydate(new Date());
                fileNodeMapper.updateByPrimaryKeySelective(cur);
            }
        }
//        返回
        Map res = new HashMap();
        res.put("NodeID", NewPosisitonNodeID);
        return Msg.ParseMap(Msg.OK, "/file/modifyfileposition", res);
    }
}
