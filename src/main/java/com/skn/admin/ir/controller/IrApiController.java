package com.skn.admin.ir.controller;

import com.skn.admin.ir.dto.Announcement;
import com.skn.admin.ir.dto.AuditReport;
import com.skn.admin.ir.dto.IrData;
import com.skn.admin.ir.dto.IrSchedule;
import com.skn.admin.ir.service.AnnouncementService;
import com.skn.admin.ir.service.AuditReportService;
import com.skn.admin.ir.service.IrDataService;
import com.skn.admin.ir.service.IrScheduleService;
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
@RequestMapping("/api/v1/ir")
public class IrApiController {
    private final IrScheduleService irSheduleService;

    @Operation("IR 일정 등록")
    @PostMapping(value={"/ir-schedule"})
    public APIDataResponse<String> insertIrSchedule(@Valid @RequestBody IrSchedule data, Authentication authentication) {

        Admin admin = (Admin) authentication.getPrincipal();
        data.setAdminId(admin.getAdminId());
        data.setAdminIndex(admin.getAdminIndex());
        data.setAdminName(admin.getAdminName());
        irSheduleService.insertIrSchedule(data);

        return APIDataResponse.of(data.getUid());
    }

    @Operation("IR 일정 수정")
    @PostMapping(value={"/ir-schedule/update/{uid}"})
    public APIDataResponse<String> updateIrSchedule(@PathVariable String uid, @Valid @RequestBody IrSchedule data, Authentication authentication) {
        Admin admin = (Admin) authentication.getPrincipal();
        data.setAdminId(admin.getAdminId());
        data.setAdminIndex(admin.getAdminIndex());
        data.setAdminName(admin.getAdminName());
        irSheduleService.updateIrSchedule(data);

        return APIDataResponse.of(uid);
    }

    @Operation("IR 일정 삭제")
    @PostMapping(value={"/ir-schedule/delete/{uid}"})
    public APIDataResponse<String> deleteIrSchedule(@PathVariable String uid) {
        IrSchedule data = irSheduleService.selectIrSchedule(uid);
        irSheduleService.deleteIrSchedule(data);
        return APIDataResponse.of(Boolean.toString(true));
    }

    /*********************************************************************************************************/

    private final IrDataService irDataService;

    @Operation("경영실적발표/IR 자료 등록")
    @PostMapping(value={"/ir-data"})
    public APIDataResponse<String> insertIrData(@Valid @RequestBody IrData data, Authentication authentication) {

        Admin admin = (Admin) authentication.getPrincipal();
        data.setAdminId(admin.getAdminId());
        data.setAdminIndex(admin.getAdminIndex());
        data.setAdminName(admin.getAdminName());
        irDataService.insertIrData(data);

        return APIDataResponse.of(data.getUid());
    }

    @Operation("경영실적발표/IR 자료 수정")
    @PostMapping(value={"/ir-data/update/{uid}"})
    public APIDataResponse<String> updateIrData(@PathVariable String uid, @Valid @RequestBody IrData data, Authentication authentication) {
        Admin admin = (Admin) authentication.getPrincipal();
        data.setAdminId(admin.getAdminId());
        data.setAdminIndex(admin.getAdminIndex());
        data.setAdminName(admin.getAdminName());
        irDataService.updateIrData(data);

        return APIDataResponse.of(uid);
    }

    @Operation("경영실적발표/IR 자료 삭제")
    @PostMapping(value={"/ir-data/delete/{uid}"})
    public APIDataResponse<String> deleteIrData(@PathVariable String uid) {
        irDataService.deleteIrData(uid);
        return APIDataResponse.of(Boolean.toString(true));
    }

    /*********************************************************************************************************/

    private final AnnouncementService announcementService;

    @Operation("전자공고 등록")
    @PostMapping(value={"/announcement"})
    public APIDataResponse<String> insertAnnouncement(@Valid @RequestBody Announcement data, Authentication authentication) {

        Admin admin = (Admin) authentication.getPrincipal();
        data.setAdminId(admin.getAdminId());
        data.setAdminIndex(admin.getAdminIndex());
        data.setAdminName(admin.getAdminName());
        announcementService.insertAnnouncement(data);

        return APIDataResponse.of(data.getUid());
    }

    @Operation("전자공고 수정")
    @PostMapping(value={"/announcement/update/{uid}"})
    public APIDataResponse<String> updateAnnouncement(@PathVariable String uid, @Valid @RequestBody Announcement data, Authentication authentication) {
        Admin admin = (Admin) authentication.getPrincipal();
        data.setAdminId(admin.getAdminId());
        data.setAdminIndex(admin.getAdminIndex());
        data.setAdminName(admin.getAdminName());
        announcementService.updateAnnouncement(data);

        return APIDataResponse.of(uid);
    }

    @Operation("전자공고 삭제")
    @PostMapping(value={"/announcement/delete/{uid}"})
    public APIDataResponse<String> deleteAnnouncement(@PathVariable String uid) {
        announcementService.deleteAnnouncement(uid);
        return APIDataResponse.of(Boolean.toString(true));
    }

    /*********************************************************************************************************/

    private final AuditReportService auditReportService;

    @Operation("감사보고서 등록")
    @PostMapping(value={"/audit-report"})
    public APIDataResponse<String> insertAuditReport(@Valid @RequestBody AuditReport data, Authentication authentication) {

        Admin admin = (Admin) authentication.getPrincipal();
        data.setAdminId(admin.getAdminId());
        data.setAdminIndex(admin.getAdminIndex());
        data.setAdminName(admin.getAdminName());
        auditReportService.insertAuditReport(data);

        return APIDataResponse.of(data.getUid());
    }

    @Operation("감사보고서 수정")
    @PostMapping(value={"/audit-report/update/{uid}"})
    public APIDataResponse<String> updateAuditReport(@PathVariable String uid, @Valid @RequestBody AuditReport data, Authentication authentication) {
        Admin admin = (Admin) authentication.getPrincipal();
        data.setAdminId(admin.getAdminId());
        data.setAdminIndex(admin.getAdminIndex());
        data.setAdminName(admin.getAdminName());
        auditReportService.updateAuditReport(data);

        return APIDataResponse.of(uid);
    }

    @Operation("감사보고서 삭제")
    @PostMapping(value={"/audit-report/delete/{uid}"})
    public APIDataResponse<String> deleteAuditReport(@PathVariable String uid) {
        auditReportService.deleteAuditReport(uid);
        return APIDataResponse.of(Boolean.toString(true));
    }
}
