package Controllor.File;

import Dao.*;
import Model.FileNode;
import Model.Node;
import Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletConfigAware;

import javax.servlet.ServletConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FileControllerFather implements ServletConfigAware {

    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected FileMapper fileMapper;

    @Autowired
    protected FileNodeMapper fileNodeMapper;

    @Autowired
    protected NodeMapper nodeMapper;

    @Autowired
    protected ChildNodeMapper childNodeMapper;

    protected ServletConfig servletConfig;
    public void setServletConfig(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
    }

    // region tool 工具
    /**
     * 判断当前账号是否拥有节点权限
     * @param nodeID
     * @param loginUser
     * @return ture,有权限 false，没有权限
     *
     */
    public boolean AuthCheck(int nodeID, User loginUser)
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

    /**
     * 返回前端的map格式
     * @param NodeID
     * @param NodeName
     * @param Type
     * @param Childs
     * @return
     */
    public static Map getres(Integer NodeID, String NodeName, int Type, List Childs) {
        Map res = new HashMap();
        res.put("NodeID", NodeID);
        res.put("NodeName", NodeName);
        res.put("Type", Type);
        res.put("Childs", Childs);
        return res;
    }
// endregion
}

/**
 * 这是专门属于文件类操作的私有类
 * @author zyz
 *
 */
class innerNodes{

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private FileNodeMapper fileNodeMapper;

    public innerNodes(Integer Type, Integer NodeID) {
        this.Type = Type;
        this.NodeID = NodeID;
    }
    public Integer Type;
    public Integer NodeID;

    /**
     * 检查当前节点是否存在
     * @return
     */
    public boolean checkExisit() {
        if (this.Type == 0) {
            //查找目录存在
            return nodeMapper.selectByPrimaryKey(NodeID)!=null;
        }
        else
        {
            //查找文件存在
            return fileNodeMapper.selectByPrimaryKey(NodeID)!=null;
        }
    }

    /**
     * 获取当前节点是否有访问权限
     * @param user
     * @return
     */
    public boolean CheckAuth(User user) {
        int NodeID;
        if (this.Type == 0) {
            NodeID = this.NodeID;
        }
        else
        {
            FileNode file = fileNodeMapper.selectByPrimaryKey(this.NodeID);
            NodeID = file.getNodeid();
        }
            Node t = nodeMapper.selectNodeByNodeIDAndUserID(this.NodeID,
                    user.getParentid() == 0 ? user.getId() : user.getParentid());
            return t != null;

    }
}


