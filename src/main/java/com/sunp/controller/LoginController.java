package com.sunp.controller;

import com.alibaba.fastjson.JSONObject;
import com.sunp.service.UserService;
import com.sunp.sso.session.LocalSessionManager;
import com.sunp.sso.utils.HttpUtils;
import com.sunp.sso.utils.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: sunpeng
 * Date: 2017/9/3
 * Time: 17:21
 * Describe: 用户数据 dao 接口实现
 */

@Controller
@RequestMapping(value = "/user")
public class LoginController {

    private String serverUrl = "http://server.sso.com:8082/";

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getUserList(HttpServletResponse response) {
        logger.info("user/getUserList init");
        Map<String, Object> map = new HashMap<>();
        map.put("users", userService.getUserList());
        return map;
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> loginOut(HttpServletRequest request) {
        logger.info("user/loginOut init");
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);

        String username = UrlUtils.getUserNameByCookies(request.getCookies());
        Map<String, Object> param = new HashMap<>();
        param.put("username", username);
        String result = HttpUtils.httpPostRequest(serverUrl + "user/loginOut", param);
        JSONObject resultLogin = JSONObject.parseObject(result);
        if (resultLogin.getBoolean("success")) {
            map.put("success", true);
        }
        return map;
    }

    @RequestMapping(value = "/toLoginOut", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> toLoginOut(String sessionId) {
        logger.info("user/toLoginOut init");
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);

        try {
            LocalSessionManager.getSession(sessionId).setAttribute("token_info",null);
            map.put("success", true);
        } catch (Exception e) {
            logger.error("toLoginOut fail",e);
            return map;
        }
        return map;
    }
}
