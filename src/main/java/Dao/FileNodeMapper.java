package Dao;

import Model.FileNode;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FileNodeMapper {

    /**
     * 根据目录节点ID和文件名称获取文件信息
     * @param NodeID
     * @param FileName
     * @return FileNode
     */
    @Select("select * from FileNode where  NodeID=#{NodeID} and [Name]=#{FileName}")
    FileNode selectByNodeIDandFileName(@Param("NodeID") Integer NodeID,@Param("FileName") String FileName);

    //获取某一文件节点下所有文件
    @Select("select * from FileNode where NodeID=#{NodeID}")
    List<FileNode> selectAllFilebyNodeID(@Param("NodeID") Integer NodeID);

    int deleteByPrimaryKey(Integer pathnode);

    int insert(FileNode record);

    int insertSelective(FileNode record);

    FileNode selectByPrimaryKey(Integer pathnode);

    int updateByPrimaryKeySelective(FileNode record);

    int updateByPrimaryKey(FileNode record);
}