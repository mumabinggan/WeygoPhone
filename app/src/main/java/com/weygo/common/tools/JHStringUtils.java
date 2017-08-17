package com.weygo.common.tools;

import com.weygo.common.base.JHObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by muma on 2016/11/30.
 */

public class JHStringUtils extends JHObject {

    public static boolean isNullOrEmpty(String string) {
        return (string == null || string.equals("") || string.isEmpty() || string.length() == 0);
    }

    public static String safeString(String string) {
        if (isNullOrEmpty(string)) {
            return "";
        }
        else {
            return string;
        }
    }

    public static String md5(String str) {
        if (str == null) return null;
        StringBuffer hexValue = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            char[] charArray = str.toCharArray();
            byte[] byteArray = new byte[charArray.length];
            for (int i = 0; i < charArray.length; i++) {
                byteArray[i] = (byte) charArray[i];
            }
            byte[] md5Bytes = md5.digest(byteArray);
            hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexValue == null ? null : hexValue.toString();
    }
}
