package com.github.wechatgzh.biz;

import com.alibaba.fastjson2.JSONObject;
import com.github.wechatgzh.entity.AccessToken;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * @author 13439
 */
@Service
public class TokenBiz {

    public void saveAccessToken(AccessToken accessToken) throws IOException {
        //序列化为json字符串
        String token = JSONObject.toJSONString(accessToken);
        //写入accessToken文件
        File file = new File("accessToken.txt");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(token);
        bufferedWriter.flush();
        bufferedWriter.close();
    }


    public AccessToken getAccessToken() throws IOException {
        FileReader fileReader = new FileReader("accessToken.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String s = bufferedReader.readLine();
        bufferedReader.close();
        return JSONObject.parseObject(s, AccessToken.class);
    }

    public boolean isValidAccessToken() {
        try {
            AccessToken accessToken = getAccessToken();
            if (accessToken != null) {
                long now = System.currentTimeMillis();
                long expires = Long.parseLong(accessToken.getExpiresIn());
                long last = Long.parseLong(accessToken.getExpiresIn());
                if (now - last < expires) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public AccessToken fetchAccessToken(AccessToken accessToken) {


        return null;
    }
}
