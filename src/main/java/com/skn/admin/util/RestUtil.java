package com.skn.admin.util;

import com.google.gson.Gson;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Map;

/*
*  API 사용 코드
*  @author bestcure
* */

public class RestUtil {

    public static <T> T nCloudMailSendByPost(String domain, Map<String, String> sendMap, Class<T> returnType) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        String body = NTUtil.isNull(sendMap.get("mailContents"));

        if (body != null) {
            //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            headers.add("x-ncp-apigw-timestamp", sendMap.get("timestamp"));
            headers.add("x-ncp-iam-access-key", sendMap.get("accessKey"));
            headers.add("x-ncp-apigw-signature-v2", sendMap.get("apiSignature"));
            headers.add("x-ncp-lang", "ko-KR");

            HttpEntity entity = new HttpEntity(body, headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                    domain,
                    entity,
                    String.class);
            return new Gson().fromJson(responseEntity.getBody(), returnType);
        } else {
            return null;
        }
    }
}