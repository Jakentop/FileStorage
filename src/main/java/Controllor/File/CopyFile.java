package Controllor.File;

import Function.Msg;
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
public class CopyFile extends FileControllerFather {

    /**
     * 文件复制，实现对目录或者文件的复制
     * @param UserName
     * @param PreNodeID
     * @param NewNodeID
     * @param CopyNodes
     * @param session
     * @return
     */
    @RequestMapping("copyfile")
    public @ResponseBody
    String copyfile(String UserName,
                    Integer PreNodeID,
                    Integer NewNodeID,
                    String CopyNodes,
                    HttpSession session) {
        User loginUser = (User) session.getAttribute("user");
        List<innerNodes> NodeLists = new ArrayList<innerNodes>();
//        欺骗
        UserName = loginUser.getUsername();
        if(PreNodeID==0) PreNodeID = loginUser.getLogicnode();
        if(NewNodeID==0) NewNodeID = loginUser.getLogicnode();
//        验证
        try {
//          验证空
            if (UserName == null || PreNodeID == null || NewNodeID == null || CopyNodes == null) {
                throw new Exception(Msg.ERR.toString());
            }
//            用户登录一致性
            if (UserName != loginUser.getUsername()) {
                throw new Exception(Msg.LoginAuth.toString());
            }
//          预处理需要复制的节点
            for(String t : CopyNodes.split("\\|"))
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
//                目录复制（深度优先）
                Stack<Node> nodeStack = new Stack<Node>();
                nodeStack.push(nodeMapper.selectByPrimaryKey(inner.NodeID));
                while (!nodeStack.empty()) {//判断栈不为空
                    Node cur = nodeStack.pop();
                    Node newnode = new Node();
                    newnode.setParentnode(cur.getId());
                    newnode.setUserid(cur.getUserid());
                    newnode.setName(cur.getName());
                    nodeMapper.insert(newnode);
//                获取当前目录下的所有文件，同样执行一次复制
                    List<FileNode> curFileNode = fileNodeMapper.selectAllFilebyNodeID(cur.getId());
                    for (FileNode fileNode : curFileNode) {
                        fileNode.setPathnode(null);
                        fileNode.setNodeid(newnode.getId());
                        fileNode.setModifydate(null);
                        fileNode.setUploaddate(null);
                        fileNodeMapper.insertSelective(fileNode);
                    }
//                获取当前目录下的所有子目录，并压入堆栈
                    nodeStack.addAll(nodeMapper.selectAllChildNode(cur.getId()));
                }
            }
            else {
//                文件复制
                FileNode cur = fileNodeMapper.selectByPrimaryKey(inner.NodeID);
                cur.setPathnode(null);
                cur.setNodeid(NewNodeID);
                cur.setUploaddate(null);
                cur.setModifydate(null);
                fileNodeMapper.insertSelective(cur);
                fileMapper.updatePointByID(cur.getFileid());
            }
        }
//        返回
        Map res = new HashMap();
        res.put("NodeID", NewNodeID);
        return Msg.ParseMap(Msg.OK, "/file/copyfile", res);
    }



}
