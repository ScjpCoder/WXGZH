package com.github.wechatgzh.biz;

import com.github.wechatgzh.utils.TemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.github.wechatgzh.config.TypeEnum.*;

/**
 * @author 13439
 */
@Slf4j
@Service
public class SignatureBiz {
    @Resource
    private TemplateUtil templateUtil;

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
    @SuppressWarnings("all")
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
            String msgType = rootElement.element("MsgType").getText();
            System.out.println(msgType);
            if (TEXT.getType().equals(msgType)) {
                log.info("文本消息");
                String content = rootElement.element("Content").getText();
                if ("1".equals(content)) {
                    return templateUtil.textMessage("大吉大利，今晚吃鸡", document);
                } else if ("2".equals(content)) {
                    return templateUtil.textMessage("落地成盒，明天吃鸡", document);
                } else {
                    return templateUtil.textMessage("你说啥呢，我咋听不懂", document);
                }
            } else if (IMAGE.getType().equals(msgType)) {
                log.info("图片消息");
                return templateUtil.imageMessage(document);
            } else if (VOICE.getType().equals(msgType)) {
                log.info("语音消息");
                return templateUtil.voiceMessage(document);
            } else if (VIDEO.getType().equals(msgType)) {
                log.info("视频消息");
                return templateUtil.videoMessage(document);
            } else if (SHORTVIDEO.getType().equals(msgType)) {
                log.info("小视频消息");
                return templateUtil.shortvideoMessage(document);
            } else if (LOCATION.getType().equals(msgType)) {
                log.info("地理位置消息");
                return templateUtil.locationMessage(document);
            } else if (LINK.getType().equals(msgType)) {
                log.info("链接消息");
                return templateUtil.linkMessage(document);
            } else if (EVENT.getType().equals(msgType)) {
                log.info("事件消息");
                return templateUtil.eventMessage(document);
            }
            return templateUtil.textMessage("你说啥呢，我咋听不懂", document);
        } else {
            log.info("xml中没有数据");
            return "success";
        }
    }
}
