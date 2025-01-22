package com.skn.admin.business.controller;

import com.skn.admin.business.service.BusinessContentsService;
import com.skn.admin.config.annotation.Operation;
import com.skn.admin.config.api.apidto.APIDataResponse;
import com.skn.admin.environment.dto.Admin;
import com.skn.admin.business.dto.BusinessContents;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/business/business-contents")
public class BusinessApiController {
    private final BusinessContentsService service;

    @Operation("사업별 콘텐츠 등록")
    @PostMapping(value={""})
    public APIDataResponse<String> insertBusinessContents(@Valid @RequestBody BusinessContents data, Authentication authentication) {

        Admin admin = (Admin) authentication.getPrincipal();
        data.setAdminId(admin.getAdminId());
        data.setAdminIndex(admin.getAdminIndex());
        data.setAdminName(admin.getAdminName());
        service.insertBusinessContents(data);

        return APIDataResponse.of(data.getUid());
    }

    @Operation("사업별 콘텐츠 수정")
    @PostMapping(value={"/update/{uid}"})
    public APIDataResponse<String> updateBusinessContents(@PathVariable String uid, @Valid @RequestBody BusinessContents data, Authentication authentication) {
        Admin admin = (Admin) authentication.getPrincipal();
        data.setAdminId(admin.getAdminId());
        data.setAdminIndex(admin.getAdminIndex());
        data.setAdminName(admin.getAdminName());
        service.updateBusinessContents(data);

        return APIDataResponse.of(uid);
    }

    @Operation("사업별 콘텐츠 삭제")
    @PostMapping(value={"/delete/{uid}"})
    public APIDataResponse<String> deleteBusinessContents(@PathVariable String uid) {
        service.deleteBusinessContents(uid);
        return APIDataResponse.of(Boolean.toString(true));
    }
}
