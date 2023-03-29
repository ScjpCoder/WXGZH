package com.github.wechatgzh.biz;

import com.alibaba.fastjson2.JSONObject;
import com.github.wechatgzh.entity.AccessToken;
import com.github.wechatgzh.entity.MediaResult;
import com.github.wechatgzh.utils.MaterialUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.github.wechatgzh.biz.TokenBiz.isValidAccessToken;
import static com.github.wechatgzh.config.StaticUrl.UPLOAD_TEMPORARY_MATERIAL;

@Slf4j
public class MaterialBiz {

    public MediaResult uploadTemporaryMaterial(String type, File file) throws IOException {
        AccessToken accessToken = isValidAccessToken();
        String urlString = String.format(UPLOAD_TEMPORARY_MATERIAL, accessToken.getAccessToken(), type);
        log.info("url: " + urlString);
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        String result = null;
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        String boundary = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
        byte[] head = MaterialUtil.getHeader(file, boundary);
        MaterialUtil.outputTable(file, con, boundary, head);
        result = MaterialUtil.getResponse(con, result);
        return JSONObject.parseObject(result, MediaResult.class);
    }



    public static void main(String[] args) {
        MaterialBiz materialBiz = new MaterialBiz();
        try {
            MediaResult image = materialBiz.uploadTemporaryMaterial("image", new File("C:\\Users\\13439\\Desktop\\1.png"));
            System.out.println(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
