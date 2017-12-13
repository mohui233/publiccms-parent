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
 * 
 * WechatOauthClient
 * 
 */
public class WechatOauth extends AbstractOauth {

    public WechatOauth(String appkey, String appSecret, String returnUrl) {
        super("wechat", appkey, appSecret, returnUrl);
    }

    /**
     * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN
     */
    public String getAuthorizeUrl(String state, boolean mobile) {
        StringBuilder urlBuilder = new StringBuilder("https://open.weixin.qq.com/connect/qrconnect?appid=");
        urlBuilder.append(appKey).append("&redirect_uri=").append(returnUrl)
                .append("&response_type=code&scope=snsapi_login&state=").append(state).append("#wechat_redirect");
        return urlBuilder.toString();
    }

    /*
     * 
     */
    @Override
    public OauthInfo getAccessToken(String code) throws ClientProtocolException, IOException {
        StringBuilder sb = new StringBuilder("https://api.weixin.qq.com/sns/oauth2/access_token?appid=");
        sb.append(appKey).append("&secret=").append(appSecret).append("&code=").append(code)
                .append("&grant_type=authorization_code");
        String html = get(sb.toString());
        Map<String, Object> map = objectMapper.readValue(html, new TypeReference<Map<String, Object>>() {
        });
        if (notEmpty(map.get("access_token"))) {
            return new OauthInfo(code, (String) map.get("access_token"), (String) map.get("openid"));
        }
        return null;
    }

    /* 
     * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316518&token=&lang=zh_CN
     */
    @Override
    public UserInfo getUserInfo(OauthInfo oauthInfo) throws ClientProtocolException, IOException {
        if (notEmpty(oauthInfo)) {
            StringBuilder sb = new StringBuilder("https://api.weixin.qq.com/sns/userinfo?access_token=");
            sb.append(oauthInfo.getAccessToken()).append("&openid=").append(oauthInfo.getOpenId());
            String html = get(sb.toString());
            if (notEmpty(html)) {
                Map<String, Object> map = objectMapper.readValue(html, new TypeReference<Map<String, Object>>() {
                });
                return new UserInfo(oauthInfo.getOpenId(), (String) map.get("nickname"), (String) map.get("headimgurl"),
                        (Integer) map.get("sex") == 1 ? "m" : "f");
            }
        }
        return null;
    }

}
