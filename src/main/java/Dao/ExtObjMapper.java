package Dao;

import Model.ExtObj;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtObjMapper {


    List<ExtObj> selectAllExtobjByExLinkName(@Param("name") String UUID);

    int deleteByPrimaryKey(Integer id);

    int insert(ExtObj record);

    int insertSelective(ExtObj record);

    ExtObj selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExtObj record);

    int updateByPrimaryKey(ExtObj record);
}