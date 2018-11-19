package com.jtgroup.wechat.utils;

import java.security.MessageDigest;

public class PasswordUtil {
	 /**
     * @param password
     * @return
     * @Title: ThreeMD5
     */
    public static String ThreeMD5(String password) {
        for (int i = 0; i < 3; i++) {
            password = compute(password);
        }
        return password;
    }

    /**
     * MD5����.<br />
     *
     * @param inStr
     * @return
     */
    public static String compute(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {

            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];

        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }
   public static void main(String[] args) {
	System.out.println(ThreeMD5("123456"));
}
}
