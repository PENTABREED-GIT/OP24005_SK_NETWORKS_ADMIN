package com.skn.admin.newsroom.dto;

import com.skn.admin.base.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SocialMedia extends BaseDTO {
    private String uid;
    private String socialMediaIndex;
    private String title;
    private String contents;
    private String category;
    private List<String> tagList;
    private String tags;
    private String tagString;
    private String thumbnailUrl;
    private String url;
    private String thumbnail;

}
