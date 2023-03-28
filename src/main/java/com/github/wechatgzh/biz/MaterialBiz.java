package com.github.wechatgzh.biz;

import com.alibaba.fastjson2.JSONObject;
import com.github.wechatgzh.entity.AccessToken;
import com.github.wechatgzh.entity.MediaResult;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

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
        byte[] head = getHeader(file, boundary);
        outputTable(file, con, boundary, head);
        result = getResponse(con, result);
        return JSONObject.parseObject(result, MediaResult.class);
    }

    private static String getResponse(HttpURLConnection con, String result) {
        StringBuilder buffer = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            // 定义BufferedReader输入流来读取URL的响应
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            result = buffer.toString();
        } catch (IOException e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        return result;
    }

    private static void outputTable(File file, HttpURLConnection con, String boundary, byte[] head) throws IOException {
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);
        DataInputStream in = new DataInputStream(Files.newInputStream(file.toPath()));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        // 结尾部分
        byte[] foot = ("\r\n--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8);// 定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();
    }

    @SuppressWarnings("all")
    private static byte[] getHeader(File file, String boundary) {
        StringBuilder sb = new StringBuilder();
        sb.append("--")
                .append(boundary)
                .append("\r\n")
                .append("Content-Disposition: form-data;name=\"media\";filelength=\"")
                .append(file.length())
                .append("\";filename=\"")
                .append(file.getName())
                .append("\"\r\n")
                .append("Content-Type:application/octet-stream\r\n\r\n");
        return sb.toString().getBytes(StandardCharsets.UTF_8);
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
