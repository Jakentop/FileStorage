package Function;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class MD5Test {

    @Before
    public void setUp() throws Exception {
        System.out.print("开始测试");

    }

    @Test
    public void getFile() throws IOException {
        System.out.println(MD5.getFile("C:\\Users\\zyz\\Desktop\\Developer_CHS.7z"));
    }

    @Test
    public void getstring() {
        System.out.println(MD5.getstring("1234"));

    }

    @Test
    public void getsec() {
        System.out.print(MD5.getsec("1234"));
    }

    @Test
    public void getres() {
        System.out.println(MD5.getres(MD5.getsec("1234")));

    }

}