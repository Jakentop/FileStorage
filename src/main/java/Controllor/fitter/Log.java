package Controllor.fitter;

import Dao.LogMapper;
import Model.User;
import com.alibaba.fastjson.JSON;
import org.springframework.web.portlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;

public class Log extends HandlerInterceptorAdapter {

//    请求信息
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler, LogMapper logMapper)throws Exception {
        HashMap req = new HashMap();
        HashMap params = new HashMap();
        Enumeration t = request.getParameterNames();
        while(t.hasMoreElements())
        {
            String temp = (String) t.nextElement();
            params.put(temp, request.getParameter(temp));
        }
        req.put("title", request.getRequestURL());
        req.put("params", params);
        //        存入session中
        HttpSession session = request.getSession();
//        存入数据库
        Model.Log log = new Model.Log();
        log.setUserid(((User) session.getAttribute("user")).getId());
        log.setRequest(JSON.toJSONString(req));
        logMapper.insertSelective(log);
//        获取列表
        session.setAttribute("req", req);
        return true;
    }

//    返回信息
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {



    }

}
