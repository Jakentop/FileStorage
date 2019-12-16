package Dao;

import Model.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public interface UserMapper {

    //    根据用户名称查询用户信息
    List<User> selectByUserName(String name);

    //    根据邮箱查询用户信息
    @Select("select * from [User] where E_Mail=#{value}")
    List<User> selectByEMail(String value);

    //    根据主账号ID获取所有子账号信息
    @Select("select * from [User] where ParentID=#{value}")
    List<User> selectByParentID(int value);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);


}