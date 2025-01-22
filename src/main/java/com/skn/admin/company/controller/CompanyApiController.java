package com.skn.admin.company.controller;

import com.skn.admin.config.annotation.Operation;
import com.skn.admin.config.api.apidto.APIDataResponse;
import com.skn.admin.environment.dto.Admin;
import com.skn.admin.company.dto.GlobalNetwork;
import com.skn.admin.company.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/company/global-network")
public class CompanyApiController {
    private final CompanyService service;

    @Operation("글로벌 네트워크 등록")
    @PostMapping(value={""})
    public APIDataResponse<String> insertGlobalNetwork(@Valid @RequestBody GlobalNetwork data, Authentication authentication) {

        Admin admin = (Admin) authentication.getPrincipal();
        data.setAdminId(admin.getAdminId());
        data.setAdminIndex(admin.getAdminIndex());
        data.setAdminName(admin.getAdminName());
        service.insertGlobalNetwork(data);

        return APIDataResponse.of(data.getUid());
    }

    @Operation("글로벌 네트워크 수정")
    @PostMapping(value={"/update/{uid}"})
    public APIDataResponse<String> updateGlobalNetwork(@PathVariable String uid, @Valid @RequestBody GlobalNetwork data, Authentication authentication) {
        Admin admin = (Admin) authentication.getPrincipal();
        data.setAdminId(admin.getAdminId());
        data.setAdminIndex(admin.getAdminIndex());
        data.setAdminName(admin.getAdminName());
        service.updateGlobalNetwork(data);

        return APIDataResponse.of(uid);
    }

    @Operation("글로벌 네트워크 삭제")
    @PostMapping(value={"/delete/{uid}"})
    public APIDataResponse<String> deleteGlobalNetwork(@PathVariable String uid) {
        GlobalNetwork data = service.selectGlobalNetwork(uid);
        int result = service.deleteGlobalNetwork(data);
        return APIDataResponse.of(Boolean.toString(true));
    }
}
