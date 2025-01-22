package com.skn.admin.newsroom.mapper;

import com.skn.admin.newsroom.dto.SocialMedia;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MediaMapper {
    /**
     * PR > 미디어 목록 조회
     * @param reqMap 페이징
     * @return
     */
    List<SocialMedia> selectMediaList(Map reqMap);
    int insertBlogFeed(SocialMedia socialMedia);
}
