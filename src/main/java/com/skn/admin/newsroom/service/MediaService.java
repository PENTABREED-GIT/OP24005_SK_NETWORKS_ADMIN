package com.skn.admin.newsroom.service;

import com.skn.admin.newsroom.dto.SocialMedia;
import com.skn.admin.newsroom.mapper.MediaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MediaService {
    private final MediaMapper mediaMapper;

    /**
     * PR > 소셜 미디어
     * @return
     */
    @Transactional
    @Cacheable(value="BLOG_FEED_LIST")
    public List<SocialMedia> getBlogFeedList() {
        List<SocialMedia> socialMediaList = new ArrayList<>();
        try {
            RssReader reader = RssReader.getInstance();
            reader.setURL(new URL("https://blog.sknetworks.co.kr/feed"));
            socialMediaList = reader.getFeedList();

            for (SocialMedia socialMedia : socialMediaList) {
                // 이미 존재하는지 체크 (중복 방지 로직)
                if (!isFeedAlreadyExists(socialMedia.getSocialMediaIndex())) {
                    mediaMapper.insertBlogFeed(socialMedia);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return socialMediaList;
    }

    // mediaService를 사용하는 예시
    private boolean isFeedAlreadyExists(String mediaIndex) {
        Map<String, Object> map = new HashMap<>();
        map.put("socialMediaIndex", mediaIndex);

        return !mediaMapper.selectMediaList(map).isEmpty();
    }

    // 오전 10시와 오후 4시에 실행되는 스케쥴러
    @Scheduled(cron = "0 0 10,16 * * ?", zone = "Asia/Seoul")
    public void updateBlogFeedScheduler() {
        System.out.println("Starting scheduled task to update blog feed");
        getBlogFeedList();
    }
}

