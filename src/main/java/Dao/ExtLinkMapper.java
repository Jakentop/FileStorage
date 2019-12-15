package Dao;

import Model.ExtLink;

public interface ExtLinkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExtLink record);

    int insertSelective(ExtLink record);

    ExtLink selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExtLink record);

    int updateByPrimaryKey(ExtLink record);
}