package com.example.sqltest.util;

import java.util.Base64;

/**
 * 编码util
 */
public class CoderUtil {

    /**
     * BASE64解码
     */
    public static final byte[] decryptBASE64(String key) throws Exception {
        return (Base64.getDecoder()).decode(key);
    }

    /**
     * BASE64编码
     */
    public static final String encryptBASE64(byte[] key) throws Exception {
        return (Base64.getEncoder()).encodeToString(key);
    }

    /**
     * byte数组转换成十六进制字符串
     */
    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 把16进制字符串转换成字节数组
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static int toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }


}
