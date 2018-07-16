package suncere.androidapp.lib.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Hjo on 2017/4/6.
 */
public class MD5Utils {

    /**
     * 将key值转为MD5
     *
     * @param key
     * @return
     */
    public static String String2MD5(String key) { //避免包含特殊字符时，文明命名不合法
        String CacheString = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(key.getBytes());
            CacheString = bytes2HexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            CacheString = String.valueOf(key.hashCode());
            e.printStackTrace();
        }
        return CacheString;
    }

    private static String bytes2HexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();

    }
}
