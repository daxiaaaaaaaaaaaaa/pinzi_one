package com.jilian.pinzi.common.dto;

import java.io.Serializable;

public class AccessTokenDto implements Serializable {
    private String access_token;//":"ACCESS_TOKEN",
    private String expires_in;//":;//7200,
    private String refresh_toke;//;//n":"REFRESH_TOKEN",
    private String openid;//":"OPE;//NID",
    private String scope;//":"SCOP;//E",
    private String unionid;//":"o6;//_bmasdasdsad6_2sgVt7hMZOPfL"

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_toke() {
        return refresh_toke;
    }

    public void setRefresh_toke(String refresh_toke) {
        this.refresh_toke = refresh_toke;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
