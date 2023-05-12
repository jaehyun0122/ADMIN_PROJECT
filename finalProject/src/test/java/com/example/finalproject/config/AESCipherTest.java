package com.example.finalproject.config;

import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class AESCipherTest {

    @Test
    void encryptTest() throws Exception {
        String symmetricKey = "test"; // 대칭키
        String data = "kakao"; // 암호화할 데이터

        // 대칭키를 32바이트로 확장
        byte[] key = Arrays.copyOf(MessageDigest.getInstance("SHA-256").digest(symmetricKey.getBytes(StandardCharsets.UTF_8)), 32);

        // AES 암호화 설정 및 초기화
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        // 데이터 암호화
        byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

        // Base64 인코딩 결과를 16진수 문자열로 변환
        String base64EncodedValue = Base64.getEncoder().encodeToString(encryptedData);
        StringBuilder hexString = new StringBuilder();
        for (byte b : base64EncodedValue.getBytes()) {
            hexString.append(String.format("%02X", b));
        }

        // 32자리의 문자열로 출력
        String encodedValue = hexString.toString().substring(0, 32);
        System.out.println("Encoded Value: " + encodedValue);
        System.out.println("Encoded length: " + encodedValue.length());
    }
// 6855456A4D794C485561454E6C687455
    @Test
    void decryptTest() throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        String symmetricKey = "test"; // 대칭키
        String encodedValue = "6855456A4D794C485561454E6C687455"; // 복호화할 암호화된 데이터

        // 대칭키를 32바이트로 확장
        byte[] key = Arrays.copyOf(MessageDigest.getInstance("SHA-256").digest(symmetricKey.getBytes(StandardCharsets.UTF_8)), 32);

        // Base64 인코딩된 16진수 문자열을 원래의 Base64 인코딩 결과로 변환
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < encodedValue.length(); i += 2) {
            String hex = encodedValue.substring(i, i + 2);
            hexString.append((char) Integer.parseInt(hex, 16));
        }
        String base64EncodedValue = new String(Base64.getDecoder().decode(hexString.toString()), StandardCharsets.UTF_8);

        // AES 복호화 설정 및 초기화
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        // 데이터 복호화
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(base64EncodedValue));

        // 복호화된 데이터 출력
        String decryptedValue = new String(decryptedData, StandardCharsets.UTF_8);
        System.out.println("Decrypted Value: " + decryptedValue);
    }

}