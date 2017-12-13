package com.publiccms.improve.api;

import java.io.Serializable;

public class OauthInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;
    private String accessToken;
    private String openId;

    public OauthInfo(String code, String accessToken) {
        this.code = code;
        this.accessToken = accessToken;
    }

    public OauthInfo(String code, String accessToken, String openId) {
        this.code = code;
        this.accessToken = accessToken;
        this.openId = openId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
