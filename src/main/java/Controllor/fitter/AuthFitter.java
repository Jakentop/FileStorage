package Controllor.fitter;

import Function.Msg;
import org.springframework.stereotype.Component;

import org.springframework.web.portlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


public class AuthFitter extends HandlerInterceptorAdapter {
    //拦截器可以使用spring的依赖注入
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //可以在这里处理用户认
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
//            用户没有登录
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            out.print(Msg.ParseStr(Msg.LoginAuth, "fitter", "用户没有登录"));
//            执行跳转
            return false;
        }

        return true;
    }
}
