package com.github.wechatgzh.config;
/**
 * 消息类型
 * @author 13439
 */
public enum TypeEnum {
    /**
     * 文本消息
     */
    TEXT("text"),
    /**
     * 图片消息
     */
    IMAGE("image"),
    /**
     * 语音消息
     */
    VOICE("voice"),
    /**
     * 视频消息
     */
    VIDEO("video"),
    /**
     * 小视频消息
     */
    SHORTVIDEO("shortvideo"),
    /**
     * 地理位置消息
     */
    LOCATION("location"),
    /**
     * 链接消息
     */
    LINK("link"),
    /**
     * 事件推送
     */
    EVENT("event"),
    /**
     * 事件推送
     */
    NEWS("news"),
    /**
     * 音乐消息
     */
    MUSIC("music"),
    /**
     * 事件推送
     */
    TRANSFER_CUSTOMER_SERVICE("transfer_customer_service");

    private final String type;

    TypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
