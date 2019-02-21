package com.example.sqltest.util;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


/**
 * RSA加解密
 */
public class RSAUtil {
    private static final String KEY_ALGORITHM = "rsa";
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    // 公钥
    private static String PUBLIC_KEY;
    // 私钥
    private static String PRIVATE_KEY;

    public static String getPublicKey() {
        return PUBLIC_KEY;
    }

    public static void setPublicKey(String publicKey) {
        PUBLIC_KEY = publicKey;
    }

    public static String getPrivateKey() {
        return PRIVATE_KEY;
    }

    public static void setPrivateKey(String privateKey) {
        PRIVATE_KEY = privateKey;
    }

    private RSAUtil() {

    }

    private static final RSAUtil rsa = new RSAUtil();

    public static RSAUtil getInstance() {
        return rsa;
    }

    /**
     * 初始化密钥
     */
    public Map<String, String> initKey() {
        Map<String, String> map = new HashMap<String, String>();
        try {
            // 随机生成密钥对
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            // 按照指定字符串生成密钥对
            // SecureRandom secureRandom = new SecureRandom("我是字符串".getBytes());
            // keyPairGen.initialize(1024, secureRandom);

            keyPairGen.initialize(1024);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            PUBLIC_KEY = CoderUtil.encryptBASE64(publicKey.getEncoded());
//            PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCB/aF5+eAtsN4NZJikjc7XrG+KyUwOkNzZf4XYPDVGNXV7l+QL4lVB58VBZmAqj3YyCjKrjZ4u6q8BTVcKxgE7XVIbZ6br6H2RpT8qTRJaNiUYobH7hHtfdwb8TWuv0SKHaOapYID3Wx9fAhtXecz+3Ws2mNaWUdZCOSMwQ3k4QQIDAQAB";
            System.out.println("-------------------公钥-------------------");
            System.out.println(PUBLIC_KEY);

            // 私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            PRIVATE_KEY = CoderUtil.encryptBASE64(privateKey.getEncoded());
//            PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIH9oXn54C2w3g1kmKSNztesb4rJTA6Q3Nl/hdg8NUY1dXuX5AviVUHnxUFmYCqPdjIKMquNni7qrwFNVwrGATtdUhtnpuvofZGlPypNElo2JRihsfuEe193BvxNa6/RIodo5qlggPdbH18CG1d5zP7dazaY1pZR1kI5IzBDeThBAgMBAAECgYBVR4LPVfoXn6gd2zg1BH+FiK/ouiT2jV0GJ540fVNqCxpXCg/nKAZM5XPIcxR194ZQT9ZsK81x9C75yLZo7/vP+ULf/DY40rO/BNG7l06gqymV1Qu9z0gH6ICAXnGSzvvUkL8f/Ix8iB2gpxPKhKug8VJ8cztPRM67/fdMzX6wPQJBAMXvBOvq9ozYjU6a/EkOGUt0X9wXsS/0dLJOtEBJUYZYkvi2Rj3gVeCSoF8UD+RF1JnFcCY+s3VGeC7+FWOZlKMCQQCoIArTFK/69pKxjqQaztOtOUr3kp3EF2l4YPzr+cuHueMeu1FXFLsaF1LbI1ODdk4e4MCxDdjw8uLGa5wceOnLAkEAilMHtXd25edQmWZmeQeJVdn2Q/GqukC0bQfjoCmc965ex8FaRVPJsC/IM6npkTXb5bq9LX0UHRkqVGzstbZW0QJAdNv4yik5/PXAlxwvotaaOXB2VEkb0f33+I1pn9g8CTZC0MygyCeHuaX2mI0gcmMb/8h3B1o5rZkV2ZdYpk3BKwJBAMC1fzwr/JHVFH1jTEeBQQ0H+n8aeHD/YczsE6k0hX3SUulbkyv+hhRpvsPh+4aUd39GyaAwumctKxcPDIXbP1o=";
            System.out.println("-------------------私钥-------------------");
            System.out.println(PRIVATE_KEY);
            map.put("pubkey", PUBLIC_KEY);
            map.put("prikey", PRIVATE_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("初始化密钥异常" + e);
        }
        return map;
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param key
     */
    public String encryptByPublicKey(String data, String key) {
        try {
            // 取得公钥
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(CoderUtil.decryptBASE64(key));
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key publicKey = keyFactory.generatePublic(x509KeySpec);

            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] encryptedData = data.getBytes("utf-8");
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > 117) {
                    cache = cipher.doFinal(encryptedData, offSet, 117);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * 117;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return CoderUtil.encryptBASE64(decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("公钥加密异常!");
        }
        return null;
    }

    /**
     * 公钥解密
     *
     * @param data
     * @param key
     */
    public String decryptByPublicKey(String data, String key) {
        // 对密钥解密
        try {
            byte[] keyBytes = CoderUtil.decryptBASE64(key);

            // 取得公钥
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key publicKey = keyFactory.generatePublic(x509KeySpec);

            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);

            byte[] encryptedData = CoderUtil.decryptBASE64(data);

            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > 128) {
                    cache = cipher.doFinal(encryptedData, offSet, 128);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * 128;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();

            return new String(decryptedData, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("公钥解密异常");
        }
        return null;
    }

    /**
     * 私钥加密
     *
     * @param data
     * @param key
     */
    public String encryptByPrivateKey(String data, String key) {
        try {

            // 对密钥解密
            byte[] keyBytes = CoderUtil.decryptBASE64(key);

            // 取得私钥
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            byte[] encryptedData = data.getBytes("utf-8");
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > 117) {
                    cache = cipher.doFinal(encryptedData, offSet, 117);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * 117;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return CoderUtil.encryptBASE64(decryptedData);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("私钥加密异常");
        }
        return null;
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param key
     */
    public String decryptByPrivateKey(String data, String key) {
        try {
            // 对密钥解密
            byte[] keyBytes = CoderUtil.decryptBASE64(key);

            // 取得私钥
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] encryptedData = CoderUtil.decryptBASE64(data);

            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > 128) {
                    cache = cipher.doFinal(encryptedData, offSet, 128);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * 128;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();

            return new String(decryptedData, "utf-8");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("私钥解密异常");
        }
        return null;
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public String sign(byte[] data, String privateKey) throws Exception {
        // 解密由base64编码的私钥
        byte[] keyBytes = CoderUtil.decryptBASE64(privateKey);

        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);

        return CoderUtil.encryptBASE64(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public boolean verify(byte[] data, String publicKey, String sign) throws Exception {

        // 解密由base64编码的公钥
        byte[] keyBytes = CoderUtil.decryptBASE64(publicKey);

        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        // 验证签名是否正常
        return signature.verify(CoderUtil.decryptBASE64(sign));
    }


      public static void main(String args[]) {
        RSAUtil.getInstance().initKey();
        String str = "lyq123";
        System.out.println("公钥加密，私钥解密");
        String _tmp1 = RSAUtil.getInstance().encryptByPublicKey(str, PUBLIC_KEY);
        System.out.println("输出加密内容");
        System.out.println(_tmp1);
        System.out.println("输出解密内容");
        System.out.println(RSAUtil.getInstance().decryptByPrivateKey(_tmp1, PRIVATE_KEY));
    }


}
