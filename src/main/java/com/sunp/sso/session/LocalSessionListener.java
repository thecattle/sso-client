package com.sunp.sso.session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by IntelliJ IDEA.
 * User: sunpeng
 * Date: 2017/9/13
 * Time: 23:04
 * Describe: 会话监听
 */
public class LocalSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        LocalSessionManager.addSession(httpSessionEvent.getSession().getId(), httpSessionEvent.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        LocalSessionManager.delSession(httpSessionEvent.getSession().getId());
    }
}
