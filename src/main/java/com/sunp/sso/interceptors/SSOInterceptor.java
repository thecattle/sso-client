package com.sunp.sso.interceptors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunp.sso.model.UserInfo;
import com.sunp.sso.session.LocalSessionManager;
import com.sunp.sso.utils.Base64Utils;
import com.sunp.sso.utils.HttpUtils;
import com.sunp.sso.utils.UrlUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: sunpeng
 * Date: 2017/9/13
 * Time: 22:49
 * Describe:
 */
public class SSOInterceptor implements HandlerInterceptor {
    private String serverUrl = "http://server.sso.com:8082/";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI();
        HttpSession session = request.getSession();
        Object auth = LocalSessionManager.getSession(session.getId()).getAttribute("token_info");
        String token = request.getParameter("token");

        //loginOut 不拦截
        if (request.getRequestURI().contains("loginOut") || request.getRequestURI().contains("toLoginOut")) {
            return true;
        }
        String userName = UrlUtils.getUserNameByCookies(request.getCookies());

        //说明当前用户为登录状态
        if (auth != null) {
            return true;
        }

        //说明是sso服务器调用的，稍后可能会改成别的判断
        if (token != null) {
            Map<String, Object> param = new HashMap<>();
            param.put("token", token);
            param.put("appUrl", request.getServerName() + ":" + request.getServerPort());
            String result = HttpUtils.httpPostRequest(serverUrl + "user/checkToken", param);
            if (resultHandle(result, request, response)){
                return true;
            }
        }

        //说明当前用户 有 cookie，可能认证中心已经登录了
        if (auth == null && userName != null) {
            //判断sso服务器是否已经登录了
            Map<String, Object> params = new HashMap<>();
            params.put("appSessionId", session.getId());
            params.put("appUrl", request.getServerName() + ":" + request.getServerPort());
            params.put("username", userName);
            String result1 = HttpUtils.httpPostRequest(serverUrl + "user/checkLogin", params);
            if (resultHandle(result1, request, response)){
                return true;
            }
        }

        response.sendRedirect(serverUrl + "?back=" + UrlUtils.encodeUrlWithSessionId(url, session.getId()));
        return false;
    }

    private boolean resultHandle(String result, HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonResult = JSONObject.parseObject(result);
        if (jsonResult.getBoolean("success")) {
            UserInfo info = JSONObject.parseObject(jsonResult.getString("data"), UserInfo.class);
            HttpSession sessionLocal = LocalSessionManager.getSession(info.getLocalSessionId());
            if (sessionLocal != null) {
                Cookie cookie = new Cookie("_token_security", UrlUtils.encodeUrl(Base64Utils.encodeBase64(info.getUserName())));
                cookie.setPath("/");
                cookie.setDomain(".sso.com");
                response.addCookie(cookie);
                System.out.println("url:" + request.getServerName());
                sessionLocal.setAttribute("token_info", info);
                return true;
            }
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("afterCompletion");
    }
}
