package cn.iocoder.yudao.module.system.util.encryption;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

public class Sha1Utils {

    /**
     * 获取随机字符串
     * @param input
     * @return
     */
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 16;

    public static String generateRandomString() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }


    /**
     * 对字符串进行SHA-1加密
     * @param input 原文
     * @return SHA-1摘要（40位十六进制字符串）
     */
    public static String sha1(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] bytes = md.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("SHA-1加密失败", e);
        }
    }

    public static void main(String[] args) {
        String text = "hello world";
        String sha1 = sha1(text);
        System.out.println("SHA-1: " + sha1);
    }

}
