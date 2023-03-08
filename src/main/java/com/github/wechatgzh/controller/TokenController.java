package com.github.wechatgzh.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 13439
 */
public class TokenController {
    @Resource
    private HttpServletRequest request;
//    @RequestMapping("")
//    public void getToken(String signature, String timestamp, String nonce, String echostr) {
//        System.out.println(signature);
//        System.out.println(timestamp);
//        System.out.println(nonce);
//        System.out.println(echostr);
//        String requestURI = request.getRequestURI();
//        System.out.println(requestURI);
//    }
}
