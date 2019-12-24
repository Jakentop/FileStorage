package Controllor.Transfer;

import Function.MD5;
import Function.Msg;
import Model.File;
import Model.FileNode;
import Model.Node;
import Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/transfer")
public class DownloadLink extends TransferFather {

    /**
     * 根据选中的内容保存文件下载信息
     * @param NodeList
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "/downloadlink", method = RequestMethod.POST)
    public @ResponseBody
    String downloadlink(String NodeList,
                        HttpSession session,
                        HttpServletRequest request) {
        User loginUser = (User) session.getAttribute("user");
        List<innerNodes> NodeLists = new ArrayList<innerNodes>();
//        验证
        try {
//            用户登录验证
            if (loginUser == null) throw new Exception(Msg.LoginAuth.toString());
//          预处理需要复制的节点
            for(String t : NodeList.split("\\|"))
            {
                if(t.length()<2) throw new Exception(Msg.ERR.toString());
                NodeLists.add(new innerNodes(Integer.parseInt(t.substring(0, 1)), Integer.parseInt(t.substring(1)),
                        nodeMapper,fileNodeMapper,fileMapper));
            }
//            节点存在性和权限
            boolean flag = true;
            for (innerNodes n : NodeLists) {
                if(n.checkExisit()==false||n.CheckAuth(loginUser)==false)
                {
                    flag=false;break;
                }
            }
            if(flag==false) throw new Exception("502");


        } catch (Exception e) {
            return Msg.ParseMap(Integer.parseInt(e.getMessage()), "/transfer/downloadlink", null);
        }
//        处理
        List<downFile> downfiles = new ArrayList<downFile>();
        for (innerNodes innernode : NodeLists) {
            if (innernode.Type == 1) {
//                文件
                FileNode fn = fileNodeMapper.selectByPrimaryKey(innernode.NodeID);
                File f = fileMapper.selectByPrimaryKey(fn.getFileid());
                if(fn.getSuffix().equals(""))
                    downfiles.add(new downFile(fn.getName(), "", f.getUuid()));
                else
                    downfiles.add(new downFile(fn.getName() +"."+ fn.getSuffix(), "", f.getUuid()));
            }
            else
            {
//                目录
                Node t = nodeMapper.selectByPrimaryKey(innernode.NodeID);
                List<FileNode> curf = fileNodeMapper.selectAllFilebyNodeID(t.getId());
                for (FileNode curt : curf) {
                    File f = fileMapper.selectByPrimaryKey(curt.getFileid());
//                    添加当前目录下的文件
                    downfiles.add(new downFile(curt.getName() + curt.getSuffix(),
                            ""+ t.getName()+"/",
                            f.getUuid()));
                }
                innerNodes.DFS(downfiles, innernode.NodeID, "" + t.getName(),
                        nodeMapper, fileNodeMapper, fileMapper);
            }
        }
//        返回
//          保存session，生成外部链接
        String uuid = MD5.getUUID();
        session.setAttribute(uuid, downfiles);

        Map res = new HashMap();
        res.put("url", uuid);
        return Msg.ParseMap(Msg.OK, "/transfer/downloadlink", res);
    }

}
