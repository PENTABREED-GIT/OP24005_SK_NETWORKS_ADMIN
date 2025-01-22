package com.skn.admin.site.service;

import com.skn.admin.base.etc.Page;
import com.skn.admin.config.api.exception.GeneralException;
import com.skn.admin.environment.dto.Setting;
import com.skn.admin.environment.service.SettingService;
import com.skn.admin.site.dto.AdminGroup;
import com.skn.admin.site.dto.adminaccount.AdminAccount;
import com.skn.admin.site.dto.adminaccount.request.AdminAccountReqeust;
import com.skn.admin.site.dto.adminaccount.request.AdminAccountSearchParam;
import com.skn.admin.site.mapper.AdminAccountMapper;
import com.skn.admin.site.mapper.AdminGroupOldMapper;
import com.skn.admin.util.NTCryptoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.*;

import static com.skn.admin.config.api.constant.ErrorCode.BAD_REQUEST;
import static com.skn.admin.customer.service.InquiryService.maskName;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminAccountService {
	private final AdminAccountMapper adminAccountMapper;
	private final AdminGroupOldMapper adminGroupOldMapper;
	private final SettingService settingService;

	@Transactional(readOnly = true)
	public boolean checkDuplicateAdminId(String adminId) {
		return adminAccountMapper.checkDuplicateAdminId(adminId);
	}

	@Transactional(readOnly = true)
	public boolean checkDuplicateEmail(String email) {
		String decryptedEmail = NTCryptoUtil.encrypt(email, "EMAIL");

		return adminAccountMapper.checkDuplicateEmail(decryptedEmail);
	}

	@Transactional(readOnly = true)
	public void getAdminAccountList(AdminAccountSearchParam _param, Page paging, Model model) {
		int totalCount = 0;

		AdminAccountSearchParam params = AdminAccountSearchParam.of(_param, paging);
		List<AdminAccount> list = this.selectAdminAccountList(params);
		totalCount = this.selectAdminAccountTotalCount(params);
		paging.setPage(totalCount);

		model.addAttribute("adminAccountList", list)
			.addAttribute("adminGroupList", this.selectAdminGroupList("Y"))
			.addAttribute("departmentList", this.selectDepartmentList())
			.addAttribute("page", paging)
			.addAttribute("listNum", totalCount)
			.addAttribute("search", params);
	}

	public List<AdminAccount> selectAdminAccountList(AdminAccountSearchParam param) {
		List<AdminAccount> list = adminAccountMapper.selectAdminAccountList(param);

		for (int i = 0; i < list.size(); i++) {
			// Inquiry 객체에서 이름을 가져와 마스킹 후, 다시 설정
			String maskedName = maskName(list.get(i).getAdminName());
			list.get(i).setAdminName(maskedName);  // setName을 사용하여 변경된 이름을 설정
		}

		return list;
	}
	public int selectAdminAccountTotalCount(AdminAccountSearchParam param) {
		return adminAccountMapper.selectAdminAccountTotalCount(param);
	}
	public AdminAccount selectAdminAccount(String uid) {
		Optional<AdminAccount> _adminAccount = adminAccountMapper.selectAdminAccount(uid);

		if(_adminAccount.isEmpty()) throw new GeneralException(BAD_REQUEST);

		return _adminAccount.get();
	}

	@Transactional
	public ResponseEntity<AdminAccount> insertAdminAccount(AdminAccountReqeust.Insert req) {
		// 세팅 기록 들고오기
		Setting setting = settingService.selectSetting();

		AdminAccount adminAccount = new AdminAccount(req, setting.getPwPeriod(), setting.getPwPeriodNext());

		adminAccountMapper.insertAdminAccount(adminAccount);

		return ResponseEntity.ok(adminAccount);
	}

	@Transactional
	public ResponseEntity<AdminAccount> updateAdminAccount(AdminAccountReqeust.Update req) {
		// 세팅 기록 들고오기
		Setting setting = settingService.selectSetting();
		AdminAccount adminAccount = new AdminAccount(req, setting.getPwPeriod(), setting.getPwPeriodNext());
		try {
			adminAccountMapper.updateAdminAccount(adminAccount);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok(adminAccount);
	}

	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Map<String, String>> deleteAdminAccount(String uid) {
		adminAccountMapper.deleteAdminAccount(uid);

		Map<String, String> resMap = new HashMap<>();
		resMap.put("msg", "삭제완료");

		return ResponseEntity.ok(resMap);
	}

	public List<String> selectDepartmentList() {
		return adminAccountMapper.selectDepartmentList();
	}

	public List<AdminGroup> selectAdminGroupList(String isUse) {
		return adminGroupOldMapper.selectAdminGroupList(isUse);
	}

	public List<AdminAccount> selectAdminListAll() {
		return adminAccountMapper.selectAdminListAll();
	}

	public void updateAdminIsSleep(AdminAccount adminAccount) {
		adminAccountMapper.updateAdminIsSleep(adminAccount);
	}

	/**
	* 3개월 이상 접속하지 않은 관리자 계정 사용중지 스케쥴러
	* */
	@Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
	public void updateInactiveAdmins() {
		// 3개월 이상 접속하지 않은 관리자 UID 목록 가져오기
		List<String> inactiveAdminUids = adminAccountMapper.findInactiveAdminUids();

		for (String uid : inactiveAdminUids) {
			AdminAccount adminAccount = new AdminAccount();
			adminAccount.setUid(uid);
			updateAdminIsSleep(adminAccount);
		}
	}
}
