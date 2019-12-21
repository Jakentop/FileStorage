package Dao;

import Model.Node;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NodeMapperTest extends Father {

    @Autowired
    private NodeMapper nodeMapper;

    @Test
    public void selectNodeByNodeIDAndUserID() {
        nodeMapper.selectNodeByNodeIDAndUserID(0, 31);
    }

    @Test
    public void selectChildByNodeIDAndNodeName() {
        nodeMapper.selectChildByNodeIDAndNodeName(24,"test");
    }

    @Test
    public void selectAllChildNode() {
        nodeMapper.selectAllChildNode(24);
    }

    @Test
    public void insert() {
        Node node = new Node();
        node.setName("text");
        node.setParentnode(24);
        node.setUserid(33);
        nodeMapper.insert(node);
        nodeMapper.deleteByPrimaryKey(node.getId());
    }

    @Test
    public void CUDR_all()
    {

        Node node = new Node();
        node.setName("这是测试节点");
        node.setParentnode(0);
        node.setUserid(6);

//        insert
        nodeMapper.insert(node);
        int id = node.getId();
//        select
        pri(nodeMapper.selectByPrimaryKey(id));
//        update
        node.setName("修改结果");
        node.setParentnode(1);
        node.setUserid(7);
        node.setId(id);

        nodeMapper.updateByPrimaryKey(node);
//        select
        pri(nodeMapper.selectByPrimaryKey(id));
//        delete
        nodeMapper.deleteByPrimaryKey(id);

    }

    @Test
    public void CUDR_part() {
        Node node = new Node();
        node.setName("这是测试节点");
        node.setParentnode(0);

//        insert
        int id = nodeMapper.insertSelective(node);
//        select
        pri(nodeMapper.selectByPrimaryKey(id));
//        update
        node.setName("修改结果");
        node.setId(id);

        nodeMapper.updateByPrimaryKey(node);
//        select
        pri(nodeMapper.updateByPrimaryKeySelective(node));
//        delete
        nodeMapper.deleteByPrimaryKey(id);
    }


}