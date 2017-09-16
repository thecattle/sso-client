package com.sunp.sso.model;

/**
 * Created by IntelliJ IDEA.
 * User: sunpeng
 * Date: 2017/9/14
 * Time: 22:51
 * Describe:
 */
public class UserInfo {
    private String gloalSessionId;
    private String localSessionId;
    private String userName;
    private String appUrl;

    public UserInfo() {
    }

    public UserInfo(String gloalSessionId, String localSessionId, String userName, String appUrl) {
        this.gloalSessionId = gloalSessionId;
        this.localSessionId = localSessionId;
        this.userName = userName;
        this.appUrl = appUrl;
    }

    public String getGloalSessionId() {
        return gloalSessionId;
    }

    public void setGloalSessionId(String gloalSessionId) {
        this.gloalSessionId = gloalSessionId;
    }

    public String getLocalSessionId() {
        return localSessionId;
    }

    public void setLocalSessionId(String localSessionId) {
        this.localSessionId = localSessionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }
}
