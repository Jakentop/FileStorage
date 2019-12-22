package Controllor.Transfer;

import Function.Msg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.event.DocumentEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
@RequestMapping("/transfer")
public class Download extends TransferFather {


    @RequestMapping("/download")
    public @ResponseBody
    String Download(String Url, HttpSession session,
                    HttpServletRequest request, HttpServletResponse response) throws IOException {
//        获取是否存在对象
        List<downFile> down = (ArrayList<downFile>) session.getAttribute(Url);
        if (down == null) {
            return Msg.ParseStr(Msg.ERR, "", "请求错误");
        }
        else{
//            处理返回问题
            String path=servletConfig.getServletContext().getInitParameter("path");
            List<filestreaminfo> res = new ArrayList<filestreaminfo>();
            response.setContentType("text/html");
            response.setHeader("Content-Disposition", "attachment;fileName=" + "hello"+".zip");

            ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());

            for (downFile t : down) {
                zos.putNextEntry(new ZipEntry(t.getPath() + t.getFilename()));
                FileInputStream fis = new FileInputStream(new File(path+t.getUuid()));
                int len = 0;
                byte[] b = new byte[1024];
                while((len = fis.read(b)) > 0) {
                    zos.write(b, 0, len);
                }
                fis.close();
                response.flushBuffer();
            }
        }

        return "完成";
    }

}

class filestreaminfo{
    private OutputStream stream;
    private String name;
    private String path;

    public OutputStream getStream() {
        return stream;
    }

    public void setStream(OutputStream stream) {
        this.stream = stream;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
