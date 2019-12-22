package Function;

import org.springframework.util.DigestUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.UUID;

public abstract class MD5 {

    /**
     * 生成不可逆的md5打码
     * @param Str
     * @return string md5密码
     */
    public static String getstring(String Str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = Str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16){
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /**
     * md5加密算法(可逆算法)
     * @param inStr
     * @return
     */
    public static String getsec(String inStr) {

        return getstring(inStr);
//        char[] a = inStr.toCharArray();
//        for (int i = 0; i < a.length; i++) {
//            a[i] = (char) (a[i] ^ 't');
//        }
//        String s = new String(a);
//        return s;

    }

    public static String getres(String string) {
        return getsec(string);
    }

    /**
     * 获取某个路径下文件的md5码
     * @param path
     * @return 16进制的md5码
     * @throws IOException
     */
    public static String getFile(String path) throws IOException {
        return DigestUtils.md5DigestAsHex(new FileInputStream(path));
    }


    /**
     * 获取某个FileStream流中的md5码
     * @param stream
     * @return
     * @throws IOException
     */
    public static String getFile(InputStream stream) throws IOException {
        return DigestUtils.md5DigestAsHex(stream);
    }

    /**
     * 获取UUID
     * @return 返回32位数据库中标准格式
     */
    public static String  getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


}
