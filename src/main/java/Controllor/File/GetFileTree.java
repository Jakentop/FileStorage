package Controllor.File;

import Function.Msg;
import Model.FileNode;
import Model.Node;
import Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.FilenameFilter;
import java.util.*;

@Controller
@RequestMapping("/file")
public class GetFileTree extends FileControllerFather {

    @RequestMapping("/getfiletree")
    public @ResponseBody
    String getfiletree(String UserName,
                       Integer NodeID,
                       Integer Deep,
                       HttpSession session) {

        User loginUser = (User) session.getAttribute("user");
//        验证
        try {
//            验空
            if (UserName == null || NodeID == null || Deep == null) {
                throw new Exception(Msg.ERR.toString());
            }
//            登录状态一致性
            if (UserName != loginUser.getUsername()) {
                throw new Exception(Msg.LoginAuth.toString());
            }
//            验证当前目录是否存在
            if (nodeMapper.selectByPrimaryKey(NodeID) == null) {
                throw new Exception("500");
            }
//            文件权限
            if (!AuthCheck(NodeID, loginUser)) {
                throw new Exception(Msg.NoAuth.toString());
            }

        } catch (Exception e) {
            return Msg.ParseMap(Integer.parseInt(e.getMessage()), "/file/getfiletree", null);
        }

//        处理
//          子账号换取主账号操作权限
        int userID = loginUser.getParentid() == 0 ? loginUser.getId() : loginUser.getParentid();
//          获取目录
        List<Map> allNodes = new ArrayList<Map>();
        Stack<Integer> queueNodes = new Stack<Integer>();
        for (int i = 0; i < Deep; i++) {
            List<Integer> curLevelID = new ArrayList<Integer>();
            while (!queueNodes.empty()) {
                List<Integer> curChilds = new ArrayList<Integer>();
                Node cur = nodeMapper.selectByPrimaryKey(queueNodes.pop());
                List<Node> curLevel = nodeMapper.selectAllChildNode(cur.getId());
                for (Node t : curLevel) {//添加当前节点下的子节点信息
                    curChilds.add(t.getId());
                }
//                添加到当前全部下级ID中，在下次while后压入堆栈
                curLevelID.addAll(curChilds);
//                添加到节点列表中
                allNodes.add(getres(cur.getId(), cur.getName(), 0, curChilds));
            }
            queueNodes.addAll(curLevelID);
        }
//        处理文件
        List<Map> allFiles = new ArrayList<Map>();
        for (Map t : allNodes) {
            List<FileNode> re = fileNodeMapper.selectAllFilebyNodeID((Integer) t.get("NodeID"));
            for (FileNode res : re) {
                allFiles.add(getres(res.getPathnode(), res.getName(), 1, null));
            }
        }
//        合并
        allNodes.addAll(allFiles);
//        返回
        Map res = new HashMap();
        res.put("Node", allNodes);
        res.put("Deep", Deep);
        res.put("UserName", loginUser.getUsername());
        return Msg.ParseMap(Msg.OK, "/file/getfiletree", res);

    }

}
