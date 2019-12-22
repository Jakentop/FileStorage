package Controllor.Chain;


import Dao.ExtLinkMapper;
import Dao.ExtObjMapper;
import Dao.FileNodeMapper;
import Dao.NodeMapper;
import Model.FileNode;
import Model.Node;
import Model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Father {

    @Autowired
    protected NodeMapper nodeMapper;

    @Autowired
    protected ExtLinkMapper extLinkMapper;

    @Autowired
    protected ExtObjMapper extObjMapper;

    @Autowired
    protected FileNodeMapper fileNodeMapper;

    /**
     * 检查当前节点是否存在
     * @return
     */
    public boolean checkExisit(int Type,Integer NodeID) {
        if (Type == 0) {
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
     * 鉴权存在bug没有判定子账号是否有权限访问目录
     * @param user
     * @return
     */
    public boolean CheckAuth(User user,int Type,Integer NodeID) {
        int NodeIDs;
        if (Type == 0) {
            NodeIDs = NodeID;
        }
        else
        {
            FileNode file = fileNodeMapper.selectByPrimaryKey(NodeID);
            NodeIDs = file.getNodeid();
        }
        Node t = nodeMapper.selectNodeByNodeIDAndUserID(NodeIDs,
                user.getParentid() == 0 ? user.getId() : user.getParentid());
        return t != null;

    }

    public Map CreateRes(Integer NodeParentID, Integer NodeID, String NodeName,
                         int Type, Date UploadDate, Date ModifyDate, List ChildsID) {
        Map res = new HashMap();
        res.put("NodeParentID", NodeParentID);
        res.put("NodeID", NodeID);
        res.put("NodeName", NodeName);
        res.put("Type", Type);
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd" );
        res.put("UploadDate", UploadDate==null?null:sdf.format(UploadDate));
        res.put("ModifyDate", ModifyDate==null?null:sdf.format(ModifyDate));
        res.put("ChildsID", ChildsID);

        return res;
    }

}
