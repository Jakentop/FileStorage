package Dao;

import Model.ExtLink;
import Model.ExtObj;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

public class ExtObjMapperTest extends Father {

    @Autowired
    private ExtLinkMapper extLinkMapper;

    @Autowired
    private ExtObjMapper extObjMapper;

    private int ExtLinkID ;

    @Before
    public void setUp() throws Exception {
        ExtLink extLink = new ExtLink();
        extLink.setEndtime(new Date());
        extLink.setName("test");
        extLink.setUserid(2);
        //insert
        extLinkMapper.insert(extLink);
        ExtLinkID= extLink.getId();
        //select
        pri(extLinkMapper.selectByPrimaryKey(ExtLinkID));
        //update
        extLink.setEndtime(new Date());
        extLink.setUserid(2);
        extLink.setName("fdjkfls");
        extLinkMapper.updateByPrimaryKey(extLink);
        //select
        pri(extLinkMapper.selectByPrimaryKey(ExtLinkID));
    }

    @After
    public void tearDown() throws Exception {
        //delete
        extLinkMapper.deleteByPrimaryKey(ExtLinkID);

    }

    @Test
    public void CUDR_all() {
        ExtObj extObj = new ExtObj();
        extObj.setExtid(ExtLinkID);
        extObj.setObjid(10);
        extObj.setType("0");
        extObjMapper.insert(extObj);
        int id = extObj.getId();
        pri(extObjMapper.selectByPrimaryKey(id));
        extObjMapper.updateByPrimaryKey(extObj);
        extObjMapper.deleteByPrimaryKey(id);
    }

    @Test
    public void CUDR() {
        ExtObj extObj = new ExtObj();
        extObj.setExtid(ExtLinkID);
        extObj.setObjid(10);
        extObj.setType("0");
        extObjMapper.insertSelective(extObj);
        int id = extObj.getId();
        pri(extObjMapper.selectByPrimaryKey(id));
        extObjMapper.updateByPrimaryKeySelective(extObj);
        extObjMapper.deleteByPrimaryKey(id);
    }
}