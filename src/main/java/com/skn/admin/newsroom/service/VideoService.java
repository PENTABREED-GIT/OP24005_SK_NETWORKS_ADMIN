package com.skn.admin.newsroom.service;

import com.skn.admin.file.dto.FileInfo;
import com.skn.admin.file.service.FileInfoService;
import com.skn.admin.newsroom.dto.Video;
import com.skn.admin.newsroom.dto.request.VideoSearchParam;
import com.skn.admin.newsroom.mapper.VideoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoMapper mapper;
    private final FileInfoService fileInfoService;

    public List<Video> selectVideoList(VideoSearchParam param) {
        return mapper.selectVideoList(param);
    }

    public int selectVideoListCount(VideoSearchParam param) {
        return mapper.selectVideoListCount(param);
    }

    @Transactional
    public int insertVideo(Video data) {
        // 기본 정보 insert
        int result = mapper.insertVideo(data);
        if(result > 0) {
            // file info > update parent index
            FileInfo f = new FileInfo();
            f.setParentUid(data.getUid());
            f.setParentIndex(data.getVideoIndex().toString());
            fileInfoService.updateFileInfo(f);
        }
        return result;
    }

    public Video selectVideo(String uid) {
        Video data = mapper.selectVideo(uid);
        return data;
    }

    public int updateVideo(Video data) {
        int result = mapper.updateVideo(data);
        if(result > 0) {
            // file info > update parent index
            FileInfo f = new FileInfo();
            f.setParentUid(data.getUid());
            f.setParentIndex(data.getVideoIndex().toString());
            fileInfoService.updateFileInfo(f);
        }
        return result;
    }

    public void deleteVideo(String uid) {
        Video data = this.selectVideo(uid);
        int result = mapper.deleteVideo(data);

        if(result < 1)
            return;

        // 파일 삭제
        Map<String, String> map = new HashMap<>();
        map.put("parentUid", uid);
        map.put("parentTable", "VIDEO");

        try {
            fileInfoService.deleteFile(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
