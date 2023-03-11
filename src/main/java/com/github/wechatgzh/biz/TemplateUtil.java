package com.github.wechatgzh.biz;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 13439
 */
@Service
public class TemplateUtil {

    public String textMessage(String content, Document document) {
        Element rootElement = document.getRootElement();
        return "<xml>" +
                "<ToUserName><![CDATA[" + rootElement.element("FromUserName").getText() + "]]></ToUserName>" +
                "<FromUserName><![CDATA" + rootElement.element("ToUserName").getText() + "]></FromUserName>" +
                "<CreateTime>" + new Date() + "</CreateTime>" +
                "<MsgType><![CDATA[text]]></MsgType>" +
                "<Content><![CDATA[" + content + "]]></Content>" +
                "</xml>";
    }

    public String voiceMessage(Document document) {
        Element rootElement = document.getRootElement();
        return "<xml>" +
                "<ToUserName><![CDATA[" + rootElement.element("FromUserName").getText() + "]]></ToUserName>" +
                "<FromUserName><![CDATA" + rootElement.element("ToUserName").getText() + "]></FromUserName>" +
                "<CreateTime>" + new Date() + "</CreateTime>" +
                "<MsgType><![CDATA[voice]]></MsgType>" +
                "<Voice>" +
                "<MediaId><![CDATA[" + rootElement.element("MediaId").getText() + "]]></MediaId>" +
                "</Voice>" +
                "</xml>";
    }

    public String videoMessage(Document document) {
        Element rootElement = document.getRootElement();
        return "<xml>" +
                "<ToUserName><![CDATA[" + rootElement.element("FromUserName").getText() + "]]></ToUserName>" +
                "<FromUserName><![CDATA" + rootElement.element("ToUserName").getText() + "]></FromUserName>" +
                "<CreateTime>" + new Date() + "</CreateTime>" +
                "<MsgType><![CDATA[video]]></MsgType>" +
                "<Video>" +
                "<MediaId><![CDATA[" + rootElement.element("MediaId").getText() + "]]></MediaId>" +
                "<Title><![CDATA[title]]></Title>" +
                "<Description><![CDATA[description]]></Description>" +
                "</Video>" +
                "</xml>";
    }


    public String shortvideoMessage(Document document) {
        Element rootElement = document.getRootElement();
        return "<xml>" +
                "<ToUserName><![CDATA[" + rootElement.element("FromUserName").getText() + "]]></ToUserName>" +
                "<FromUserName><![CDATA" + rootElement.element("ToUserName").getText() + "]></FromUserName>" +
                "<CreateTime>" + new Date() + "</CreateTime>" +
                "<MsgType><![CDATA[shortvideo]]></MsgType>" +
                "<Video>" +
                "<MediaId><![CDATA[" + rootElement.element("MediaId").getText() + "]]></MediaId>" +
                "<Title><![CDATA[title]]></Title>" +
                "<Description><![CDATA[description]]></Description>" +
                "</Video>" +
                "</xml>";
    }
    public String imageMessage(Document document) {
        Element rootElement = document.getRootElement();
        return "<xml>" +
                "<ToUserName><![CDATA[" + rootElement.element("FromUserName").getText() + "]]></ToUserName>" +
                "<FromUserName><![CDATA" + rootElement.element("ToUserName").getText() + "]></FromUserName>" +
                "<CreateTime>" + new Date() + "</CreateTime>" +
                "<MsgType><![CDATA[image]]></MsgType>" +
                "<Image>" +
                "<MediaId><![CDATA[" + rootElement.element("MediaId").getText() + "]]></MediaId>" +
                "</Image>" +
                "</xml>";
    }

    public String locationMessage(Document document) {
        Element rootElement = document.getRootElement();
        return "<xml>" +
                "<ToUserName><![CDATA[" + rootElement.element("FromUserName").getText() + "]]></ToUserName>" +
                "<FromUserName><![CDATA" + rootElement.element("ToUserName").getText() + "]></FromUserName>" +
                "<CreateTime>" + new Date() + "</CreateTime>" +
                "<MsgType><![CDATA[location]]></MsgType>" +
                "<Location_X>" + rootElement.element("Location_X").getText() + "</Location_X>" +
                "<Location_Y>" + rootElement.element("Location_Y").getText() + "</Location_Y>" +
                "<Scale>" + rootElement.element("Scale").getText() + "</Scale>" +
                "<Label><![CDATA[" + rootElement.element("Label").getText() + "]]></Label>" +
                "</xml>";
    }

    public String linkMessage(Document document) {
        Element rootElement = document.getRootElement();
        return "<xml>" +
                "<ToUserName><![CDATA[" + rootElement.element("FromUserName").getText() + "]]></ToUserName>" +
                "<FromUserName><![CDATA" + rootElement.element("ToUserName").getText() + "]></FromUserName>" +
                "<CreateTime>" + new Date() + "</CreateTime>" +
                "<MsgType><![CDATA[link]]></MsgType>" +
                "<Title><![CDATA[" + rootElement.element("Title").getText() + "]]></Title>" +
                "<Description><![CDATA[" + rootElement.element("Description").getText() + "]]></Description>" +
                "<Url><![CDATA[" + rootElement.element("Url").getText() + "]]></Url>" +
                "</xml>";
    }

    public String eventMessage(Document document) {
        Element rootElement = document.getRootElement();
        return "<xml>" +
                "<ToUserName><![CDATA[" + rootElement.element("FromUserName").getText() + "]]></ToUserName>" +
                "<FromUserName><![CDATA" + rootElement.element("ToUserName").getText() + "]></FromUserName>" +
                "<CreateTime>" + new Date() + "</CreateTime>" +
                "<MsgType><![CDATA[event]]></MsgType>" +
                "<Event><![CDATA[" + rootElement.element("Event").getText() + "]]></Event>" +
                "<EventKey><![CDATA[" + rootElement.element("EventKey").getText() + "]]></EventKey>" +
                "</xml>";
    }
}
