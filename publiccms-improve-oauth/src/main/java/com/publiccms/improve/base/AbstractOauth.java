package com.publiccms.improve.base;

import static org.apache.commons.logging.LogFactory.getLog;
import static org.apache.http.util.EntityUtils.consume;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.publiccms.common.base.Base;
import com.publiccms.improve.api.Oauth;
import com.publiccms.improve.api.OauthInfo;
import com.publiccms.improve.api.UserInfo;

public abstract class AbstractOauth implements Oauth, Base {
    protected static final CloseableHttpClient httpclient = HttpClients.createDefault();
    protected final Log log = getLog(getClass());
    protected String channel;
    protected String appKey;
    protected String appSecret;
    protected String returnUrl;

    public AbstractOauth(String channel, String appKey, String appSecret, String returnUrl) {
        this.channel = channel;
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.returnUrl = returnUrl;
    }

    protected String get(String url) throws ClientProtocolException, IOException {
        String html = null;
        HttpUriRequest request = new HttpGet(url);
        try (CloseableHttpResponse response = httpclient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                html = EntityUtils.toString(entity, DEFAULT_CHARSET);
                consume(entity);
            }
        }
        return html;
    }

    protected String post(String url, Map<String, String> paramters) throws ClientProtocolException, IOException {
        String html = null;
        HttpPost request = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<>();
        for (Entry<String, String> entry : paramters.entrySet()) {
            nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        request.setEntity(new UrlEncodedFormEntity(nvps, DEFAULT_CHARSET));
        try (CloseableHttpResponse response = httpclient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                html = EntityUtils.toString(entity, DEFAULT_CHARSET);
                consume(entity);
            }
        }
        return html;
    }

    @Override
    public String getAuthorizeUrl(String state) {
        return getAuthorizeUrl(state, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.publiccms.improve.api.Oauth#getOpenId(com.publiccms.improve.api.
     * OauthInfo)
     */
    @Override
    public OauthInfo getOpenId(OauthInfo oauthInfo) throws ClientProtocolException, IOException {
        return oauthInfo;
    }

    @Override
    public UserInfo getUserInfo(String code) throws ClientProtocolException, IOException {
        return getUserInfo(getOpenId(getAccessToken(code)));
    }
}
