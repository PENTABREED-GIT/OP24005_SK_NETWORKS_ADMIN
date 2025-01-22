package com.skn.admin.company.dto;

import com.skn.admin.base.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GlobalNetwork extends BaseDTO {
    private String uid;
    private Integer globalNetworkIndex;
    private String isKo="N";
    private String isEn="N";
    private String classification;
    private String classificationName;
    private String regionNameKo;
    private String regionNameEn;
    private String countryNameKo;
    private String countryNameEn;
    private String companyNameKo;
    private String companyNameEn;
    private String addressKo;
    private String addressEn;
    private String phoneNoKo;
    private String phoneNoEn;

    private String latitude;
    private String longitude;

    public String getRegionNameKo() {
        try {
            String[] parts = this.regionNameKo.split("::");
            return parts[0];
        } catch(Exception e) {
            return "";
        }
    }

    public String getRegionNameEn() {
        try {
            String[] parts = this.regionNameKo.split("::");
            return parts[1];
        } catch(Exception e) {
            return "";
        }
    }

    public String getClassificationName() {
        if("DOMESTIC".equals(this.classification))
            return "국내";
        if("OVERSEAS".equals(this.classification))
            return "해외";
        return this.classification;
    }

    /**
     * 노출/비노출 뱃지
     */
    private String isOpenBadge;
    public String getIsOpenBadge() {
        if("Y".equals(this.isOpen))
            return "<span class=\"badge py-2 px-3 fs-7 badge-light-primary\">노출</span>";
        return "<span class=\"badge py-2 px-3 fs-7 badge-light\">비노출</span>";
    }

    /**
     * 지역
     */
    public enum Region {
        SEOUL("서울특별시", "Seoul"),
        SEJONG("세종", "Sejong"),
        DAEJEON("대전", "Daejeon"),
        DAEGU("대구", "Daegu"),
        ULSAN("울산", "Ulsan"),
        BUSAN("부산", "Busan"),
        INCHEON("인천", "Incheon"),
        GWANGJU("광주", "Gwangju"),
        GYEONGGI_DO("경기도", "Gyeonggi-do"),
        CHUNGCHEONGBUK_DO("충청북도", "Chungcheongbuk-do"),
        CHUNGCHEONGNAM_DO("충청남도", "Chungcheongnam-do"),
        GYEONGSANGBUK_DO("경상북도", "Gyeongsangbuk-do"),
        GYEONGSANGNAM_DO("경상남도", "Gyeongsangnam-do"),
        JEOLLABUK_DO("전라북도", "Jeollabuk-do"),
        JEOLLANAM_DO("전라남도", "Jeollanam-do"),
        GANGWON_DO("강원도", "Gangwon-do"),
        JEJU("제주특별자치도", "Jeju"),
        ASIA("아시아", "Asia"),
        EUROPE("유럽", "Europe"),
        SOUTH_AMERICA("남아메리카", "South America"),
        NORTH_AMERICA("북아메리카", "North America"),
        OCEANIA("오세아니아", "Oceania"),
        AFRICA("아프리카", "Africa"),
        ETC("기타", "etc.");

        private final String ko;
        private final String en;

        Region(String ko, String en) {
            this.ko = ko;
            this.en = en;
        }

        public String getKo() {
            return this.ko;
        }

        public String getEn() {
            return this.en;
        }
    }

}

