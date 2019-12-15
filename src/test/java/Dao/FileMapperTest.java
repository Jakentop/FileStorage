package Dao;

import Model.File;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

public class FileMapperTest extends Father {

    @Autowired
    private FileMapper fileMapper;

    @Test
    public void CRUD_all() {
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
        int id = file.getId();
//        select
        pri(fileMapper.selectByPrimaryKey(id));
//        update
        file.setMd5("this is change");
        file.setModifydate(new Date());
        file.setName("这是一次修改");
        file.setPathnode(2);
        file.setPoint(2);
        file.setSuffix("cn");
        file.setUuid("fjdkljfsdfjweiofj");
        file.setSize(234);
        fileMapper.updateByPrimaryKey(file);
//        select
        pri(fileMapper.selectByPrimaryKey(id));
//    delete
        fileMapper.deleteByPrimaryKey(id);
    }



    @Test
    public void CRUD() {
        File file = new File();
        file.setMd5("thiis is a test");
        file.setModifydate(new Date());
        file.setName("这是一个测试");
        file.setPathnode(1);
        file.setPoint(1);

        file.setUuid("fjdskflsdfjei");

//        insert
        fileMapper.insert(file);
        int id = file.getId();
//        select
        pri(fileMapper.selectByPrimaryKey(id));
//        update
        file.setMd5("this is change");
        file.setModifydate(new Date());
        file.setName("这是一次修改");
        file.setPathnode(2);
        file.setPoint(2);
        file.setSize(234);
        fileMapper.updateByPrimaryKey(file);
//        select
        pri(fileMapper.selectByPrimaryKey(id));
//    delete
        fileMapper.deleteByPrimaryKey(id);
    }

}