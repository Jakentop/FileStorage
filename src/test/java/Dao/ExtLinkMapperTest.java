package Dao;

import Model.ExtLink;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

public class ExtLinkMapperTest extends Father {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Autowired
    private ExtLinkMapper extLinkMapper;

    @Test
    public void CURD_all() {
        ExtLink extLink = new ExtLink();
        extLink.setEndtime(new Date());
        extLink.setName("test");
        extLink.setUserid(2);
        //insert
        extLinkMapper.insert(extLink);
        int id = extLink.getId();
        //select
        pri(extLinkMapper.selectByPrimaryKey(id));
        //update
        extLink.setEndtime(new Date());
        extLink.setUserid(2);
        extLink.setName("fdjkfls");
        extLinkMapper.updateByPrimaryKey(extLink);
        //select
        pri(extLinkMapper.selectByPrimaryKey(id));
    }
    @Test
    public void CURD() {
        ExtLink extLink = new ExtLink();
        extLink.setEndtime(new Date());
        extLink.setName("test");
        extLink.setUserid(2);
        //insert
        extLinkMapper.insertSelective(extLink);
        int id = extLink.getId();
        //select
        pri(extLinkMapper.selectByPrimaryKey(id));
        //update
        extLink.setEndtime(new Date());
        extLink.setUserid(2);
        extLink.setName("fdjkfls");
        extLinkMapper.updateByPrimaryKeySelective(extLink);
        //select
        pri(extLinkMapper.selectByPrimaryKey(id));

    }
}