package com.skn.admin.newsroom.dto;

import com.skn.admin.base.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Video extends BaseDTO {
    private Integer videoIndex;
    private String title;
    private String content;
    private String brand;
    private String videoUrl;
    private String videoScript;
    private String isOpenBadge;
    public String getIsOpenBadge() {
        if("Y".equals(this.isOpen))
            return "<span class=\"badge py-2 px-3 fs-7 badge-light-primary\">노출</span>";
        return "<span class=\"badge py-2 px-3 fs-7 badge-light\">비노출</span>";
    }
    /**
     * 지역
     */
    public enum Brand {
        BRAND1("그랜드 워커힐 서울"),
        BRAND2("비스타 워커힐 서울"),
        BRAND3("다락휴"),
        BRAND4("SK네트웍스 서비스"),
        BRAND5("SK매직"),
        BRAND6("SK렌터카"),
        BRAND7("SK일렉링크"),
        BRAND8("MINTIT"),
        BRAND9("더 카펫"),
        BRAND10("엔코아"),
        BRAND11("HICO CAPITAL");

        private final String name;

        Brand(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}

