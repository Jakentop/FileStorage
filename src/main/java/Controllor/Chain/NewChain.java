package Controllor.Chain;

import Function.MD5;
import Function.Msg;
import Model.ExtLink;
import Model.ExtObj;
import Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/chain")
public class NewChain extends Father {

    @RequestMapping("/newchain")
    public @ResponseBody
    String newchain(Integer Day,
                    String Nodes,
                    boolean isCheck,
                    HttpSession session) {
        User loginUser = (User) session.getAttribute("user");
            List<ExtObj> NodeLists = new ArrayList<ExtObj>();
//        验证
        try {
//            判空
            if(Day==null||Nodes==null) throw new Exception(Msg.ERR.toString());
//          预处理需要复制的节点
            for(String t : Nodes.split("\\|"))
            {
                if(t.length()<2) throw new Exception(Msg.ERR.toString());
                ExtObj temp = new ExtObj();
                temp.setType(t.substring(0, 1));
                temp.setObjid( Integer.parseInt(t.substring(1)));
                NodeLists.add(temp);
            }
//            节点存在性和权限
            boolean flag = true;
            for (ExtObj n : NodeLists) {
                if(!checkExisit(Integer.parseInt(n.getType()),n.getObjid())||
                        !CheckAuth(loginUser,Integer.parseInt(n.getType()),n.getObjid()))
                {
                    flag=false;break;
                }
            }
            if(!flag) throw new Exception("502");

        } catch (Exception e) {
            return Msg.ParseMap(Integer.parseInt(e.getMessage()), "", null);
        }

//        处理
//          写入外链
        ExtLink extLink = new ExtLink();
        extLink.setUserid(loginUser.getId());
        extLink.setName(MD5.getUUID());
        if (Day > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, Day);
            extLink.setEndtime(calendar.getTime());
            extLink.setType(0);
        } else if (Day == 0) {
            extLink.setType(-1);
        } else {
            extLink.setType(1);
        }
        if (isCheck == true) {
            extLink.setPassword(MD5.getUUID().substring(0, 4));
        }
        extLinkMapper.insertSelective(extLink);
        for (ExtObj n : NodeLists) {
            n.setExtid(extLink.getId());
            extObjMapper.insert(n);
        }
//        返回
        Map map = new HashMap();
        map.put("UrlID", extLink.getName());
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd" );
        if(extLink.getEndtime()!=null)
        map.put("EndTime", sdf.format(extLink.getEndtime()));
        map.put("Password", extLink.getPassword());
        return Msg.ParseMap(Msg.OK, "", map);
    }

}
