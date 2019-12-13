package Controllor.hello;

import Dao.UserMapper;
import Function.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
@Service
@RequestMapping("/hello")
public class hello {

    @RequestMapping("/RetJsp")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/ret")
    public ModelAndView ret() {
        ModelAndView modelAndView = new ModelAndView("hello");
        modelAndView.addObject("message", "hello");
        return modelAndView;
    }

    @RequestMapping(value = "/reason")
    public @ResponseBody
    String user(HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        return "这是一个测试";
    }

    @Autowired
    UserMapper userMapper;
    @RequestMapping(value = "/reasonv")
    public @ResponseBody String user1() {

        return Msg.ParseMap(Msg.OK, "test", userMapper.selectByPrimaryKey(6));




    }


}
