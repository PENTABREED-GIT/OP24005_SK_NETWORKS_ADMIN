package com.skn.admin.site.dto.adminprivilege.response;

import com.skn.admin.site.dto.AdminMenu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMenuDto {
    private String uid;
    private Integer menuIndex;
    private String menuName;
    private Integer parentIndex;
    private Integer depth;
    private String menuCode;
    private Integer sortOrder;
    private String url;
    private Integer rootIndex;
    private String isDisplay;
    private String urlString;
    private String description;
    private String icon;
    private String isCheck;
    private List<ResponseMenuDto> children;

    public static ResponseMenuDto toDto(AdminMenu menu) {
        ResponseMenuDto dto = new ResponseMenuDto(
            menu.getUid(),
            menu.getMenuIndex(),
            menu.getMenuName(),
            menu.getParentIndex(),
            menu.getDepth(),
            menu.getMenuCode(),
            menu.getSortOrder(),
            menu.getUrl(),
            menu.getRootIndex(),
            menu.getIsDisplay(),
            menu.getUrlString(),
            menu.getDescription(),
            menu.getIcon(),
            menu.getIsCheck(),
            null
        );

        List<AdminMenu> children = menu.getChildren();
        if (children != null && !children.isEmpty()) {
            List<ResponseMenuDto> childDtoList = new ArrayList<>();
            for (AdminMenu childMenu : children) {
                ResponseMenuDto childDto = toDto(childMenu); // 재귀적으로 자식 메뉴를 변환
                childDtoList.add(childDto);
            }
            dto.setChildren(childDtoList);
        }

        return dto;
    }

    public static List<ResponseMenuDto> toDtoList(List<AdminMenu> menuList) {
        return menuList.stream()
            .map(ResponseMenuDto::toDto)
            .collect(Collectors.toList());
    }
}
