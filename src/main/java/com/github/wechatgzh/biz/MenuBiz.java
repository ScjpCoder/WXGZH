package com.github.wechatgzh.biz;

import com.alibaba.fastjson2.JSONObject;
import com.github.wechatgzh.entity.AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author 13439
 */
@Slf4j
@Service
public class MenuBiz {

    public String createMenu(AccessToken accessToken) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken.getAccessToken();
        FileReader fileReader = new FileReader("menu.json");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String s = bufferedReader.readLine();
        bufferedReader.close();
        JSONObject jsonObject = JSONObject.parseObject(s);
        String result = restTemplate.postForObject(url, jsonObject.toJSONString(), String.class);
        log.info("创建菜单结果:{}", result);
        return result;
    }

    public String deleteMenu(AccessToken accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + accessToken.getAccessToken();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        String body = forEntity.getBody();
        log.info(body);
        return body;
    }
}
