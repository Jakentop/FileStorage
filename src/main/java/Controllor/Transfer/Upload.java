package Controllor.Transfer;

import Function.FileParse;
import Function.MD5;
import Function.Msg;
import Model.FileNode;
import Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/transfer")
public class Upload extends TransferFather{

    /**
     * 文件上传方法，注意该方法一次只可以上传一个文件，同时只支持文件的上传不支持目录的上传！
     * @param file
     * @param NodeID
     * @param req
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/upload" ,method = RequestMethod.POST)
    public @ResponseBody
    String upload(@RequestParam("file") MultipartFile file,
                  Integer NodeID,
                  HttpServletRequest req,
                  HttpSession session) throws IOException {

        User loginUser = (User) session.getAttribute("user");
//        欺骗
        if(NodeID==0) NodeID = loginUser.getLogicnode();
//        验证
        try {
            if (file.isEmpty() || NodeID == null) {
                throw new Exception(Msg.ERR.toString());
            }
//            验证目录存在
            if(nodeMapper.selectByPrimaryKey(NodeID)==null) throw new Exception("500");
//            验证目录权限
            if(!AuthCheck(NodeID,loginUser)) throw new Exception(Msg.NoAuth.toString());
        } catch (Exception e) {
            return Msg.ParseList(Integer.parseInt(e.getMessage()), "", null);
        }
//        处理

//        获取原文件名
        String fileName[] = FileParse.FileName(file.getOriginalFilename());
//        根据MD5码判断当前是否已经存在
        String md5 = MD5.getFile(file.getInputStream());
        Model.File file1 = fileMapper.selectByMd5(md5);
        if (file1==null) {
            file1 = new Model.File();
            file1.setUuid(MD5.getUUID());
            file1.setPoint(1);
            file1.setMd5(md5);
            fileMapper.insertSelective(file1);
        }
//        封装文件的逻辑目录
        FileNode fileNode = new FileNode();
        fileNode.setNodeid(NodeID);
        fileNode.setName(fileName[0]);
        fileNode.setFileid(file1.getId());
        fileNode.setSuffix(fileName[1]);
//        提交数据库
        fileNodeMapper.insertSelective(fileNode);

        // 获取文件存储路径（绝对路径）
        String path = servletConfig.getServletContext().getInitParameter("path");
        // 创建文件实例
        File filePath = new File(path, file1.getUuid());
        // 如果文件目录不存在，创建目录
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录" + filePath);
        }
        // 写入文件
        file.transferTo(filePath);
//        返回
        Map res = new HashMap();
        res.put("NodeID", fileNode.getPathnode());
        res.put("NodeName", fileNode.getName() + fileNode.getSuffix());
        res.put("Type", 1);
        res.put("Childs", null);
        return Msg.ParseMap(Msg.OK,"/transfer/upload",res);
    }

}
