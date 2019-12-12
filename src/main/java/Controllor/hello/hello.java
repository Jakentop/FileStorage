package Controllor.hello;

import Function.Msg;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.*;
import org.springframework.web.servlet.View;

import java.util.HashMap;
import java.util.Map;

@Controller
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

    @RequestMapping(value = "/reasonv")
    public @ResponseBody String user1(String name,String password,
                                      int age) {

        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("name", name);
        temp.put("password", password);
        temp.put("age", age);
        return Msg.ParseMap(Msg.OK, "test", temp);




    }


}
