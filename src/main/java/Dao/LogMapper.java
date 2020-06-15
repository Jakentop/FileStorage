package Dao;

import Model.Log;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LogMapper {

    @Select("select * from Log")
    List<Log> selectAll();

    int deleteByPrimaryKey(Integer id);

    int insert(Log record);

    int insertSelective(Log record);

    Log selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);
}