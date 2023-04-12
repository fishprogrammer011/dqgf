package com.yipin_server.yihuo.util;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jetbrains.annotations.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.Base64Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WeChatUtil {

    private static final String APPID = "wxaadc4404937a675c";
    private static final String SECRET = "43eeb434031156f2cd1fecf06ef8ae16";

    public static JSONObject getSessionKeyOrOpenId(String code) {
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> requestUrlParam = new HashMap<>();
        //小程序appId
        requestUrlParam.put("appid", APPID);
        //小程序secret
        requestUrlParam.put("secret", SECRET);
        //小程序端返回的code
        requestUrlParam.put("js_code", code);
        //默认参数
        requestUrlParam.put("grant_type", "authorization_code");
        //发送post请求读取调用微信接口获取openid用户唯一标识
        JSONObject jsonObject = JSONObject.fromObject(JSON.parseObject(HttpClientUtil.doPost(requestUrl, requestUrlParam)));
        System.out.println("登录的：-------------------" + jsonObject);
        return jsonObject;
    }

    static {
        //BouncyCastle是一个开源的加解密解决方案，主页在http://www.bouncycastle.org/
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * AES解密
     *
     * @param //密文，被加密的数据
     * @param //秘钥
     * @param //偏移量
     * @param //解密后的结果需要进行的编码
     * @return
     * @throws Exception
     */
    @Nullable
    public static String decrypt(String encryptedData, String session_key, String vi) throws Exception {
        //被加密的数据
        byte[] dataByte = Base64.decodeBase64(encryptedData);
        //加密秘钥
        byte[] keyByte = Base64.decodeBase64(session_key);
        //偏移量
        byte[] ivByte = Base64.decodeBase64(vi);
        try {
        // 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
//            int base = 16;
//            if (keyByte.length % base != 0) {
//                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
//                byte[] temp = new byte[groups * base];
//                Arrays.fill(temp, (byte) 0);
//                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
//                keyByte = temp;
//            }
            int base1 = 16;
            if (dataByte.length % base1 != 0) {
                int groups = dataByte.length / base1 + (dataByte.length % base1 != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base1];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(dataByte, 0, temp, 0, dataByte.length);
                dataByte = temp;
            }
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            System.out.println("1111111");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            System.out.println("2222222");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            System.out.println("3333333");
            parameters.init(new IvParameterSpec(ivByte));
            System.out.println("4444444");
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            System.out.println("5555555");
            byte[] resultByte = cipher.doFinal(dataByte);
            System.out.println("6666666");
            System.out.println("resultByte==================" + Arrays.toString(resultByte));
            if (null != resultByte && resultByte.length > 0) {
                System.out.println("777777");
                String result = new String(resultByte, "UTF-8");
                System.out.println("888888");
                return result;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAccessToken(){
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String, String> requestUrlParam = new HashMap<>();
        //小程序appId
        requestUrlParam.put("appid", APPID);
        //小程序secret
        requestUrlParam.put("secret", SECRET);
        //grant_type
        requestUrlParam.put("grant_type", "client_credential");
        //发送post请求读取调用微信接口获取openid用户唯一标识
        JSONObject jsonObject = JSONObject.fromObject(JSON.parseObject(HttpClientUtil.doGet(requestUrl, requestUrlParam)));
        System.out.println("登录的：-------------------" + jsonObject);
        System.out.println(jsonObject.getString("access_token"));
        return jsonObject.getString("access_token");
    }

    public static JSONObject getPhoneNUmber(String code,String accessToken){
        System.out.println("getPhoneNUmber----------"+accessToken);
        String requestUrl = "https://api.weixin.qq.com/wxa/business/getuserphonenumber";
        Map<String, String> requestUrlParam = new HashMap<>();
        //access_token
        requestUrlParam.put("access_token", accessToken);
        requestUrlParam.put("code",code);
        //发送post请求读取调用微信接口获取openid用户唯一标识
        JSONObject jsonObject = JSONObject.fromObject(JSON.parseObject(HttpClientUtil.doPost(requestUrl, requestUrlParam)));
        System.out.println("获取手机号：-------------------" + jsonObject);
        return jsonObject.getJSONObject("phone_info");
    }
}
