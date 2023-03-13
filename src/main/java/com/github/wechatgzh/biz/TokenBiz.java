package com.github.wechatgzh.biz;

import com.alibaba.fastjson2.JSONObject;
import com.github.wechatgzh.config.Constant;
import com.github.wechatgzh.entity.AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;

/**
 * @author 13439
 */
//@Service
@Slf4j
public class TokenBiz {
    /**
     * 保存accessToken
     *
     * @param accessToken accessToken
     * @throws IOException 保存文件异常
     */
    public static void saveAccessToken(AccessToken accessToken) throws IOException {
        String token = JSONObject.toJSONString(accessToken);
        File file = new File("accessToken.txt");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(token);
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    /**
     * 获取accessToken
     *
     * @return accessToken
     */
    public static AccessToken getAccessToken() throws IOException {
        final RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + Constant.APPID + "&secret=" + Constant.APP_SECRET;
        System.out.println(url);
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        String body = forEntity.getBody();
        AccessToken accessToken = JSONObject.parseObject(body, AccessToken.class);
        String expiresIn = accessToken.getExpiresIn();
        long now = System.currentTimeMillis();
        accessToken.setExpiresIn(String.valueOf(now + Long.parseLong(expiresIn) * 1000));
        saveAccessToken(accessToken);
        return accessToken;
    }

    /**
     * 读取accessToken
     *
     * @return accessToken
     * @throws IOException 读取文件异常
     */
    public static AccessToken readAccessToken() throws IOException {
        try {
            FileReader fileReader = new FileReader("accessToken.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s = bufferedReader.readLine();
            bufferedReader.close();
            return JSONObject.parseObject(s, AccessToken.class);
        } catch (Exception e) {
            // 如果读取文件失败，重新获取accessToken
            log.warn("读取文件失败，重新获取accessToken");
            AccessToken accessToken = getAccessToken();
            // 保存accessToken
            log.info("保存accessToken");

            return accessToken;
        }
    }

    public static AccessToken isValidAccessToken() throws IOException {
        AccessToken accessToken = readAccessToken();
        long now = System.currentTimeMillis();
        log.info("now: " + now);
        long expires = Long.parseLong(accessToken.getExpiresIn());
        log.info("expires: " + expires);

        if (now < expires) {
            return accessToken;
        } else {
            return getAccessToken();
        }
    }

    @Test
    public static void main(String[] args) throws IOException {
        AccessToken validAccessToken = isValidAccessToken();
        System.out.println(validAccessToken);
    }
}
