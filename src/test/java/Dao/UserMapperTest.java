package Dao;

import Model.User;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.bind.annotation.XmlType;

import static org.junit.Assert.*;
import static org.junit.runners.MethodSorters.DEFAULT;


public class UserMapperTest extends Father {

    @Autowired
    private UserMapper userMapper;

    private int DelID;

    private int SelectID;

    @Test
    public void ainsertSelective() {
        System.out.println("1");
        User user = new User();
        user.setParentid(6);
        user.setUsername("这是单元化测试");
        user.setPassword("123456");
        user.seteMail("840124639@qq.com");
        this.DelID=userMapper.insertSelective(user);

    }
    @Test
    public void binsert() {
        System.out.println("2");
        User user = new User();
        user.setParentid(6);
        user.setUsername("这是单元化测试");
        user.setPassword("123456");
        user.seteMail("840124639@qq.com");
        user.setValid(0);
        user.setLogicnode(1);

        this.SelectID=userMapper.insert(user);

    }
    @Test
    public void cdeleteByPrimaryKey() {
        System.out.println("3");
        userMapper.deleteByPrimaryKey(this.DelID);


    }

    @Test
    public void dselectByPrimaryKey() {

        System.out.println("4");
        System.out.println(userMapper.selectByPrimaryKey(this.SelectID).toString());

    }

    @Test
    public void eupdateByPrimaryKeySelective() {
        System.out.println("5");
        User user = new User();
        user.setId(6);//修改的主键
        user.setParentid(6);
        user.setUsername("修改了信息");
        userMapper.updateByPrimaryKeySelective(user);

        System.out.println(userMapper.selectByPrimaryKey(6).toString());
    }

    @Test
    public void fupdateByPrimaryKey() {
        System.out.println("6");
        User user = new User();
        user.setParentid(6);
        user.setUsername("这是单元化测试");
        user.setPassword("123456");
        user.seteMail("840124639@qq.com");
        user.setValid(0);
        user.setLogicnode(1);
        user.setId(6);
        userMapper.updateByPrimaryKeySelective(user);
        System.out.println(userMapper.selectByPrimaryKey(6).toString());

    }
}