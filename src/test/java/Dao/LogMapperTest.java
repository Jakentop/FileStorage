package Dao;

import Model.Log;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class LogMapperTest extends Father {

    @Autowired
    private LogMapper logMapper;

    @Test
    public void crud_all() {

    }

    @Test
    public void crud() {

        Log log = new Log();

        log.setTime(new Date());
        log.setUserid(1);
//        insert
        logMapper.insertSelective(log);
        int id = log.getId();
//        select
        pri(logMapper.selectByPrimaryKey(id));

//        update


        logMapper.updateByPrimaryKeySelective(log);
//        select
        pri(logMapper.selectByPrimaryKey(id));

    }
}