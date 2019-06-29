package com.jilian.pinzi.common.vo;

import com.jilian.pinzi.base.BaseVo;

public class AccesstokenVo extends BaseVo {
    private String appid;//	是	应用唯一标识，在微信开放平台提交应用审核通过后获得
    private String secret;//	是	应用密钥AppSecret，在微信开放平台提交应用审核通过后获得
    private String code	;//是	填写第一步获取的code参数
    private String grant_type;//	是	填authorization_code

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
