package Controllor.File;

import Function.Msg;
import Model.FileNode;
import Model.Node;
import Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.accept.PathExtensionContentNegotiationStrategy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


@Controller
@RequestMapping("/file")
public class DelFile extends FileControllerFather {

    @RequestMapping("/delfile")
    public @ResponseBody
    String delfile(String UserName,
                   String Nodes,
                   HttpSession session) {

        User loginUser = (User) session.getAttribute("user");
        List<innerNodes> NodeLists = new ArrayList<innerNodes>();
//        验证
        try {
//          验证空
            if (UserName == null || Nodes == null ) {
                throw new Exception(Msg.ERR.toString());
            }
//            用户登录一致性
            if (UserName != loginUser.getUsername()) {
                throw new Exception(Msg.LoginAuth.toString());
            }
//          预处理需要复制的节点
            for(String t : Nodes.split("|"))
            {
                if(t.length()<2) throw new Exception(Msg.ERR.toString());
                NodeLists.add(new innerNodes(Integer.parseInt(t.substring(0, 1)), Integer.parseInt(t.substring(1))));
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
//                目录删除（深度优先）
                Node current = nodeMapper.selectByPrimaryKey(inner.NodeID);
                childNodeMapper.deleteByParentIDAndChildID(current.getParentnode(), current.getId());
                Stack<Node> nodeStack = new Stack<Node>();
                nodeStack.push(nodeMapper.selectByPrimaryKey(inner.NodeID));
                while (!nodeStack.empty()) {//判断栈不为空
                    Node cur = nodeStack.pop();
//                    获取当前目录下的所有文件，执行删除
                    List<FileNode> curFileNode = fileNodeMapper.selectAllFilebyNodeID(cur.getId());
                    for (FileNode fileNode : curFileNode) {
                        fileMapper.updatesubPointByID(fileNode.getFileid());
                        fileNodeMapper.deleteByPrimaryKey(fileNode.getPathnode());
                    }
//                    获取当前目录下的所有子目录，并压入堆栈
                    nodeStack.addAll(nodeMapper.selectAllChildNode(cur.getId()));
//                    删除当前节点所有子节点引用
                    childNodeMapper.deleteAllChildByParentID(cur.getId());
                    nodeMapper.deleteByPrimaryKey(cur.getId());
                }
            }
            else {
//                文件删除
                FileNode cur = fileNodeMapper.selectByPrimaryKey(inner.NodeID);
                fileMapper.updatesubPointByID(cur.getFileid());
                fileNodeMapper.deleteByPrimaryKey(cur.getPathnode());
            }
        }
        return Msg.ParseStr(Msg.OK, "/file/delfile", "");
    }
}
