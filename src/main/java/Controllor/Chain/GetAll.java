package Controllor.Chain;

import Function.Msg;
import Model.ExtLink;
import Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/chain")
@Controller
public class GetAll extends Father {

    /**
     * 获取当前用户下的所有外链信息
     * @param session
     * @return
     */
    @RequestMapping("/getall")
    public @ResponseBody
    String getall(HttpSession session) {
        User loginUser = (User) session.getAttribute("user");
//        验证
        if (loginUser == null) {
            return Msg.ParseList(Msg.ERR, "", null);
        }

        List<ExtLink> res = extLinkMapper.selectByUserID(loginUser.getId());
        return Msg.ParseList(Msg.OK, "", res);
    }
}
