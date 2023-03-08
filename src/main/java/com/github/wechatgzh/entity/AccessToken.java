package com.github.wechatgzh.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @author 13439
 */
@Data
public class AccessToken {

    @JSONField(name = "url")
    private String url;

    @JSONField(name = "access_token")
    private String accessToken;

    @JSONField(name = "expires_in")
    private String expiresIn;
}
