package com.skn.admin.file.mapper;

import com.skn.admin.file.dto.FileInfo;
import com.skn.admin.file.dto.FileInit;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface FileInfoMapper {
    List<FileInfo> selectFileInfoList(Map fileMap);
    FileInfo selectFileInfoView(String uid);
    List<FileInfo> selectFileInfoViewApi(String uid);
    FileInfo selectFileInfoViewParentUid(Map fileMap);
    List<FileInfo> selectFileInfoListViewParentUid(Map fileMap);
    void insertFileInfo(FileInfo fileInfo);
    void deleteFileInfo(Map fileMap);
    void deleteFileInfoApi(FileInfo fileInfo);
    void updateFileInfo(FileInfo fileInfo);

    @Select("SELECT\n" +
            "    CASE\n" +
            "        WHEN EXISTS (\n" +
            "            SELECT 1\n" +
            "            FROM CONTRACT C\n" +
            "            LEFT JOIN FILE F ON C.UID = F.PARENT_UID\n" +
            "            WHERE C.HOSPITAL_NID = #{hospitalNid} AND F.PARENT_TABLE = #{parentTable} AND F.PARENT_TYPE = #{parentType}\n" +
            "        ) THEN 1\n" +
            "        ELSE 0\n" +
            "    END AS isExist;"
    )
    int findFileByParentUidAndParentTypeAndParentTable(String hospitalNid, String parentType, String parentTable);


    FileInfo selectFileUidByParentUidAndFileTableAndParentType(@Param("parentUid") String parentUid, @Param("parentTable") String parentTable, @Param("parentType") String parentType);

    @Delete("DELETE FROM FILE WHERE UID = #{x}")
    void deleteFileInfoByUid(String x);

    FileInfo getFileInfoParentUidAndType(Map<String, String> fileMap);
}
