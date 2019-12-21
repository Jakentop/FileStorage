package Dao;

import Model.Node;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NodeMapper {

    //    给出节点ID与当前用户ID查看是否存在这个节点
    @Select("select * from Node where ID=#{nodeID} and UserID=#{userID}")
    Node selectNodeByNodeIDAndUserID(@Param("nodeID") int nodeID,@Param("userID") int userID);

    //    返回当前节点下所有子节点
    List<Node> selectAllChildNode(int nodeID);

    //  获取某一节点下子节点中特定节点名称信息
    Node selectChildByNodeIDAndNodeName(@Param("nodeID") int nodeID,@Param("nodeName") String nodeName);

    int deleteByPrimaryKey(Integer id);

    int insert(Node record);

    int insertSelective(Node record);

    Node selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Node record);

    int updateByPrimaryKey(Node record);
}