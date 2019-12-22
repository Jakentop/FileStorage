package Controllor.Chain;

import Function.Msg;
import Model.ExtLink;
import Model.ExtObj;
import Model.FileNode;
import Model.Node;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

@Controller
@RequestMapping("/chain")
public class Get extends Father {

    @RequestMapping("/get")
    public @ResponseBody
    String get(String UUID,
               String Password) {
        ExtLink cur = extLinkMapper.selectByName(UUID);
//        验证
        try {
            if (cur == null) {
                throw new Exception(Msg.ERR.toString());
            }
            if (!cur.getPassword() .equals(Password) ) {
                throw new Exception(Msg.NoAuth.toString());
            }
        } catch (Exception e) {
            return Msg.ParseList(Integer.parseInt(e.getMessage()), "", null);
        }
//        处理
        List<ExtObj> objs = extObjMapper.selectAllExtobjByExLinkName(UUID);
//          读取文件或者目录信息
        List<Map> res = new ArrayList<Map>();
        for (ExtObj obj : objs) {
            if (obj.getType() .equals("1")) {
//                处理文件
                FileNode temp = fileNodeMapper.selectByPrimaryKey(obj.getObjid());
                res.add(CreateRes(-1, temp.getPathnode()
                        , temp.getName(), 1, temp.getUploaddate(), temp.getModifydate(), null));
            }
            else
            {
//                处理文件夹
                Stack<Node> curNode = new Stack<Node>();
                curNode.push(nodeMapper.selectByPrimaryKey(obj.getObjid()));
                while (!curNode.empty()) {
                    Node c = curNode.pop();
                    List<Node> childNodes = nodeMapper.selectAllChildNode(c.getId());
                    List<Integer> childIDs = new ArrayList<Integer>();
                    for (Node child : childNodes) {
                        childIDs.add(child.getId());
                    }
//                    添加当前节点
                    res.add(CreateRes(-1, c.getId(), c.getName(), 0,
                            null, null, childIDs));
//                    将当前子节点压入堆栈
                    curNode.addAll(childNodes);
//                    添加文件节点
                    List<FileNode> curFIles = fileNodeMapper.selectAllFilebyNodeID(c.getId());
                    for (FileNode f : curFIles) {
                        res.add(CreateRes(c.getId(), f.getPathnode(), f.getName() + f.getSuffix(),1,
                                f.getUploaddate(), f.getModifydate(), null));
                    }
                }
            }
        }
        return Msg.ParseList(Msg.OK, "", res);
    }

}
