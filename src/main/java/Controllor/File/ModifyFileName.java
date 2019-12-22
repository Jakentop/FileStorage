package Controllor.File;

import Function.Msg;
import Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/file")
public class ModifyFileName extends FileControllerFather {

    /**
     * 修改文件名称
     * @param FileNode
     * @param FilePreName
     * @param FileNewName
     * @param session
     * @return
     */
    @RequestMapping("modifyfilename")
    public @ResponseBody
    String modifyfilename(Integer FileNode,
                          String FilePreName,
                          String FileNewName,
                          HttpSession session) {
        User loginUser = (User) session.getAttribute("user");
        Model.FileNode filechange = fileNodeMapper.selectByPrimaryKey(FileNode);
//        验证
        try {
//            验空
            if (FileNode == null || FileNewName == null || FilePreName == null) {
                throw new Exception(Msg.ERR.toString());
            }
//            文件是否存在
            if (filechange == null) {
                throw new Exception("502");
            }
//            原始文件一致性
            if (!filechange.getName().equals(FilePreName)) {
                throw new Exception("501");
            }
//            验证文件是否有权限
            if (!AuthCheck(filechange.getNodeid(), loginUser)) {
                throw new Exception(Msg.NoAuth.toString());
            }
//            查找重名
            if (fileNodeMapper.selectByNodeIDandFileName(filechange.getNodeid(), FileNewName)!=null) {
                throw new Exception("503");
            }
        } catch (Exception e) {
            return Msg.ParseStr(Integer.parseInt(e.getMessage()), "/file/modifyfilename", "");
        }

//        处理
        filechange.setName(FileNewName);
        fileNodeMapper.updateByPrimaryKeySelective(filechange);
//        返回
        return Msg.ParseStr(Msg.OK, "/file/modifyfilename", "");
    }

}
