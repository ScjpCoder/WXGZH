package com.github.wechatgzh.biz;

import com.alibaba.fastjson2.JSONObject;
import com.github.wechatgzh.controller.SignatureController;
import com.github.wechatgzh.entity.AccessToken;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author 13439
 */
//@Service
public class TokenBiz {

    public void saveAccessToken(AccessToken accessToken) throws IOException {
        //序列化为json字符串
        SignatureController.saveAccessToken(accessToken);
    }


    public static AccessToken getAccessToken() throws IOException {
        try {
            FileReader fileReader = new FileReader("accessToken.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s = bufferedReader.readLine();
            bufferedReader.close();
            return JSONObject.parseObject(s, AccessToken.class);
        } catch (Exception e) {
            AccessToken accessToken = SignatureController.getAccessToken();
            SignatureController.saveAccessToken(accessToken);
            return accessToken;
        }
    }

    public static boolean isValidAccessToken() {
        try {
            AccessToken accessToken = getAccessToken();
            if (accessToken != null) {
                long now = System.currentTimeMillis();
                System.out.println("now: " + now);
                long expires = Long.parseLong(accessToken.getExpiresIn());
                System.out.println("expires: " + expires);
                long last = now + 7200 * 1000;
                System.out.println("last: " + last);
                if (now - last < expires) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Test
    public static void main(String[] args) {
        boolean validAccessToken = isValidAccessToken();
        System.out.println(validAccessToken);
    }
}
