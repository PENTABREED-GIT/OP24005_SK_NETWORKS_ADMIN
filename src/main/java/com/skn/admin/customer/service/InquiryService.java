package com.skn.admin.customer.service;

import com.skn.admin.customer.dto.Inquiry;
import com.skn.admin.customer.dto.request.InquirySearchParam;
import com.skn.admin.customer.mapper.InquiryMapper;
import com.skn.admin.file.service.FileInfoService;
import com.skn.admin.util.NTCryptoUtil;
import com.skn.admin.util.NTResult;
import com.skn.admin.util.NTUtil;
import io.swagger.v3.oas.models.examples.Example;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class InquiryService {
    private final InquiryMapper mapper;
    private final FileInfoService fileInfoService;
    private final EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(Example.class);


    public List<Inquiry> selectInquiryList(InquirySearchParam param) {
        List<Inquiry> list = mapper.selectInquiryList(param);

        for (int i = 0; i < list.size(); i++) {
            // Inquiry 객체에서 이름을 가져와 마스킹 후, 다시 설정
            String maskedName = maskName(list.get(i).getName());
            list.get(i).setName(maskedName);  // setName을 사용하여 변경된 이름을 설정
        }

        return list;
    }

    public int selectInquiryListCount(InquirySearchParam param) {
        return mapper.selectInquiryListCount(param);
    }

    @Transactional
    public NTResult insertInquiry(Inquiry data) {
        logger.debug("START insert Inquiry :::::::::::::::::::::::::::");
        String uid = NTUtil.isNull(data.getUid(), "");
        NTResult ntResult = new NTResult();
        ntResult.setSuccess();

        // 이메일 발송
        String toEmail = data.getEmail();
        String subject = data.getAnswerTitle();
        String body = data.getAnswerContent();

        logger.debug("START sendEmail :::::::::::::::::::::::::::");
        NTResult emailSent = emailService.sendEmail(toEmail, subject, body);

        if (!"SUCCESS".equals(emailSent.getResultCode())) {
            // 이메일 발송 실패 시 처리
            System.out.println("SEND MAIL ::::::::::::::::::::::::::: FAIL");
            ntResult.setFail();
            ntResult.setResultCode("SEND FAIL");
            return ntResult;
        }

        data.setStatus("DONE");
        // 답변 내용 insert
        this.insertAnswer(data);
        // 답변 상태 DONE(완료) 으로 업데이트 및 답변 작성자 UPDATE
        // 기존 게시글 uid set
        data.setUid(uid);
        mapper.updateInquiry(data);
        return ntResult;
    }

    @Transactional
    public int insertAnswer(Inquiry data) {
        data.setUid(RandomStringUtils.randomAlphanumeric(16));
        int result = mapper.insertAnswer(data);
        return result;
    }

    public Inquiry selectInquiry(String uid) {
        Inquiry data = mapper.selectInquiry(uid);
        data.setEtcInfo(NTCryptoUtil.decrypt(data.getEtcInfo(), "ETC"));
        return data;
    }

    public List<Inquiry> selectInquiryAnswer(String uid) {
        List<Inquiry> list = mapper.selectInquiryAnswer(uid);
        return list;
    }

    public int updateInquiry(Inquiry data) {
        return mapper.updateInquiry(data);
    }

    public int updateInquiryStatus(Inquiry data) {
        return mapper.updateInquiryStatus(data);
    }

    public void deleteInquiry(String uid) {
        Inquiry data = this.selectInquiry(uid);
        int result = mapper.deleteInquiry(data);

        if(result < 1)
            return;

        // 파일 삭제
        Map<String, String> map = new HashMap<>();
        map.put("parentUid", uid);
        map.put("parentTable", "REPORTS_AND_POLICIES");

        try {
            fileInfoService.deleteFile(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String maskName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }

        // 공백을 유지하기 위해 문자열 배열로 분리
        String[] parts = name.split(" ");
        int numParts = parts.length;

        StringBuilder maskedName = new StringBuilder();

        for (int i = 0; i < numParts; i++) {
            String part = parts[i];
            String maskedPart = (i == numParts - 1) ? maskPart(part) : part;
            maskedName.append(maskedPart);
            if (i < numParts - 1) {
                maskedName.append(" ");
            }
        }

        return maskedName.toString();
    }

    public static String maskPart(String part) {
        int length = part.length();

        if (isKorean(part)) {
            // 한글 이름 처리
            if (length == 2) {
                return part.substring(0, 1) + "*";
            } else if (length == 3) {
                return part.substring(0, 1) + "*" + part.substring(2);
            } else if (length == 4) {
                return part.substring(0, 1) + "*" + part.substring(length - 1);
            } else if (length >= 5) {
                return part.substring(0, 1) + "*".repeat(length - 2) + part.substring(length - 1);
            }
        } else {
            // 영문 이름 처리
            if (length == 2) {
                return part.substring(0, 1) + "*";
            } else if (length == 3) {
                return part.substring(0, 1) + "*" + part.substring(2);
            } else if (length == 4) {
                return part.substring(0, length - 3) + "**" + part.substring(length - 1);
            } else if (length >= 5) {
                return part.substring(0, length - 3) + "**" + part.substring(length - 1);
            }
        }

        return part; // 처리할 수 없는 경우 원본 반환
    }

    public static boolean isKorean(String str) {
        // 한글 범위 (가-힣) 확인
        return str.chars().allMatch(c -> (c >= 0xAC00 && c <= 0xD7A3));
    }

    /**
     * 등록일 1년 이상된 고객문의 삭제 스케쥴러
     * 실행일: 매일 자정
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteOver1yearInquiryScheduler() {
        try {
            mapper.deleteOver1yearInquiry();
        } catch (Exception e) {
            System.err.println("1년 이상된 문의 삭제 중 오류 발생: " + e.getMessage());
        }
    }
}
