package com.sunp.sso.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by IntelliJ IDEA.
 * User: sunpeng
 * Date: 2017/9/15
 * Time: 22:29
 * Describe:
 */
public class UrlUtils {
    private static Logger logger= LoggerFactory.getLogger(UrlUtils.class);
    /**
     * 解码
     * @param url
     * @return
     */
    public static String decodeUrl(String url){
        try {
            return URLDecoder.decode(url,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("解码url失败",e);
        }
        return null;
    }

    /**
     * 编码
     * @param url
     * @return
     */
    public static String encodeUrl(String url){
        try {
            return URLEncoder.encode(url,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("编码url失败",e);
        }
        return null;
    }

    /**
     * 编码并且拼接 sessionId
     * @param url
     * @param sessionId
     * @return
     */
    public static String encodeUrlWithSessionId(String url,String sessionId){
        try {
            return URLEncoder.encode(url,"UTF-8")+"&"+sessionId;
        } catch (UnsupportedEncodingException e) {
            logger.error("编码url并且拼接sessionId失败",e);
        }
        return null;
    }

    /**
     * 获取用户名
     * @param cookies
     * @return
     */
    public static String getUserNameByCookies(Cookie[] cookies){
        if (cookies!=null){
            for (Cookie c:cookies) {
                if ("_token_security".equals(c.getName())){
                    return c.getValue();
                }
            }
        }
        return null;
    }
}
