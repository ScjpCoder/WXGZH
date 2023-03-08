package com.github.wechatgzh.filter;

import com.alibaba.fastjson2.JSONObject;
import com.github.wechatgzh.config.Constant;
import com.github.wechatgzh.entity.AccessToken;
import com.github.wechatgzh.utils.Sha1Util;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author 13439
 */
@WebFilter("/")
public class SignatureFilter implements Filter {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        System.out.println(echostr);
        String string = timestamp + nonce + Constant.TOKEN;
        String result = Sha1Util.getSha1(string);
        System.out.println(result);
        System.out.println(signature);
        if (result.equals(signature)) {
            System.out.println("验证成功");
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + Constant.APPID + "&secret=" + Constant.APP_SECRET;
            System.out.println(url);
            ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
            String body = forEntity.getBody();

            saveAccessToken(body);
        } else {
            System.out.println("验证失败");
        }
    }

    private void saveAccessToken(String body) throws IOException {
        //写入accessToken文件
        File file = new File("accessToken.txt");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(body);
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
