package com.example.pass.key;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
public class RsaDecrypt {
    private String ci;
    public RsaDecrypt(String ci){
        this.ci = ci;
    }

    public String ciDecryption() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        //이용기관의 공개키로 암호화한 CI 값 (CA 기관에서 전달)
        String encryptedCiByPublicKey = this.ci;
        //이용기관 등록시 발급한 개인키 파일 로드
        String fileName = "C:\\Users\\ATON\\Desktop\\미니프로젝트\\미니프로젝트\\테스트이용기관\\CI복호화키\\개발\\kmprikey.pem";
        Path filePath = Paths.get(fileName);
        String getPrivate = new String(Files.readAllBytes(filePath),
                Charset.defaultCharset());
        String privateKey = getPrivate.replace("-----BEGIN PRIVATE KEY-----",
                "").replaceAll("\\R", "").replace("-----END PRIVATE KEY-----",
                "");

        byte[] key = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        RSAPrivateKey pk = (RSAPrivateKey) kf.generatePrivate(keySpec);
        //알고리즘 및 패딩 설정
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, pk);
        byte[] decBytes =
                Base64.getDecoder().decode(encryptedCiByPublicKey.getBytes());
        //복호화 수행
        String decData = new String(cipher.doFinal(decBytes));
        //복호화된 개인키
//        System.out.println("복호화된 CI = " + decData);

        return decData;
    }
}
