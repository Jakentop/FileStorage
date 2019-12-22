package Dao;

import Model.ExtLink;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ExtLinkMapper {

    /**
     * 根据UUID名称获取当前的外链
     * @param UUID
     * @return
     */
    @Select("select * from ExtLink where Name=#{name}")
    ExtLink selectByName(@Param("name") String UUID);

    /**
     * 获取当前用户下所有外链集合
     * @param UserID
     * @return
     */
    @Select("select * from ExtLink where UserID=#{userid}")
    List<ExtLink> selectByUserID(@Param("userid") Integer UserID);

    int deleteByPrimaryKey(Integer id);

    int insert(ExtLink record);

    int insertSelective(ExtLink record);

    ExtLink selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExtLink record);

    int updateByPrimaryKey(ExtLink record);
}