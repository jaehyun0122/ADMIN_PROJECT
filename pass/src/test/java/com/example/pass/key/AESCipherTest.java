package com.example.pass.key;

import org.apache.commons.codec.binary.Base64;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static org.junit.jupiter.api.Assertions.*;

class AESCipherTest {
    static class AESCipher{
        // 알고리즘/모드/패딩
        private static final String algorithm = "AES/CBC/PKCS5Padding";
        // 암호화 키
        private SecretKey secretKey;
        // 초기화 벡터
        private IvParameterSpec iv;
        // 문자인코딩 방식
        private final String charset = "UTF-8";

        public AESCipher(String aesKey) {
            if (aesKey == null) {
                throw new com.example.pass.key.AESCipher.NoSecretKeyException("No SecretKey, Please Set SecretKey!!!");
            }

            if (aesKey.length() > 16) {
                this.iv = new IvParameterSpec(aesKey.substring(0, 16).getBytes());
            } else {
                this.iv = new IvParameterSpec(aesKey.getBytes());
            }

            this.secretKey = new SecretKeySpec(aesKey.getBytes(), "AES");
        }

        // 암호화
        public String encrypt(String str) throws Exception {
            Cipher c = Cipher.getInstance(algorithm);
            c.init(Cipher.ENCRYPT_MODE, this.secretKey, this.iv);
            return new String(Base64.encodeBase64(c.doFinal(str.getBytes(charset))));
        }

        // 복호화
        public String decrypt(String str) throws Exception {
            Cipher c = Cipher.getInstance(algorithm);
            c.init(Cipher.DECRYPT_MODE, this.secretKey, this.iv);
            return new String(c.doFinal(Base64.decodeBase64(str.getBytes())), charset);
        }

        class NoSecretKeyException extends RuntimeException {
            public NoSecretKeyException(String msg) {
                super(msg);
            }
        }
    }

    @Test
    void aesTest() throws Exception {
        com.example.pass.key.AESCipher aesCipher = new com.example.pass.key.AESCipher(
                "YzNmOGQ2OGI1ZDEwNDA5YmJmZmRhMTI5");

        String name = aesCipher.encrypt("정재현");
        String phone = aesCipher.encrypt("01093689836");
        String birthday = aesCipher.encrypt("950122");
        String gender = aesCipher.encrypt("1");

        System.out.println(name + " "+phone +" "+birthday+" "+gender);

        Assertions.assertThat(aesCipher.decrypt(name)).isEqualTo("정재현");
        Assertions.assertThat(aesCipher.decrypt(phone)).isEqualTo("01093689836");
        Assertions.assertThat(aesCipher.decrypt(birthday)).isEqualTo("950122");
        Assertions.assertThat(aesCipher.decrypt(gender)).isEqualTo("1");
    }
}