package com.skn.admin.util;

import com.skn.admin.common.ApplicationProperty;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class NTCryptoUtil {
    private static final String IV = ApplicationProperty.CRYPTO_IV;
    private static final String KEY_CARD = ApplicationProperty.CRYPTO_KEY_CARD;
    public static final String KEY_EMAIL = ApplicationProperty.CRYPTO_KEY_EMAIL;
    private static final String KEY_PHONE = ApplicationProperty.CRYPTO_KEY_PHONE;
    private static final String KEY_ETC = ApplicationProperty.CRYPTO_KEY_ETC;

    /**
     * 암호화 함수
     * @param data 암호화할 원본 문자열
     * @param type 키 종류 CARD / EMAIL / PHONE / ETC
     * @return
     */
    public static String encrypt(String data, String type) {
        if(NTUtil.isEmpty(data)) return "";

        String tKey = getKeyValue(type);

        SecretKeySpec skeySpec = new SecretKeySpec(tKey.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher;
        String result = "";
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));

            byte[] encrypted;
            encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            result = new String(Base64.encodeBase64(encrypted), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String eData = result;

        return eData;
    }

    /**
     * 복호화 함수
     * @param data 복호화할 암호화된 문자열
     * @param type 키 종류 CARD / EMAIL / PHONE / ETC
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String decrypt(String data, String type) {
        if(NTUtil.isEmpty(data)) return "";

        String tKey = getKeyValue(type);

        SecretKeySpec skeySpec = new SecretKeySpec(tKey.getBytes(StandardCharsets.UTF_8), "AES");
        String originalString = "";

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            byte[] byteStr = Base64.decodeBase64(data.getBytes(StandardCharsets.UTF_8));

            // Decrypted
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));

            byte[] original = cipher.doFinal(byteStr);
            originalString = new String(original, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return originalString;
    }

    private static String getKeyValue(String type) {
        String result = "";

        type = NTUtil.isNull(type, "");
        if (type.equals("CARD")) {
            result = KEY_CARD;
        } else if (type.equals("EMAIL")) {
            result = KEY_EMAIL;
        } else if (type.equals("PHONE")) {
            result = KEY_PHONE;
        } else if (type.equals("PASSWORD")) {
            result = IV;
        } else {
            result = KEY_ETC;
        }

        return result;
    }
}
