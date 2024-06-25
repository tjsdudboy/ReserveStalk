package InventoryBox.reserveIn;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {
    public static void main(String[] args) {
        // 32 바이트 (256 비트) 비밀키 생성
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[32];
        random.nextBytes(key);
        String base64Key = Base64.getEncoder().encodeToString(key);
        System.out.println(base64Key);
    }
}