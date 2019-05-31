package com.wj.api.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.UUID;

public class AesUtil {

    /**
     * AES加密【AES/ECB/PKCS5Padding】
     * @param content
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String password) throws Exception {
        byte[] bysKey = password.getBytes("UTF-8");
        SecretKeySpec key = new SecretKeySpec(bysKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] byteContent = content.getBytes("UTF-8");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(byteContent);
        return Base64.encodeBase64String(result);
    }

    /**
     * AES解密【AES/ECB/PKCS5Padding】
     *
     * @param content
     * @return
     * @throws Exception
     */
    public static String decrypt(String content, String password) throws Exception {
        byte[] bContent = Base64.decodeBase64(content);
        byte[] bysKey = password.getBytes("UTF-8");
        SecretKeySpec key = new SecretKeySpec(bysKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] result = cipher.doFinal(bContent);
        return new String(result, "UTF-8");
    }

    private static String createPassWord() {
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            hashCodeV = -hashCodeV;
        }
        return String.format("%016d", hashCodeV);
    }

    public static void main(String[] args) {
        try {
            String password = createPassWord();
            String encryptStr = encrypt("guang涛", password);
            System.out.println(encryptStr);
            System.out.println(decrypt(encryptStr, password));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}