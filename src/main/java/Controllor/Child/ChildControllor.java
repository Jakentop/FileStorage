package Controllor.Child;

import Dao.NodeMapper;
import Dao.UserMapper;
import Function.MD5;
import Function.Msg;
import Function.Vaild;
import Model.User;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.objenesis.instantiator.annotations.Instantiator;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/child")
public class ChildControllor {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NodeMapper nodeMapper;

    /**
     * 获取某个用户下所有子账号信息
     * @param session
     * @param UserName 用户名称用于检测登录的一致性
     * @return
     */
    @Transactional(rollbackFor = Throwable.class)//配置spring事务，出现错误则自动滚回
    @RequestMapping(value = "/getchilds", method = RequestMethod.POST)
    public @ResponseBody String getchilds(HttpSession session,
                                          @RequestParam(defaultValue = "") String UserName)
    {
//        验证
        if(UserName=="")return Msg.ParseList(Msg.ERR,"/child/getchilds",new ArrayList());

        User loginUser = (User) session.getAttribute("user");
        if (loginUser==null||loginUser.getUsername()!=UserName)
            return Msg.ParseList(Msg.LoginAuth, "/child/getchilds", new ArrayList());

        if (loginUser.getId()!=0)
            return Msg.ParseList(557,"/child/getchilds",new ArrayList());

//        处理数据
        List<User> getlist = userMapper.selectChildByParentID(loginUser.getId());

        ArrayList<Map> res = new ArrayList<Map>();
        for (User user : getlist) {
            Map temp = new HashMap();
            temp.put("name", user.getUsername());
            temp.put("logicnode", user.getLogicnode());
            temp.put("e_mail", user.geteMail());
            res.add(temp);
        }
//        返回结果
        return Msg.ParseList(Msg.OK, "/child/getchilds", res);


    }

    /**
     * 修改子账号信息
     * @param ChildName
     * @param LogicNode
     * @param Password
     * @param E_mail
     * @param CheckPassword
     * @param session
     * @return
     */
    @RequestMapping(value = "/modifychildinfo",method = RequestMethod.POST)
    public @ResponseBody
    String modifychildinfo( String ChildName,
                            Integer LogicNode,
                            String Password,
                            String E_mail,
                            String CheckPassword,
                           HttpSession session
    ) {
//        验证
        if (ChildName == null || (LogicNode== null && Password == null && E_mail == null )|| CheckPassword == null) {
            return Msg.ParseList(Msg.ERR, "/child/modifychildinfo", new ArrayList());
        }
        User loginUser = (User) session.getAttribute("user");
        try {
            if (Password.length()<8) throw new Exception("503");
            if(!Vaild.E_mail(E_mail)) throw new Exception("502");
            if (MD5.getsec(Password) != loginUser.getPassword()) throw new Exception("500");
            if(loginUser.getParentid()!=0) throw new Exception(((Integer) Msg.NoAuth).toString());
            if(LogicNode!=null&&nodeMapper.selectByPrimaryKey(LogicNode)==null) throw new Exception("501");
        } catch (Exception e) {
            return Msg.ParseList(Integer.parseInt(e.getMessage()), "/child/modifychildinfo", null);
        }

//        处理
        List<User> getlist = userMapper.selectByUserName(ChildName);
        if(getlist.size()==0)
        {
//            子账号不存在
            return Msg.ParseList(504,"/child/modifychildinfo",null);
        }
        ArrayList<Map> res = new ArrayList<Map>();
        User user = getlist.get(0);
        if(E_mail!=null) {
            user.seteMail(E_mail);
            Map temp = new HashMap();
            temp.put("key", "E_mail");
            temp.put("value", E_mail);
            res.add(temp);
        }
        if(Password!=null)
        {
            user.setPassword(MD5.getsec(Password));
            user.seteMail(Password);
            Map temp = new HashMap();
            temp.put("key", "Password");
            temp.put("value", Password);
            res.add(temp);
        }

        if(LogicNode!=null) {
            user.setLogicnode(LogicNode);
            Map temp = new HashMap();
            temp.put("key", "LogicNode");
            temp.put("value", LogicNode);
            res.add(temp);
        }

//        更新用户信息
        if (userMapper.updateByPrimaryKey(user) == 1) {
            return Msg.ParseList(Msg.OK, "/child/modifychildinfo", res);
        }
        else
        {
            return Msg.ParseList(Msg.ERR, "", null);
        }
    }

    @RequestMapping(value = "/delchild", method = RequestMethod.POST)
    public @ResponseBody String delchild(String ChildName,
                            String UserName,
                            String Password,
                            HttpSession session) {
//        验证
        try {
            if (UserName == null || Password == null || ChildName == null)
                throw new Exception(((Integer) Msg.ERR).toString());
            User loginUser = (User)session.getAttribute("user");
            if (loginUser.getUsername()!=UserName)
                throw new Exception(((Integer) Msg.LoginAuth).toString());
            if(loginUser.getParentid()!=0)
                throw new Exception(((Integer) Msg.NoAuth).toString());
            if(loginUser.getPassword()!=MD5.getsec(Password))
                throw new Exception("503");

        } catch (Exception e) {
            return Msg.ParseStr(Integer.parseInt(e.getMessage()), "/child/delchild", "");
        }

//        处理
        if (userMapper.deleteByUserName(UserName) == 1) {
            return Msg.ParseStr(Msg.OK, "/child/delchild", "");
        }
        else
            return Msg.ParseStr(Msg.ERR, "/child/delchild", "");

    }


    /**
     * 查看当前子账号集合中是否包含
     * @param list
     * @param ChildID
     * @return boolean true标识包含在内，false标识不包含在内
     */
    public boolean isChildInclude(List<User> list, int ChildID) {
        for (User user : list) {
            if(user.getId()==ChildID) return true;

        }
        return false;
    }

}
