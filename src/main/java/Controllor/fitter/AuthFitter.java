package Controllor.fitter;

import Function.Msg;
import org.springframework.stereotype.Component;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


public class AuthFitter implements HandlerInterceptor {
    //拦截器可以使用spring的依赖注入
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //可以在这里处理用户认证
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
//            用户没有登录
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            out.print(Msg.ParseStr(Msg.LoginAuth, "fitter", "用户没有登录"));
//            执行跳转
            response.sendRedirect("../index.jsp");
            return false;
        }

        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }



    //所有请求完成之后调用
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("所有请求完成之后调用");
    }
}
