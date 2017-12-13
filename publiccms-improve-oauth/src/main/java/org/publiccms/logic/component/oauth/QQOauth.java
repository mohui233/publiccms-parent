package org.publiccms.logic.component.oauth;

import static com.publiccms.common.tools.CommonUtils.notEmpty;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.publiccms.improve.api.OauthInfo;
import com.publiccms.improve.api.UserInfo;
import com.publiccms.improve.base.AbstractOauth;

/**
 *
 * QQOauth
 * 
 */
public class QQOauth extends AbstractOauth {

    public QQOauth(String appkey, String appSecret, String returnUrl) {
        super("qq", appkey, appSecret, returnUrl);
    }

    /*
     * http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%
     * E5%8F%96access_token
     */
    @Override
    public String getAuthorizeUrl(String state, boolean mobile) {
        StringBuilder sb = new StringBuilder("https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=");
        sb.append(appKey).append("&redirect_uri=").append(returnUrl).append("&scope=get_user_info").append("&state=")
                .append(state);
        if (mobile) {
            sb.append("&display=mobile");
        }
        return sb.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.publiccms.improve.api.Oauth#getAccessToken(java.lang.String)
     */
    @Override
    public OauthInfo getAccessToken(String code) throws ClientProtocolException, IOException {
        if (notEmpty(code)) {
            StringBuilder sb = new StringBuilder("https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&code=");
            sb.append(code).append("&client_id=").append(appKey).append("&client_secret=").append(appSecret)
                    .append("&redirect_uri=").append(returnUrl);
            String html = get(sb.toString());
            if (notEmpty(html)) {
                String[] values = html.split("&");
                for (String value : values) {
                    if (value.startsWith("access_token=")) {
                        return new OauthInfo(code, value.split("=")[1]);
                    }
                }
            }
        }
        return null;
    }

    /*
     * http://wiki.connect.qq.com/%E5%BC%80%E5%8F%91%E6%94%BB%E7%95%A5_server-
     * side
     */
    @Override
    public OauthInfo getOpenId(OauthInfo oauthInfo) throws ClientProtocolException, IOException {
        if (notEmpty(oauthInfo)) {
            StringBuilder sb = new StringBuilder("https://graph.qq.com/oauth2.0/me?");
            sb.append("access_token=" + oauthInfo.getAccessToken());
            String html = get(sb.toString());
            if (notEmpty(html)) {
                html = html.substring(html.indexOf("{"), html.indexOf("}") + 1);
                Map<String, String> map = objectMapper.readValue(html, new TypeReference<Map<String, String>>() {
                });
                oauthInfo.setOpenId(map.get("openid"));
                return oauthInfo;
            }
        }
        return null;
    }

    /*
     * http://wiki.connect.qq.com/get_user_info
     */
    @Override
    public UserInfo getUserInfo(OauthInfo oauthInfo) throws ClientProtocolException, IOException {
        if (notEmpty(oauthInfo)) {
            StringBuilder sb = new StringBuilder("https://graph.qq.com/user/get_user_info?access_token=");
            sb.append(oauthInfo.getAccessToken()).append("&oauth_consumer_key=").append(appKey).append("&openid=")
                    .append(oauthInfo.getOpenId()).append("&format=format");
            String html = get(sb.toString());
            if (notEmpty(html)) {
                Map<String, Object> map = objectMapper.readValue(html, new TypeReference<Map<String, Object>>() {
                });
                if (0 == (Integer) map.get("ret")) {
                    return new UserInfo(oauthInfo.getOpenId(), (String) map.get("nickname"), (String) map.get("figureurl_qq_2"),
                            "男".equals(map.get("gender")) ? "m" : "f");
                }
            }
        }
        return null;
    }
}
