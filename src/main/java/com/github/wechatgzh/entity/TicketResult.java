package com.github.wechatgzh.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @author 13439
 */
@Data
public class TicketResult {

    @JSONField(name = "ticket")
    private String ticket;

    @JSONField(name = "expires_in")
    private String expiresIn;
}
