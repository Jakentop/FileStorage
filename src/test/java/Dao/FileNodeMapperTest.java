package Dao;

import Model.File;
import Model.FileNode;
import Model.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * 测试FileNode节点
 *
 * @author zyz
 */
public class FileNodeMapperTest extends Father {

    @Autowired
    private FileNodeMapper fileNodeMapper;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private NodeMapper nodeMapper;


    private int FileID;

    private int NodeID;

    @Before
    public void create() {
//        建立fileNode依赖

        File file = new File();
        file.setMd5("thiis is a test");
        file.setModifydate(new Date());
        file.setName("这是一个测试");
        file.setPathnode(1);
        file.setPoint(1);
        file.setSize(128);
        file.setSuffix("com");
        file.setUuid("fjdskflsdfjei");
//        insert
        fileMapper.insert(file);
        FileID = file.getId();

        Node node = new Node();
        node.setName("这是测试节点");
        node.setParentnode(0);
        node.setUserid(6);
//        insert
        nodeMapper.insert(node);
        NodeID = node.getId();

    }

    @After
    public void after() {
        fileMapper.deleteByPrimaryKey(FileID);
        nodeMapper.deleteByPrimaryKey(NodeID);
    }

    @Test
    public void CRUD_all() {
        FileNode fileNode = new FileNode();
        fileNode.setFileid(FileID);
        fileNode.setNodeid(NodeID);
//        insert
        fileNodeMapper.insert(fileNode);
        int id = fileNode.getPathnode();
//        select
        pri(fileNodeMapper.selectByPrimaryKey(id));
//        update
        fileNode.setFileid(FileID);
        fileNode.setNodeid(NodeID);
        fileNodeMapper.updateByPrimaryKey(fileNode);
//        select
        pri(fileNodeMapper.selectByPrimaryKey(id));
//        delete
        fileNodeMapper.deleteByPrimaryKey(id);
    }

    @Test
    public void CRUD() {
        FileNode fileNode = new FileNode();
        fileNode.setFileid(FileID);
//        insert
        fileNodeMapper.insertSelective(fileNode);
        int id = fileNode.getPathnode();
//        select
        pri(fileNodeMapper.selectByPrimaryKey(id));
//        update
        fileNode.setNodeid(NodeID);
        fileNodeMapper.updateByPrimaryKeySelective(fileNode);

//        select
        pri(fileNodeMapper.selectByPrimaryKey(id));
//        delete
        fileNodeMapper.deleteByPrimaryKey(id);
    }

}