package Dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserMapperTest extends Father {

    @Autowired
    UserMapper userMapper;

    @Test
    public void selectByEMail() {
    }

    @Test
    public void selectChildByParentID() {
         userMapper.selectChildByParentID(33);
        System.out.println("fdjk");

    }
}