package Controllor.User;

import Dao.UserMapper;
import Function.MD5;
import Function.Msg;
import Function.Vaild;
import Model.ExtObj;
import Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping(value = "/user")
public class UserControllor {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册
     * @param session
     * @param name
     * @param password
     * @param e_mail
     * @param type
     * @param logicnode
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public @ResponseBody
    String register(HttpSession session,
                                         @RequestParam(defaultValue = "") String name,
                                         @RequestParam(defaultValue = "") String password,
                                         @RequestParam(defaultValue = "") String e_mail,
                                         @RequestParam(defaultValue = "-1") int type,
                                         @RequestParam(defaultValue = "-1") int logicnode) {
//        验证空
        if (name == "" || password == "" || e_mail == "" || type != 0|| type !=1) {
            return Msg.ParseStr(Msg.ERR, "/user/register", "");
        }

//        验证
        try {
            if(password.length()<8) throw new Exception("密码长度错误");
            if(!Vaild.E_mail(e_mail)) throw new Exception("邮箱格式错误");
            if (userMapper.selectByUserName(name).size() > 0 || userMapper.selectByEMail(name).size() > 0) {
                throw new Exception("用户名或邮箱重复");
            }

        } catch (Exception e) {
            return Msg.ParseStr(Msg.ERR, "/user/register", e.getMessage());
        }
//        处理数据
        User user = new User();
        user.setPassword(MD5.getsec(password));
        user.setValid(0);
        user.setUsername(name);
        user.seteMail(e_mail);
        if (type == 0) {
            user.setLogicnode(0);
            user.setParentid(0);
        }
        else
        {
            User logUser = (User) session.getAttribute("user");
            user.setParentid(logUser.getId());
            user.setLogicnode(logicnode);
        }

        userMapper.insert(user);
//      成功跳转登录

        return Msg.ParseStr(Msg.OK, "/user/register", "注册成功");
    }

    /**
     * 用于用户登录
     * @param session
     * @param name
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody
    String login(HttpSession session,
                 @RequestParam(defaultValue = "") String name,
                 @RequestParam(defaultValue = "") String password) {
//        验证
        if(name==""||password==""){
            return Msg.ParseStr(Msg.ERR, "/user/login", "");
        }

//        处理数据
        List<User> getUsers = userMapper.selectByUserName(name);
        if(getUsers.size()>0) return Msg.ParseStr(501, "/user/login", "");
        if (getUsers.get(0).getPassword() == MD5.getsec(password)) {
//            登录成功
            session.setAttribute("user", getUsers.get(0));
//            跳转到首页
        }
        else{
            return Msg.ParseStr(501, "/user/login", "");
        }
        return Msg.ParseStr(Msg.OK, "/user/login", "");
    }

    /**
     * 验证重复邮箱或者重复信息
     * @param value
     * @param type
     * @param request
     * @return
     */
    @RequestMapping(value = "/checkrepeat", method = RequestMethod.POST)
    public @ResponseBody
    String checkrepeat( @RequestParam(defaultValue = "") String value,
                        @RequestParam(defaultValue="-1") int type, HttpServletRequest request) {
//        验证
        if(value==""||(type!=0&&type!=1)){
            return Msg.ParseStr(Msg.ERR, "/user/checkrepeat", "");
        }

//        处理
        boolean flag=true;
        if (type == 0) {
//            用户名判断
            List<User> get = userMapper.selectByUserName(value);
            if (get.size()>0) flag = false;
        } else if (type == 1) {
//            邮箱判断
            List<User> get = userMapper.selectByEMail(value);
            if(get.size()>0) flag = false;
        }
        else
        {
            return Msg.ParseStr(Msg.ERR, "/user/checkrepeat", "");
        }
//        返回结果
        if (flag) {

            return Msg.ParseStr(Msg.OK, "/user/checkrepeat", "");
        }
        else
        {
            return Msg.ParseStr(500, "/user/checkrepeat", "");
        }

    }

}
