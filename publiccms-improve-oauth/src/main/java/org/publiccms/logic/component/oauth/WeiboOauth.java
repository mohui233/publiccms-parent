package org.publiccms.logic.component.oauth;

import static com.publiccms.common.tools.CommonUtils.notEmpty;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.publiccms.improve.api.OauthInfo;
import com.publiccms.improve.api.UserInfo;
import com.publiccms.improve.base.AbstractOauth;

/**
 *
 * WeiboOauth
 * 
 */
public class WeiboOauth extends AbstractOauth {

    public WeiboOauth(String appkey, String appSecret, String returnUrl) {
        super("weibo", appkey, appSecret, returnUrl);
    }

    /*
     * http://open.weibo.com/wiki/Oauth2/authorize
     */
    public String getAuthorizeUrl(String state, boolean mobile) {
        StringBuilder sb = new StringBuilder("https://api.weibo.com/oauth2/authorize?client_id=");
        sb.append(appKey).append("&redirect_uri=").append(returnUrl).append("&scope=email&state=").append(state);
        if (mobile) {
            sb.append("&display=mobile");
        }
        return sb.toString();
    }

    /**
     * http://open.weibo.com/wiki/Oauth2/access_token
     */
    @Override
    public OauthInfo getAccessToken(String code) throws ClientProtocolException, IOException {
        if (notEmpty(code)) {
            Map<String, String> paramters = new HashMap<>();
            paramters.put("client_id", appKey);
            paramters.put("client_secret", appSecret);
            paramters.put("grant_type", "authorization_code");
            paramters.put("redirect_uri", returnUrl);
            paramters.put("code", code);
            String html = post("https://api.weibo.com/oauth2/access_token", paramters);
            if (notEmpty(html)) {
                Map<String, Object> map = objectMapper.readValue(html, new TypeReference<Map<String, Object>>() {
                });
                return new OauthInfo(code, (String) map.get("access_token"), String.valueOf((Integer) map.get("uid")));
            }
        }
        return null;
    }

    /*
     * http://open.weibo.com/wiki/2/users/show
     */
    public UserInfo getUserInfo(OauthInfo oauthInfo) throws ClientProtocolException, IOException {
        if (notEmpty(oauthInfo)) {
            StringBuilder sb = new StringBuilder("https://api.weibo.com/2/users/show.json?access_token=");
            sb.append(oauthInfo.getAccessToken()).append("&uid=").append(oauthInfo.getOpenId());
            String html = get(sb.toString());
            Map<String, Object> map = objectMapper.readValue(html, new TypeReference<Map<String, Object>>() {
            });
            if (notEmpty(map.get("id"))) {
                return new UserInfo(oauthInfo.getOpenId(), (String) map.get("screen_name"), (String) map.get("avatar_large"),
                        (String) map.get("gender"));
            }
        }
        return null;
    }
}
