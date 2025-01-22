package com.skn.admin.newsroom.controller;

import com.skn.admin.newsroom.dto.Press;
import com.skn.admin.newsroom.dto.Video;
import com.skn.admin.newsroom.service.PressService;
import com.skn.admin.newsroom.service.VideoService;
import com.skn.admin.config.annotation.Operation;
import com.skn.admin.config.api.apidto.APIDataResponse;
import com.skn.admin.environment.dto.Admin;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/news-room")
public class NewsRoomApiController {
    private final VideoService videoService;

    @Operation("영상 라이브러리 등록")
    @PostMapping(value={"/video"})
    public APIDataResponse<String> insertVideo(@Valid @RequestBody Video data, Authentication authentication) {

        Admin admin = (Admin) authentication.getPrincipal();
        data.setAdminId(admin.getAdminId());
        data.setAdminIndex(admin.getAdminIndex());
        data.setAdminName(admin.getAdminName());
        videoService.insertVideo(data);

        return APIDataResponse.of(data.getUid());
    }

    @Operation("영상 라이브러리 수정")
    @PostMapping(value={"/video/update/{uid}"})
    public APIDataResponse<String> updateVideo(@PathVariable String uid, @Valid @RequestBody Video data, Authentication authentication) {
        Admin admin = (Admin) authentication.getPrincipal();
        data.setAdminId(admin.getAdminId());
        data.setAdminIndex(admin.getAdminIndex());
        data.setAdminName(admin.getAdminName());
        videoService.updateVideo(data);

        return APIDataResponse.of(uid);
    }

    @Operation("영상 라이브러리 삭제")
    @PostMapping(value={"/video/delete/{uid}"})
    public APIDataResponse<String> deleteVideo(@PathVariable String uid) {
        videoService.deleteVideo(uid);
        return APIDataResponse.of(Boolean.toString(true));
    }

    /*********************************************************************************************************/

    private final PressService PressService;

    @Operation("프레스 센터 등록")
    @PostMapping(value={"/press"})
    public APIDataResponse<String> insertPress(@Valid @RequestBody Press data, Authentication authentication) {

        Admin admin = (Admin) authentication.getPrincipal();
        data.setAdminId(admin.getAdminId());
        data.setAdminIndex(admin.getAdminIndex());
        data.setAdminName(admin.getAdminName());
        PressService.insertPress(data);

        return APIDataResponse.of(data.getUid());
    }

    @Operation("프레스 센터 수정")
    @PostMapping(value={"/press/update/{uid}"})
    public APIDataResponse<String> updatePress(@PathVariable String uid, @Valid @RequestBody Press data, Authentication authentication) {
        Admin admin = (Admin) authentication.getPrincipal();
        data.setAdminId(admin.getAdminId());
        data.setAdminIndex(admin.getAdminIndex());
        data.setAdminName(admin.getAdminName());
        PressService.updatePress(data);

        return APIDataResponse.of(uid);
    }

    @Operation("프레스 센터 삭제")
    @PostMapping(value={"/press/delete/{uid}"})
    public APIDataResponse<String> deletePress(@PathVariable String uid) {
        PressService.deletePress(uid);
        return APIDataResponse.of(Boolean.toString(true));
    }
}
