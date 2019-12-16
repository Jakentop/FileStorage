package Dao;

import Model.Log;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

public class LogMapperTest extends Father {

    @Autowired
    private LogMapper logMapper;

    @Test
    public void crud_all() {
        Log log = new Log();
        log.setEvent("fjskdlfjdskl");
        log.setTime(new Date());
        log.setUserid(1);
//        insert
        logMapper.insert(log);
        int id = log.getId();
//        select
        pri(logMapper.selectByPrimaryKey(id));

//        update
        log.setEvent("fjskdfdsflfjdskl");
        log.setTime(new Date());
        log.setUserid(2);
        logMapper.updateByPrimaryKey(log);
//        select
        pri(logMapper.selectByPrimaryKey(id));
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

        log.setEvent("fjskdfdsflfjdskl");
        logMapper.updateByPrimaryKeySelective(log);
//        select
        pri(logMapper.selectByPrimaryKey(id));

    }
}