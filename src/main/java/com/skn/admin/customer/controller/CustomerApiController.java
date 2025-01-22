package com.skn.admin.customer.controller;

import com.skn.admin.config.annotation.Operation;
import com.skn.admin.config.api.apidto.APIDataResponse;
import com.skn.admin.environment.dto.Admin;
import com.skn.admin.customer.dto.Inquiry;
import com.skn.admin.customer.service.InquiryService;
import com.skn.admin.util.NTResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerApiController {
    private final InquiryService service;


    /**
     * 답변 등록 < 고객문의
     */
    @Operation("고객문의 답변 등록")
    @PostMapping(value={"/inquiry"})
    public APIDataResponse<String> insertInquiry(@Valid @RequestBody Inquiry data, Authentication authentication) {

        Admin admin = (Admin) authentication.getPrincipal();
        data.setAdminId(admin.getAdminId());
        data.setAdminIndex(admin.getAdminIndex());
        data.setAdminName(admin.getAdminName());

        NTResult result = service.insertInquiry(data);

        return APIDataResponse.of(result.getResultCode());
    }

    /**
     * 답변 불필요 < 고객문의
     */
    @Operation("고객문의 답변 불필요")
    @PostMapping(value={"/inquiry-no-required/{uid}"})
    public APIDataResponse<String> updateStatus(@Valid @RequestBody Inquiry data, Authentication authentication, @PathVariable String uid) {

        Admin admin = (Admin) authentication.getPrincipal();
        data.setAdminId(admin.getAdminId());
        data.setAdminIndex(admin.getAdminIndex());
        data.setAdminName(admin.getAdminName());

        data.setUid(uid);
        data.setStatus("NOANSWERDONE");
        service.updateInquiryStatus(data);

        return APIDataResponse.of(data.getStatus());
    }


    @Operation("고객문의 수정")
    @PostMapping(value={"/inquiry/update/{uid}"})
    public APIDataResponse<String> updateInquiry(@PathVariable String uid, @Valid @RequestBody Inquiry data, Authentication authentication) {
        Admin admin = (Admin) authentication.getPrincipal();
        data.setAdminId(admin.getAdminId());
        data.setAdminIndex(admin.getAdminIndex());
        data.setAdminName(admin.getAdminName());
        service.updateInquiry(data);

        return APIDataResponse.of(uid);
    }

    @Operation("고객문의 삭제")
    @PostMapping(value={"/inquiry/delete/{uid}"})
    public APIDataResponse<String> deleteInquiry(@PathVariable String uid) {
        service.deleteInquiry(uid);
        return APIDataResponse.of(Boolean.toString(true));
    }
}
