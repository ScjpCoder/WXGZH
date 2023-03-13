package com.github.wechatgzh.biz;

import com.alibaba.fastjson2.JSONObject;
import com.github.wechatgzh.config.Constant;
import com.github.wechatgzh.controller.SignatureController;
import com.github.wechatgzh.entity.AccessToken;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;

/**
 * @author 13439
 */
//@Service
public class TokenBiz {

    public static void saveAccessToken(AccessToken accessToken) throws IOException {
        String token = JSONObject.toJSONString(accessToken);
        File file = new File("accessToken.txt");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(token);
        bufferedWriter.flush();
        bufferedWriter.close();
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

    public static AccessToken readAccessToken() throws IOException {
        try {
            FileReader fileReader = new FileReader("accessToken.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s = bufferedReader.readLine();
            bufferedReader.close();
            return JSONObject.parseObject(s, AccessToken.class);
        } catch (Exception e) {
            AccessToken accessToken = getAccessToken();
            saveAccessToken(accessToken);
            return accessToken;
        }
    }

    public static boolean isValidAccessToken() {
        AccessToken accessToken = getAccessToken();
        long now = System.currentTimeMillis();
        System.out.println("now: " + now);
        long expires = Long.parseLong(accessToken.getExpiresIn());
        System.out.println("expires: " + expires);
        long last = now + 7200 * 1000;
        System.out.println("last: " + last);
        if (now - last < expires) {
            return true;
        }
        return false;
    }

    @Test
    public static void main(String[] args) {
        boolean validAccessToken = isValidAccessToken();
        System.out.println(validAccessToken);
    }
}
