package Dao;

import Model.FileNode;

public interface FileNodeMapper {
    int deleteByPrimaryKey(Integer pathnode);

    int insert(FileNode record);

    int insertSelective(FileNode record);

    FileNode selectByPrimaryKey(Integer pathnode);

    int updateByPrimaryKeySelective(FileNode record);

    int updateByPrimaryKey(FileNode record);
}