package com.github.wechatgzh.biz;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

/**
 * @author 13439
 */
@Service
public class SignatureBiz {

    public String getUserData(HttpServletRequest request) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        //收集流数据
        StringBuilder ssb = new StringBuilder();
        int b;
        while ((b = inputStream.read()) != -1) {
            ssb.append((char) b);
        }
        return ssb.toString();
    }

    public Document parseXml(String xmlData) {
        try {
            return DocumentHelper.parseText(xmlData);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

    }

    public String formatMessage(Document document) {
        if (document != null && document.hasContent()) {
            Element rootElement = document.getRootElement();
            String content = rootElement.element("Content").getText();
            if ("1".equals(content)) {
                return result("大吉大利，今晚吃鸡", document);
            } else if ("2".equals(content)) {
                return result("落地成盒，明天吃鸡", document);
            }
        }
        return null;
    }


    public String result(String content, Document document) {
        Element rootElement = document.getRootElement();
        return "<xml>" +
                "<ToUserName><![CDATA[" + rootElement.element("FromUserName").getText() + "]]></ToUserName>" +
                "<FromUserName><![CDATA" + rootElement.element("ToUserName").getText() + "]></FromUserName>" +
                "<CreateTime>" + new Date() + "</CreateTime>" +
                "<MsgType><![CDATA[text]]></MsgType>" +
                "<Content><![CDATA[" + content + "]]></Content>" +
                "</xml>";
    }
}
