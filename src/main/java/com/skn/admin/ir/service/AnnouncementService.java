package com.skn.admin.ir.service;

import com.skn.admin.file.dto.FileInfo;
import com.skn.admin.file.service.FileInfoService;
import com.skn.admin.ir.dto.Announcement;
import com.skn.admin.ir.dto.request.AnnouncementSearchParam;
import com.skn.admin.ir.mapper.AnnouncementMapper;
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
public class AnnouncementService {
    private final AnnouncementMapper mapper;
    private final FileInfoService fileInfoService;

    public List<Announcement> selectAnnouncementList(AnnouncementSearchParam param) {
        return mapper.selectAnnouncementList(param);
    }

    public int selectAnnouncementListCount(AnnouncementSearchParam param) {
        return mapper.selectAnnouncementListCount(param);
    }

    @Transactional
    public int insertAnnouncement(Announcement data) {
        // 기본 정보 insert
        int result = mapper.insertAnnouncement(data);
        if(result > 0) {
            // file info > update parent index
            FileInfo f = new FileInfo();
            f.setParentUid(data.getUid());
            f.setParentIndex(data.getAnnouncementIndex().toString());
            fileInfoService.updateFileInfo(f);
        }

        return result;
    }

    public Announcement selectAnnouncement(String uid) {
        Announcement data = mapper.selectAnnouncement(uid);
        return data;
    }

    public int updateAnnouncement(Announcement data) {
        int result = mapper.updateAnnouncement(data);
        if(result > 0) {
            // file info > update parent index
            FileInfo f = new FileInfo();
            f.setParentUid(data.getUid());
            f.setParentIndex(data.getAnnouncementIndex().toString());
            fileInfoService.updateFileInfo(f);
        }
        return result;
    }

    public void deleteAnnouncement(String uid) {
        Announcement data = this.selectAnnouncement(uid);
        int result = mapper.deleteAnnouncement(data);
        if(result < 1)
            return;

        // 파일 삭제
        Map<String, String> map = new HashMap<>();
        map.put("parentUid", uid);
        map.put("parentTable", "ANNOUNCEMENT");

        try {
            fileInfoService.deleteFile(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
