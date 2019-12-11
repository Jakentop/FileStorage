package Dao;

import Model.ExtObj;

public interface ExtObjMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExtObj record);

    int insertSelective(ExtObj record);

    ExtObj selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExtObj record);

    int updateByPrimaryKey(ExtObj record);
}