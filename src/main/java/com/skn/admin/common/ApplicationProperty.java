package com.skn.admin.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperty {
    public static String CRYPTO_IV;
    public static String CRYPTO_KEY_EMAIL;
    public static String CRYPTO_KEY_PHONE;
    public static String CRYPTO_KEY_CARD;
    public static String CRYPTO_KEY_ETC;

    public static String USER_SITE_URL;
    public static String USER_UPLOAD_URL;

    @Value("${skn.crypto.iv}")
    public void setCryptoIv(String value) {
        CRYPTO_IV = value;
    }

    @Value("${skn.crypto.key.email}")
    public void setCryptoKeyEmail(String value) {
        CRYPTO_KEY_EMAIL = value;
    }

    @Value("${skn.crypto.key.phone}")
    public void setCryptoKeyPhoneno(String value) {
        CRYPTO_KEY_PHONE = value;
    }

    @Value("${skn.crypto.key.card}")
    public void setCryptoKeyCard(String value) {
        CRYPTO_KEY_CARD = value;
    }

    @Value("${skn.crypto.key.etc}")
    public void setCryptoKeyEtc(String value) {
        CRYPTO_KEY_ETC = value;
    }

    @Value("${skn.upload.url}")
    public void setUserUploadUrl(String value) {
        USER_UPLOAD_URL = value;
    }

}
