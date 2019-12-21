package Function;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileParseTest {

    private String temp;
    @Before
    public void setUp() throws Exception {
        temp = "";
    }

    @Test
    public void fileName() {
        System.out.println(JSON.toJSONString(FileParse.FileName(temp)));
    }

    @Test
    public void pathName() {
        System.out.println(JSON.toJSONString(FileParse.PathName(temp)));
    }
}