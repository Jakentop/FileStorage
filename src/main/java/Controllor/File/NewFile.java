package Controllor.File;

import Function.FileParse;
import Function.MD5;
import Function.Msg;
import Model.File;
import Model.FileNode;
import Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.UUID;

@Controller
@RequestMapping("/file")
public class NewFile extends FileControllerFather {

    /**
     * 新建一个文件
     * @param FileName
     * @param NodeID
     * @param UserName
     * @param session
     * @return
     */
    @RequestMapping(value = "/newfile",method = RequestMethod.POST)
    public @ResponseBody
    String NewFile (String FileName,
                   Integer NodeID,
                   String UserName,
                   HttpSession session) {

        String path=servletConfig.getServletContext().getInitParameter("path");
        User loninUser = (User) session.getAttribute("user");
//        验证

        try {
//            验证空
            if (FileName == null || NodeID == null || UserName == null)
                throw new Exception(Msg.ERR.toString());
//            验证目录是否存在
            if(nodeMapper.selectByPrimaryKey(NodeID)==null)
                throw new Exception("501");
//            用户是否有权限
            if  (!AuthCheck(NodeID,loninUser))
                throw new Exception(Msg.NoAuth.toString());
//            当前目录下是否有重名
            if (fileNodeMapper.selectByNodeIDandFileName(NodeID,FileName)!=null)
                throw new Exception("503");
//            登录状态是否一致
            if(!loninUser.getUsername().equals(UserName))
                throw new Exception(Msg.LoginAuth.toString());
        } catch (Exception e) {
            return Msg.ParseMap(Integer.parseInt(e.getMessage()), "/file/newfile", null);
        }
//        处理

        FileNode fileNode = new FileNode();
        fileNode.setName(FileParse.FileName(FileName)[0]);
        fileNode.setSuffix(FileParse.FileName(FileName)[1]);
        fileNode.setNodeid(NodeID);
//              创建空文件
        InputStream stream = new ByteArrayInputStream(new byte[0]);
        try {
            File file = fileMapper.selectByMd5(MD5.getFile(stream));
//                文件存在，执行指针加1操作,直接提交sql语句防止出现并发问题
            if (file != null)
            {
                fileMapper.updatePointByID(file.getId());
                fileNode.setFileid(file.getId());
            }
            else
            {
                String uuid = UUID.randomUUID().toString().replace("-","");
                file = new File();
                file.setPoint(1);
                file.setUuid(uuid);
                file.setSize(0);
                file.setMd5(MD5.getFile(stream));
//                创建物理存储文件
                java.io.File f = new java.io.File(path+uuid);
                f.createNewFile();
//                File写入数据库
                fileMapper.insert(file);
                fileNode.setFileid(file.getId());
            }
        } catch (IOException e) {
            return Msg.ParseMap(Msg.UnKnow, "/file/newfile", null);
        }
//            FileNode写入
        fileNodeMapper.insertSelective(fileNode);
        fileNode = fileNodeMapper.selectByPrimaryKey(fileNode.getPathnode());
//        返回
        HashMap data = new HashMap();
        data.put("FileID", fileNode.getPathnode());
        data.put("NodeID", NodeID);
        data.put("FileName", fileNode.getName());
        data.put("Suffix", fileNode.getSuffix());
        data.put("UploadData", fileNode.getUploaddate());
        return Msg.ParseMap(Msg.OK, "/dir/newfile", data);
    }
}
