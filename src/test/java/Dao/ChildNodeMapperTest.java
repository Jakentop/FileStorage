package Dao;

import Model.ChildNode;
import Model.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class ChildNodeMapperTest extends Father {

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private ChildNodeMapper childNodeMapper;

    private int node1, node2;

    @Before
    public void setUp() throws Exception {
        Node node = new Node();
        node.setUserid(2);
        node.setParentnode(0);
        node.setName("hello");
        nodeMapper.insert(node);
        node1 = node.getId();

        node.setParentnode(0);
        node.setName("how are you");
        node.setUserid(2);
        nodeMapper.insert(node);
        node2 = node.getId();
    }

    @After
    public void tearDown() throws Exception {

        nodeMapper.deleteByPrimaryKey(node1);
        nodeMapper.deleteByPrimaryKey(node2);

    }

    @Test
    public void CRUD_all() {
        ChildNode childNode = new ChildNode();
        childNode.setParentid(node1);
        childNode.setChildid(node2);
//        insert

    }
}