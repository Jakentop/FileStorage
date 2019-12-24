package Controllor.Transfer;

import Dao.FileMapper;
import Dao.FileNodeMapper;
import Dao.NodeMapper;
import Dao.UserMapper;
import Model.File;
import Model.FileNode;
import Model.Node;
import Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletConfigAware;

import javax.servlet.ServletConfig;
import java.io.OutputStream;
import java.util.List;

public class TransferFather implements ServletConfigAware {

    @Autowired
    protected NodeMapper nodeMapper;

    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected FileMapper fileMapper;

    @Autowired
    protected FileNodeMapper fileNodeMapper;

    protected ServletConfig servletConfig;
    public void setServletConfig(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
    }

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

    @Autowired
    private FileMapper fileMapper;

    public innerNodes(Integer Type, Integer NodeID,NodeMapper nodeMapper,FileNodeMapper fileNodeMapper,FileMapper fileMapper) {
        this.Type = Type;
        this.NodeID = NodeID;
        this.nodeMapper = nodeMapper;
        this.fileMapper = fileMapper;
        this.fileNodeMapper = fileNodeMapper;
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
        Node t = nodeMapper.selectNodeByNodeIDAndUserID(NodeID,
                user.getParentid() == 0 ? user.getId() : user.getParentid());
        return t != null;

    }

    public static boolean DFS(List<downFile> res, Integer nodeID, String path,
                              NodeMapper nodeMapper, FileNodeMapper fileNodeMapper, FileMapper fileMapper) {
        if(nodeID==null) return true;
        List<Node> cur = nodeMapper.selectAllChildNode(nodeID);
        if(cur==null) return true;
        else
        {
            for (Node t : cur) {
                List<FileNode> curf = fileNodeMapper.selectAllFilebyNodeID(nodeID);
                for (FileNode curt : curf) {
                    File f = fileMapper.selectByPrimaryKey(curt.getFileid());
//                    添加当前目录下的文件
                    res.add(new downFile(curt.getName() + curt.getSuffix(),
                            path + "/"+ t.getName(),
                            f.getUuid()));
                }
                if(!t.getId().equals(nodeID))
                innerNodes.DFS(res, t.getId(), path+"/"+t.getName(),
                        nodeMapper,fileNodeMapper,fileMapper);
            }
        }
        return true;
    }

}

class downFile{
    private String filename;
    private String path;
    private String uuid;
    private OutputStream stream;
    public downFile(String filename, String path,String uuid) {
        this.filename = filename;
        this.path = path;
        this.uuid=uuid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public OutputStream getStream() {
        return stream;
    }

    public void setStream(OutputStream stream) {
        this.stream = stream;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}