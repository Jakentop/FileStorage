package Controllor.File;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class filetest {

    @Test
    public void m() throws IOException {
        File f = new File("D:\\剪辑临时存放\\" + "fjdskls");
        f.createNewFile();
    }

}
