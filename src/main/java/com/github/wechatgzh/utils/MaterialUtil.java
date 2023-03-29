package com.github.wechatgzh.utils;

import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Service
public class MaterialUtil {

    static String getResponse(HttpURLConnection con, String result) {
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

    static void outputTable(File file, HttpURLConnection con, String boundary, byte[] head) throws IOException {
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
    static byte[] getHeader(File file, String boundary) {
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
}
