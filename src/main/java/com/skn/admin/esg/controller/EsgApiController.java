package com.skn.admin.esg.controller;

import com.skn.admin.esg.dto.ReportsAndPolicies;
import com.skn.admin.esg.service.ReportsAndPoliciesService;
import com.skn.admin.config.annotation.Operation;
import com.skn.admin.config.api.apidto.APIDataResponse;
import com.skn.admin.environment.dto.Admin;
import com.skn.admin.file.service.FileInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/esg/reports-and-policies")
public class EsgApiController {
    private final ReportsAndPoliciesService service;


    @Operation("보고서 및 정책 등록")
    @PostMapping(value={""})
    public APIDataResponse<String> insertReportsAndPolicies(@Valid @RequestBody ReportsAndPolicies data, Authentication authentication) {

        Admin admin = (Admin) authentication.getPrincipal();
        data.setAdminId(admin.getAdminId());
        data.setAdminIndex(admin.getAdminIndex());
        data.setAdminName(admin.getAdminName());
        service.insertReportsAndPolicies(data);

        return APIDataResponse.of(data.getUid());
    }

    @Operation("보고서 및 정책 수정")
    @PostMapping(value={"/update/{uid}"})
    public APIDataResponse<String> updateReportsAndPolicies(@PathVariable String uid, @Valid @RequestBody ReportsAndPolicies data, Authentication authentication) {
        Admin admin = (Admin) authentication.getPrincipal();
        data.setAdminId(admin.getAdminId());
        data.setAdminIndex(admin.getAdminIndex());
        data.setAdminName(admin.getAdminName());
        service.updateReportsAndPolicies(data);

        return APIDataResponse.of(uid);
    }

    @Operation("보고서 및 정책 삭제")
    @PostMapping(value={"/delete/{uid}"})
    public APIDataResponse<String> deleteReportsAndPolicies(@PathVariable String uid) {
        service.deleteReportsAndPolicies(uid);
        return APIDataResponse.of(Boolean.toString(true));
    }
}
