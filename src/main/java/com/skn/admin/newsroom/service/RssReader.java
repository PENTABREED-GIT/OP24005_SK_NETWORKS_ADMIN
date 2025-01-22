package com.skn.admin.newsroom.service;

import com.skn.admin.newsroom.dto.SocialMedia;
import org.apache.commons.lang3.RandomStringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RssReader {

    private static RssReader instance = null;
    private URL rssURL;

    private RssReader() {}

    public static RssReader getInstance() {
        if (instance == null)
            instance = new RssReader();
        return instance;
    }

    public void setURL(URL url) {
        rssURL = url;
    }

    public List<SocialMedia> getFeedList() {
        List<SocialMedia> socList = new ArrayList<>();

        try {
            // RSS 피드 가져오기
            String rssFeedContent = fetchRssFeed(rssURL.toString());

            if (rssFeedContent != null) {
                // 피드 파싱
                parseRssFeed(rssFeedContent, socList);
            } else {
                System.out.println("Failed to fetch RSS feed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socList;
    }

    // HttpClient를 사용하여 RSS 피드를 가져오는 메서드
    private String fetchRssFeed(String url) throws Exception {
        String cookie = "CUPID=bbb4268d0b0994714cf14543e1d2b7b0";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36")
                .header("Cookie", cookie)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            System.out.println("HTTP error code: " + response.statusCode());
            return null;
        }
    }

    // RSS 피드를 파싱하는 메서드
    private void parseRssFeed(String xmlContent, List<SocialMedia> socList) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new java.io.ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8)));

            NodeList items = doc.getElementsByTagName("item");

            int itemCnt = 0;
            for (int ii = 0; ii < items.getLength(); ii++) {
                SocialMedia socialMedia = new SocialMedia();
                Element item = (Element) items.item(ii);
                // 기업소식은 제외
                if (!getValue(item, "category").equals("기업소식")) {
                    String description = getValue(item, "description");
                    String url = getValue(item, "link");
                    socialMedia.setUid(RandomStringUtils.randomAlphanumeric(16));
                    socialMedia.setTitle(getValue(item, "title"));
                    socialMedia.setUrl(url);
                    String identifier = extractIdentifierFromUrl(url);
                    socialMedia.setSocialMediaIndex(identifier);
                    socialMedia.setContents(description);
                    socialMedia.setThumbnail(extractImgSrc(description));
                    socialMedia.setCategory(getValue(item, "category"));
                    String tagString = String.join(",", getTagList(item));
                    socialMedia.setTagList(getTagList(item));
                    socialMedia.setTags(tagString);
                    socList.add(socialMedia);

                    itemCnt++;
                    if (itemCnt >= 20) break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // XML 요소의 값을 가져오는 메서드
    public String getValue(Element parent, String nodeName) {
        NodeList nodeList = parent.getElementsByTagName(nodeName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }

    // <category> 태그의 리스트를 반환하는 메서드
    public List<String> getTagList(Element parent) {
        NodeList category = parent.getElementsByTagName("category");
        List<String> tagList = new ArrayList<>();
        for (int i = 1; i < category.getLength(); i++) {
            tagList.add(category.item(i).getTextContent());
        }
        return tagList;
    }

    // <description>에서 <img> 태그의 src 속성만 추출하는 메서드
    private static String extractImgSrc(String description) {
        // <img> 태그 내에서 src 속성을 추출하는 정규 표현식
        String srcRegex = "<img[^>]*\\s+src=[\"']([^\"']+)[\"'][^>]*>";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(srcRegex);
        java.util.regex.Matcher matcher = pattern.matcher(description);

        if (matcher.find()) {
            return matcher.group(1);  // 첫 번째 그룹이 src 속성의 값 (URL)
        }
        return "";
    }

    private String extractIdentifierFromUrl(String url) {
        // URL에서 식별자를 추출하는 정규 표현식
        String regex = ".*/(\\d+)$"; // URL의 마지막 부분이 숫자인 패턴
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1); // 첫 번째 그룹(숫자 부분) 반환
        }
        return "";
    }
}
