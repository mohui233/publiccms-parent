package com.publiccms.improve.api;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String openId;
    private String nickname;
    private String avatar;
    private String gender;

    public UserInfo(String openId, String nickname, String avatar, String gender) {
        this.openId = openId;
        this.nickname = nickname;
        this.avatar = avatar;
        this.gender = gender;
    }
    
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
