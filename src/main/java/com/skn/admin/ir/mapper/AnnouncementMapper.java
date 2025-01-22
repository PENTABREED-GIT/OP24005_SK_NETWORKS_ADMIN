package com.skn.admin.ir.mapper;

import com.skn.admin.ir.dto.Announcement;
import com.skn.admin.ir.dto.request.AnnouncementSearchParam;

import java.util.List;

public interface AnnouncementMapper {
    List<Announcement> selectAnnouncementList(AnnouncementSearchParam param);
    int selectAnnouncementListCount(AnnouncementSearchParam param);
    int insertAnnouncement(Announcement data);
    int deleteAnnouncement(Announcement data);
    int updateAnnouncement(Announcement data);
    Announcement selectAnnouncement(String uid);
}
