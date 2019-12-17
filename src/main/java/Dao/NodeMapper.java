package Dao;

import Model.Node;
import org.apache.ibatis.annotations.Select;

public interface NodeMapper {

    //    给出节点ID与当前用户ID查看是否存在这个节点
    @Select("select * from Node where ID=#{nodeID} and UserID=#{userID}")
    Node selectNodeByNodeIDAndUserID(int nodeID, int userID);

    int deleteByPrimaryKey(Integer id);

    int insert(Node record);

    int insertSelective(Node record);

    Node selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Node record);

    int updateByPrimaryKey(Node record);
}