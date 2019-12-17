package Controllor.Dir;

import Dao.ChildNodeMapper;
import Dao.NodeMapper;
import Function.Msg;
import Model.Node;
import Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/dir")
public class DirControllor {

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private ChildNodeMapper childNodeMapper;

    @RequestMapping(value = "/dir", method = RequestMethod.POST)
    public @ResponseBody
    String dir(@RequestParam(defaultValue = "1") int Deep,
               int Node,
               HttpSession session) {

//        验证
        try {
//            验证空
            if((Integer)Node==null) throw new Exception(((Integer) Msg.ERR).toString());
//            验证当前节点是否存在
            if(nodeMapper.selectByPrimaryKey(Node)==null) throw new Exception(((Integer) Msg.ERR).toString());
//            验证当前节点是否有权限
            if (AuthCheck(Node, (User) session.getAttribute("user"))) {
                throw new Exception(((Integer) Msg.NoAuth).toString());
            }

        } catch (Exception e) {
            return Msg.ParseMap(Integer.parseInt(e.getMessage()), "/dir/dir", null);
        }

//        处理
        List<Node> res = new ArrayList<Model.Node>();


        return "";
    }

    /**
     * 判断当前账号是否拥有节点权限
     * @param nodeID
     * @param loginUser
     * @return
     * @throws Exception
     */
    public boolean AuthCheck(int nodeID,User loginUser) throws Exception
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
            while (t.getId()!=loginUser.getLogicnode()&&t.getParentnode()!=0);
//            考虑如果子账号获取主账号所有权限时
            if(t.getParentnode()==0 && t.getId()!=loginUser.getLogicnode() )
                return false;
            else
                return true;




        }
        //账号为主账号
        else
        {
            return nodeMapper.selectNodeByNodeIDAndUserID(nodeID,loginUser.getId())==null;
        }

    }


}
