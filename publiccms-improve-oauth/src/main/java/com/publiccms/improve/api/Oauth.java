package com.publiccms.improve.api;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public interface Oauth {
    public String getAuthorizeUrl(String state, boolean mobilde);

    public String getAuthorizeUrl(String state);

    public OauthInfo getOpenId(OauthInfo oauthInfo) throws ClientProtocolException, IOException;

    public UserInfo getUserInfo(OauthInfo oauthInfo) throws ClientProtocolException, IOException;

    public UserInfo getUserInfo(String code) throws ClientProtocolException, IOException;

    public OauthInfo getAccessToken(String code) throws ClientProtocolException, IOException;
}
