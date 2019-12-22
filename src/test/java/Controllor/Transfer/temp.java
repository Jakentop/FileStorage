package Controllor.Transfer;

import Dao.FileMapper;
import Dao.FileNodeMapper;
import Dao.NodeMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class temp extends Dao.Father {


    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private FileNodeMapper fileNodeMapper;

    @Autowired
    private FileMapper fileMapper;

    @Test
    public void m() {
        List<downFile> res = new ArrayList<downFile>();
        innerNodes.DFS(res, 24, "", nodeMapper, fileNodeMapper, fileMapper);
        System.out.print("123");
    }




}
