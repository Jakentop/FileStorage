package Function;

import javafx.scene.shape.Path;

import java.util.List;

/**
 * 文件名称或者路径解析类，主要完成文件名称与后缀的分离，文件路径的解析功能
 *
 * @author zyz
 */
public abstract class FileParse {

    /**
     * 根据文件完整名称返回文件名和后缀两个部分
     * @param FileName
     * @return String数组，第一个元素为文件名称，第二个元素为文件后缀，中间没有.好分开
     */
    public static String[] FileName(String FileName) {
        String a[] = FileName.split("\\.");
        if (a.length > 2) {
            String b[]=new String[2];
            String c=a[0];
            for (int i = 1; i < a.length - 1; i++) {
                c+="."+a[i];
            }
            b[0]=c;
            b[1] = a[a.length - 1];
            return b;
        }
        else
        {
            String c[] = new String[2];
            c[0] = a.length > 0 ? a[0] : "";
            c[1] = a.length > 1 ? a[1] : "";
            return c;
        }
    }

    public static String[] PathName(String PathName) {
        PathName = PathName.replace("\\", "/");
        String a[] = PathName.split("/");
        return a;
    }

}
