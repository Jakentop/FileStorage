package Controllor.Child;

import Dao.UserMapper;
import Function.Msg;
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
@RequestMapping(value = "/child")
public class ChildControllor {

    @Autowired
    private UserMapper userMapper;

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


        return "";

    }



}
