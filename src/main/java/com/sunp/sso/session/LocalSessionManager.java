package com.sunp.sso.session;


import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: sunpeng
 * Date: 2017/9/13
 * Time: 23:04
 * Describe:存放所有局部会话（当前应用会话）
 */
public class LocalSessionManager {

    private static Map<String, HttpSession> sessions = new HashMap<>();

    public static void addSession(String sessionId, HttpSession session) {
        sessions.put(sessionId, session);
    }

    public static void delSession(String sessionId) {
        sessions.remove(sessionId);
    }

    public static HttpSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }
}
