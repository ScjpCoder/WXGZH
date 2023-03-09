package com.github.wechatgzh.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.wechatgzh.biz.SignatureBiz;
import com.github.wechatgzh.config.Constant;
import com.github.wechatgzh.entity.AccessToken;
import com.github.wechatgzh.utils.Sha1Util;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

/**
 * @author 13439
 */
@Slf4j
@RestController
public class SignatureController {
    @Resource
    private javax.servlet.http.HttpServletRequest request;
    @Resource
    private javax.servlet.http.HttpServletResponse response;
    @Resource
    private SignatureBiz signatureBiz;
    @RequestMapping("/")
    public void wxSignature(@RequestParam("signature") String signature,
                              @RequestParam("timestamp") String timestamp,
                              @RequestParam("nonce") String nonce,
                              @RequestParam(value = "echostr",required = false) String echostr) throws IOException {
        log.info("signature: {}, timestamp: {}, nonce: {}, echostr: {}", signature, timestamp, nonce, echostr);
        request.setCharacterEncoding("UTF-8");

        response.setCharacterEncoding("UTF-8");
        String string = timestamp + nonce + Constant.TOKEN;
        String result = Sha1Util.getSha1(string);
        System.out.println(result);
        System.out.println(signature);
        if (Objects.equals(request.getMethod(), RequestMethod.GET.name())) {
            if (result.equals(signature)) {
                System.out.println("验证成功");
                response.getWriter().write(echostr);
            } else {
                System.out.println("验证失败");
                response.getWriter().write("error");
            }
        } else if (Objects.equals(request.getMethod(), RequestMethod.POST.name())) {
            if (result.equals(signature)) {
                String userData = signatureBiz.getUserData(request);
                Document document = signatureBiz.parseXml(userData);
                String doc = signatureBiz.formatMessage(document);
                System.out.println(doc);
                response.getWriter().write(doc);
            } else {
                System.out.println("验证失败");
                response.getWriter().write("error");
            }
        }
    }


    public static AccessToken getAccessToken() {
        final RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + Constant.APPID + "&secret=" + Constant.APP_SECRET;
        System.out.println(url);
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        String body = forEntity.getBody();
        AccessToken accessToken = JSONObject.parseObject(body, AccessToken.class);
        String expiresIn = accessToken.getExpiresIn();
        long now = System.currentTimeMillis();
        accessToken.setExpiresIn(String.valueOf(now + Long.parseLong(expiresIn) * 1000));
        return accessToken;
    }

    public static void saveAccessToken(AccessToken accessToken) throws IOException {
        String token = JSONObject.toJSONString(accessToken);
        File file = new File("accessToken.txt");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(token);
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
