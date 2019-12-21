package Dao;

import Model.ChildNode;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ChildNodeMapper {

    /**
     * 删除父级ID下所有子节点引用，慎用
     * @param ParentID
     * @return
     */
    @Delete("delete * from ChildNode where ParentID=#{ParentID}")
    ChildNode deleteAllChildByParentID(@Param("ParentID") Integer ParentID);

    /**
     * 根据父级节点和子节点获取ID
     * @param ParentID
     * @param ChildID
     * @return
     */
    @Select("select * from ChildNode where ParentID=#{ParentID} and ChildID=#{ChildID}")
    ChildNode selectByParentChild(Integer ParentID, Integer ChildID);

    /**
     * 删除父级目录下某一个子目录
     * @param ParentID
     * @param ChildID
     * @return
     */
    @Delete("delete from ChildNode where ParentID=#{parentID} and ChildID=#{ChildID}")
    int deleteByParentIDAndChildID(@Param("ParentID") Integer ParentID, @Param("ChildID") Integer ChildID);

    int deleteByPrimaryKey(Integer id);

    int insert(ChildNode record);

    int insertSelective(ChildNode record);

    ChildNode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChildNode record);

    int updateByPrimaryKey(ChildNode record);
}