package com.skn.admin.newsroom.mapper;

import com.skn.admin.newsroom.dto.Video;
import com.skn.admin.newsroom.dto.request.VideoSearchParam;

import java.util.List;

public interface VideoMapper {
    List<Video> selectVideoList(VideoSearchParam param);
    int selectVideoListCount(VideoSearchParam param);
    int insertVideo(Video data);
    int deleteVideo(Video data);
    int updateVideo(Video data);
    Video selectVideo(String uid);
}
