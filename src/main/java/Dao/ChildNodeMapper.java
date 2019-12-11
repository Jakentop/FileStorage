package Dao;

import Model.ChildNode;

public interface ChildNodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChildNode record);

    int insertSelective(ChildNode record);

    ChildNode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChildNode record);

    int updateByPrimaryKey(ChildNode record);
}