package com.sunp;

import com.sunp.sso.utils.Base64Utils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by IntelliJ IDEA.
 * User: sunpeng
 * Date: 2017/9/15
 * Time: 22:04
 * Describe:
 */
public class test {
    public static void main(String[] args) {
        String url="http://www.cnblogs.com/ywlaker/p/6113927.html?id={'asd':true}&name='孙鹏'";
        String sessionId="JKJHGFVBNMKUYGFNVBNKKYGHBN";
        String back= encodeUrl(url)+""+sessionId;

        String[] strs=back.split("&");

        System.out.println(decodeUrl(strs[0]));
//        System.out.println(strs[1]);
        System.out.println(Base64Utils.encodeBase64("admin"));
        System.out.println(Base64Utils.AESEncode("2","admin"));
    }

    public static String decodeUrl(String url){
        try {
            return URLDecoder.decode(url,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encodeUrl(String url){
        try {
            return URLEncoder.encode(url,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
