package com.skn.admin.site.dto;

import com.skn.admin.base.dto.BaseDTO;
import com.skn.admin.site.dto.menu.request.MenuRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.List;

/**
 * 관리자 메뉴 객체
 * <pre>
 *  - 데이터베이스의 VW_ADMIN_MENU 와 연결
 * </pre>
 * @author  jinstech
 * @since   2024.09.11
 * @version 0.0.1 at 2024.09.11
 * @see     AdminGroup
 */
@Getter
@NoArgsConstructor
public class AdminMenu extends BaseDTO {
    /**
     * 메뉴 인덱스
     */
    private Integer menuIndex;

    /**
     * 메뉴명
     */
    private String menuName;

    /**
     * 부모 메뉴 인덱스
     */
    private Integer parentIndex;

    /**
     * 메뉴 단계 (1, 2, 3 Depth)
     */
    private Integer depth;

    /**
     * 메뉴 코드
     */
    private String menuCode;

    /**
     * 정렬 순서
     * <pre>
     * - DB Field : ADMIN_MENU.SORT_ORDER
     * </pre>
     */
    private Integer sortOrder;

    /**
     * 메뉴 기본 URL
     * <pre>
     * - DB Field : ADMIN_MENU.URL
     * </pre>
     */
    private String url;

    /**
     * 최상위 부모 인덱스
     * <pre>
     * - DB Field : ADMIN_MENU.ROOT_INDEX
     * </pre>
     */
    private Integer rootIndex;

    /**
     * 메뉴 위치 (링크 포함)
     * <pre>
     * - DB Field : VW_ADMIN_MENU.LOCATION
     * </pre>
     */
    private String location;

    /**
     * 메뉴 위치 (링크 불포함) - breadcrumbs
     * <pre>
     * - DB Field : VW_ADMIN_MENU.LOCATION2
     * </pre>
     */
    private String location2;

    /**
     * 표시 여부
     * <pre>
     * - DB Field : TB_ADMIN_MENU.IS_DISPLAY
     * </pre>
     */
    private String isDisplay;

    /**
     * 메뉴에 포함된 URL 문자열
     * <pre>
     * - DB Field : TB_ADMIN_MENU.URL_STRING
     * </pre>
     */
    private String urlString;

    /**
     * 메뉴 설명
     * <pre>
     * - DB Field : TB_ADMIN_MENU.DESCRIPTION
     * </pre>
     */
    private String description;

    /**
     * 메뉴 아이콘
     * <pre>
     * - DB Field : TB_ADMIN_MENU.ICON
     * </pre>
     */
    private String icon;
    private String isCheck;
    private List<AdminMenu> children;

    public AdminMenu(MenuRequest.Insert insert) {
        this.uid = RandomStringUtils.randomAlphanumeric(16);
        this.menuName = insert.getMenuName();
        this.parentIndex = insert.getParentIndex();
        this.depth = insert.getDepth();
        this.menuCode = insert.getMenuCode();
        this.sortOrder = insert.getSortOrder();
        this.url = insert.getUrl();
        this.rootIndex = insert.getRootIndex();
        this.isDisplay = insert.getIsDisplay();
        this.urlString = insert.getUrlString();
        this.description = insert.getDescription();
        this.icon = insert.getIcon();
        this.adminId = insert.getAdminId();
        this.adminIndex = insert.getAdminIndex();
    }

    public AdminMenu(MenuRequest.Update update, String uid) {
        this.uid = uid;
        this.menuName = update.getMenuName();
        this.parentIndex = update.getParentIndex();
        this.depth = update.getDepth();
        this.menuCode = update.getMenuCode();
        this.sortOrder = update.getSortOrder();
        this.url = update.getUrl();
        this.rootIndex = update.getRootIndex();
        this.isDisplay = update.getIsDisplay();
        this.urlString = update.getUrlString();
        this.description = update.getDescription();
        this.icon = update.getIcon();
        this.adminId = update.getAdminId();
        this.adminIndex = update.getAdminIndex();
    }

    public AdminMenu(
        String uid,
        Integer menuIndex,
        String menuName,
        Integer parentIndex,
        Integer depth,
        String menuCode,
        Integer sortOrder,
        String url,
        Integer rootIndex,
        String isDisplay,
        String urlString,
        String description,
        String icon,
        String isCheck,
        List<AdminMenu> children
    ) {
        this.uid = uid;
        this.menuIndex = menuIndex;
        this.menuName = menuName;
        this.parentIndex = parentIndex;
        this.depth = depth;
        this.menuCode = menuCode;
        this.sortOrder = sortOrder;
        this.url = url;
        this.rootIndex = rootIndex;
        this.isDisplay = isDisplay;
        this.urlString = urlString;
        this.description = description;
        this.icon = icon;
        this.isCheck = isCheck;
    }

    private static AdminMenu of (
        String uid,
        Integer menuIndex,
        String menuName,
        Integer parentIndex,
        Integer depth,
        String menuCode,
        Integer sortOrder,
        String url,
        Integer rootIndex,
        String isDisplay,
        String urlString,
        String description,
        String icon,
        String isCheck,
        List<AdminMenu> children

    ) {
        return new AdminMenu(
            uid,
            menuIndex,
            menuName,
            parentIndex,
            depth,
            menuCode,
            sortOrder,
            url,
            rootIndex,
            isDisplay,
            urlString,
            description,
            icon,
            isCheck,
            children
        );
    }

    public static AdminMenu toEmpty () {
        return AdminMenu.of(
            "",
            0,
            "",
            0,
            1,
            "",
            1,
            "",
            0,
            "",
            "",
            "",
            "",
            "",
            Collections.emptyList()
        );
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setRootIndex(Integer rootIndex) {
        this.rootIndex = rootIndex;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
