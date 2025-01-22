package com.skn.admin.ir.controller;

import com.skn.admin.base.etc.Page;
import com.skn.admin.config.annotation.Operation;
import com.skn.admin.ir.dto.Announcement;
import com.skn.admin.ir.dto.AuditReport;
import com.skn.admin.ir.dto.IrData;
import com.skn.admin.ir.dto.IrSchedule;
import com.skn.admin.ir.dto.request.AnnouncementSearchParam;
import com.skn.admin.ir.dto.request.AuditReportSearchParam;
import com.skn.admin.ir.dto.request.IrDataSearchParam;
import com.skn.admin.ir.dto.request.IrScheduleSearchParam;
import com.skn.admin.ir.service.AnnouncementService;
import com.skn.admin.ir.service.AuditReportService;
import com.skn.admin.ir.service.IrDataService;
import com.skn.admin.ir.service.IrScheduleService;
import com.skn.admin.util.NTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ResourceBundle;

import static com.skn.admin.base.etc.Page.PAGE_BLOCK_SIZE;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IrController {
    private final IrScheduleService irScheduleService;

    /**
     * IR 일정 - 목록
     * @param _searchParam
     * @param currentPage
     * @param pageListSize
     * @param model
     * @return
     */
    @Operation("IR 일정 목록")
    @GetMapping({"/ir/ir-schedule-list"})
    public String irScheduleList(@ModelAttribute IrScheduleSearchParam _searchParam,
            @RequestParam(value = "pageNo", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageListSize", defaultValue = "10") int pageListSize,
            Model model)
    {
        // 검색 조건에 따른 전체 레코드 수 조회
        int totalCount = irScheduleService.selectIrScheduleListCount(_searchParam);

        // 페이징 객체 세팅
        Page page = new Page(PAGE_BLOCK_SIZE, currentPage, pageListSize, totalCount);
        page.setPage(totalCount);

        // 검색 파라미터를 페이지 객체와 함께 생성
        IrScheduleSearchParam searchParam = IrScheduleSearchParam.of(_searchParam, page);

        // 목록 데이터를 뷰에 넘김
        model.addAttribute("list", irScheduleService.selectIrScheduleList(searchParam));
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageListSize", pageListSize);
        model.addAttribute("page", page);
        model.addAttribute("searchParam", _searchParam);

        return "/ir/ir-schedule-list";
    }

    /**
     * IR 일정 - 등록 폼
     * @param model
     * @return
     */
    @Operation("IR 일정 등록 폼")
    @GetMapping({"/ir/ir-schedule-reg-form"})
    public String irScheduleRegForm(Model model) {
        model.addAttribute("uid", RandomStringUtils.randomAlphanumeric(16));
        model.addAttribute("viewType", "");
        return "/ir/ir-schedule-form";
    }

    /**
     * IR 일정 - 수정 폼
     * @param model
     * @return
     */
    @Operation("IR 일정 수정 폼")
    @GetMapping({"/ir/ir-schedule-mod-form/{uid}"})
    public String irScheduleModForm(Model model, @PathVariable String uid) {
        IrSchedule detail = irScheduleService.selectIrSchedule(uid);
        if(detail == null)
            return "/404";
        model.addAttribute("detail", detail);
        return "/ir/ir-schedule-form";
    }

    /**
     * IR 일정 - 상세
     * @param model
     * @return
     */
    @Operation("IR 일정 상세")
    @GetMapping({"/ir/ir-schedule/{uid}"})
    public String irScheduleView(Model model, @PathVariable String uid) {
        IrSchedule detail = irScheduleService.selectIrSchedule(uid);
        if(detail == null)
            return "/404";
        model.addAttribute("detail", detail);
        return "/ir/ir-schedule-view";
    }
    
    /**************************************************************************************************/
    private final IrDataService irDataService;
        /**
     * 경영실적발표- 목록
     * @param _searchParam
     * @param currentPage
     * @param pageListSize
     * @param model
     * @return
     */
    @Operation("IR 경영실적발표 목록")
    @GetMapping({"/ir/management-report-list"})
    public String irDataList(@ModelAttribute IrDataSearchParam _searchParam,
            @RequestParam(value = "pageNo", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageListSize", defaultValue = "10") int pageListSize,
            Model model, String classification)
    {
        _searchParam.setClassification(NTUtil.isNull(classification, "RESULT"));

        // 검색 조건에 따른 전체 레코드 수 조회
        int totalCount = irDataService.selectIrDataListCount(_searchParam);

        // 페이징 객체 세팅
        Page page = new Page(PAGE_BLOCK_SIZE, currentPage, pageListSize, totalCount);
        page.setPage(totalCount);

        // 검색 파라미터를 페이지 객체와 함께 생성
        IrDataSearchParam searchParam = IrDataSearchParam.of(_searchParam, page);

        // 목록 데이터를 뷰에 넘김
        model.addAttribute("list", irDataService.selectIrDataList(searchParam));
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageListSize", pageListSize);
        model.addAttribute("page", page);
        model.addAttribute("searchParam", _searchParam);

        return "/ir/ir-data-list";
    }

    /**
     * 경영실적발표 - 등록 폼
     * @param model
     * @return
     */
    @Operation("IR 경영실적발표 등록 폼")
    @GetMapping({"/ir/management-report-reg-form"})
    public String irDataRegForm(Model model, String classification) {
        model.addAttribute("uid", RandomStringUtils.randomAlphanumeric(16));
        model.addAttribute("viewType", "");
        model.addAttribute("classification", NTUtil.isNull(classification, "RESULT"));
        return "/ir/ir-data-form";
    }

    /**
     * 경영실적발표/IR 자료 - 수정 폼
     * @param model
     * @return
     */
    @Operation("IR 경영실적발표 수정 폼")
    @GetMapping({"/ir/management-report-mod-form/{uid}", "/ir/ir-data-mod-form/{uid}"})
    public String irDataModForm(Model model, @PathVariable String uid) {
        IrData detail = irDataService.selectIrData(uid);
        if(detail == null)
            return "/404";
        model.addAttribute("detail", detail);
        model.addAttribute("classification", detail.getClassification());

        return "/ir/ir-data-form";
    }

    /**
     * 경영실적발표/IR 자료 - 상세
     * @param model
     * @return
     */
    @Operation("IR 경영실적발표 상세")
    @GetMapping({"/ir/management-report/{uid}", "/ir/ir-data/{uid}"})
    public String irDataView(Model model, @PathVariable String uid) {
        IrData detail = irDataService.selectIrData(uid);
        if(detail == null)
            return "/404";

        model.addAttribute("detail", detail);

        String dataUrl = "ir-data";
        if("RESULT".equals(detail.getClassification()))
            dataUrl = "management-report";
        model.addAttribute("dataUrl", dataUrl);

        return "/ir/ir-data-view";
    }

    /**
     * IR 자료 - 목록
     * @param _searchParam
     * @param currentPage
     * @param pageListSize
     * @param model
     * @return
     */
    @Operation("IR 경영실적발표 목록")
    @GetMapping({"/ir/ir-data-list"})
    public String irDataList(@ModelAttribute IrDataSearchParam _searchParam,
            @RequestParam(value = "pageNo", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageListSize", defaultValue = "10") int pageListSize,
            Model model)
    {
        return this.irDataList(_searchParam, currentPage, pageListSize, model, "DATA");
    }

    /**
     * IR 자료 - 등록 폼
     * @param model
     * @return
     */
    @Operation("IR 자료 등록 폼")
    @GetMapping({"/ir/ir-data-reg-form"})
    public String irDataRegForm(Model model) {
        return this.irDataRegForm(model, "DATA");
    }

    /**************************************************************************************************/
    private final AnnouncementService announcementService;
        /**
     * 전자공고- 목록
     * @param _searchParam
     * @param currentPage
     * @param pageListSize
     * @param model
     * @return
     */
    @Operation("전자공고 목록")
    @GetMapping({"/ir/announcement-list"})
    public String announcementList(@ModelAttribute AnnouncementSearchParam _searchParam,
            @RequestParam(value = "pageNo", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageListSize", defaultValue = "10") int pageListSize,
            Model model)
    {
        // 검색 조건에 따른 전체 레코드 수 조회
        int totalCount = announcementService.selectAnnouncementListCount(_searchParam);

        // 페이징 객체 세팅
        Page page = new Page(PAGE_BLOCK_SIZE, currentPage, pageListSize, totalCount);
        page.setPage(totalCount);

        // 검색 파라미터를 페이지 객체와 함께 생성
        AnnouncementSearchParam searchParam = AnnouncementSearchParam.of(_searchParam, page);

        // 목록 데이터를 뷰에 넘김
        model.addAttribute("list", announcementService.selectAnnouncementList(searchParam));
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageListSize", pageListSize);
        model.addAttribute("page", page);
        model.addAttribute("searchParam", _searchParam);

        return "/ir/announcement-list";
    }
    /**
     * 전자공고 - 등록 폼
     * @param model
     * @return
     */
    @Operation("전자공고 등록 폼")
    @GetMapping({"/ir/announcement-reg-form"})
    public String announcementRegForm(Model model) {
        String uid = RandomStringUtils.randomAlphanumeric(16);
        model.addAttribute("uid", uid);
        model.addAttribute("viewType", "");

        ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
        model.addAttribute("froalaKey", resourceBundle.getString("froala.key"));
        model.addAttribute("editorUploadPath", "/announcement/"+uid+"/editor");

        return "/ir/announcement-form";
    }

    /**
     * 전자공고 - 수정 폼
     * @param model
     * @return
     */
    @Operation("전자공고 수정 폼")
    @GetMapping({"/ir/announcement-mod-form/{uid}"})
    public String announcementModForm(Model model, @PathVariable String uid) {
        Announcement detail = announcementService.selectAnnouncement(uid);
        if(detail == null)
            return "/404";

        model.addAttribute("detail", detail);

        ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
        model.addAttribute("froalaKey", resourceBundle.getString("froala.key"));
    model.addAttribute("editorUploadPath", "/announcement/"+uid+"/editor");
        return "/ir/announcement-form";
    }

    /**
     * 전자공고 - 상세
     * @param model
     * @return
     */
    @Operation("전자공고 상세")
    @GetMapping({"/ir/announcement/{uid}"})
    public String announcementView(Model model, @PathVariable String uid) {
        Announcement detail = announcementService.selectAnnouncement(uid);
        if(detail == null)
            return "/404";

        model.addAttribute("detail", detail);
        return "/ir/announcement-view";
    }

    /**************************************************************************************************/
    private final AuditReportService auditReportService;
        /**
     * 감사보고서- 목록
     * @param _searchParam
     * @param currentPage
     * @param pageListSize
     * @param model
     * @return
     */
    @Operation("감사보고서 목록")
    @GetMapping({"/ir/audit-report-list"})
    public String auditReportList(@ModelAttribute AuditReportSearchParam _searchParam,
            @RequestParam(value = "pageNo", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageListSize", defaultValue = "10") int pageListSize,
            Model model)
    {
        // 검색 조건에 따른 전체 레코드 수 조회
        int totalCount = auditReportService.selectAuditReportListCount(_searchParam);

        // 페이징 객체 세팅
        Page page = new Page(PAGE_BLOCK_SIZE, currentPage, pageListSize, totalCount);
        page.setPage(totalCount);

        // 검색 파라미터를 페이지 객체와 함께 생성
        AuditReportSearchParam searchParam = AuditReportSearchParam.of(_searchParam, page);

        // 목록 데이터를 뷰에 넘김
        model.addAttribute("list", auditReportService.selectAuditReportList(searchParam));
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageListSize", pageListSize);
        model.addAttribute("page", page);
        model.addAttribute("searchParam", _searchParam);

        return "/ir/audit-report-list";
    }
    /**
     * 감사보고서 - 등록 폼
     * @param model
     * @return
     */
    @Operation("감사보고서 등록 폼")
    @GetMapping({"/ir/audit-report-reg-form"})
    public String auditReportRegForm(Model model) {
        String uid = RandomStringUtils.randomAlphanumeric(16);
        model.addAttribute("uid", uid);
        model.addAttribute("viewType", "");

        ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
        model.addAttribute("froalaKey", resourceBundle.getString("froala.key"));
        model.addAttribute("editorUploadPath", "/audit-report/"+uid+"/editor");

        return "/ir/audit-report-form";
    }

    /**
     * 감사보고서 - 수정 폼
     * @param model
     * @return
     */
    @Operation("감사보고서 수정 폼")
    @GetMapping({"/ir/audit-report-mod-form/{uid}"})
    public String auditReportModForm(Model model, @PathVariable String uid) {
        AuditReport detail = auditReportService.selectAuditReport(uid);
        if(detail == null)
            return "/404";

        model.addAttribute("detail", detail);

        ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
        model.addAttribute("froalaKey", resourceBundle.getString("froala.key"));
        model.addAttribute("editorUploadPath", "/audit-report/"+uid+"/editor");
        return "/ir/audit-report-form";
    }

    /**
     * 감사보고서 - 상세
     * @param model
     * @return
     */
    @Operation("감사보고서 상세")
    @GetMapping({"/ir/audit-report/{uid}"})
    public String auditReportView(Model model, @PathVariable String uid) {
        AuditReport detail = auditReportService.selectAuditReport(uid);
        if(detail == null)
            return "/404";

        model.addAttribute("detail", detail);
        return "/ir/audit-report-view";
    }
}
