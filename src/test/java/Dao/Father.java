package Dao;

import com.alibaba.fastjson.JSON;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:SpringMybatis.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Father {

    public static void pri(Object string) {
        System.out.println(JSON.toJSONString(string));
    }

}
