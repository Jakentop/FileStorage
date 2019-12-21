package Dao;

import Model.File;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface FileMapper {

    //    根据MD5码查询物理文件记录
    @Select("select * from [File] where MD5=#{md5};")
    File selectByMd5(@Param("md5") String MD5);

    //    根据ID将文件指针加1
    @Update("update [File] set point = point+1 WHERE id=#{ID}")
    int updatePointByID(@Param("ID") Integer ID);

    //    根据ID将文件指针减1
    @Update("update [File] set point = point-1 WHERE id=#{ID}")
    int updatesubPointByID(@Param("ID") Integer ID);

    int deleteByPrimaryKey(Integer id);

    int insert(File record);

    int insertSelective(File record);

    File selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(File record);

    int updateByPrimaryKey(File record);
}