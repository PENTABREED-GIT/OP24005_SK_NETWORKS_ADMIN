package com.skn.admin.customer.dto;

import com.skn.admin.base.dto.BaseDTO;
import com.skn.admin.util.NTCryptoUtil;
import com.skn.admin.util.NTUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Inquiry extends BaseDTO {
    private Integer inquiryIndex;
    private String name;
    private String email;
    private String title;
    private String content;
    private String etcInfo;
    private String classification;
    private String status;
    private String answerTitle;
    private String answerContent;
    private String answerDate;
    private String dateType;

    /**
     * 진행상태
     */
    public enum Status {
        READY("미확인"),
        ING("진행중(열람)"),
        DONE("완료"),
        NOANSWERDONE("미답변 완료");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

    }

    public String getEmailDecrypted() {
        if (!NTUtil.isEmpty(this.getEmail()))  this.email = NTCryptoUtil.decrypt(this.getEmail(), "EMAIL");

        return email;
    }
}

