package com.github.wechatgzh.controller;

import com.github.wechatgzh.biz.MenuBiz;
import com.github.wechatgzh.biz.TokenBiz;
import com.github.wechatgzh.entity.AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.io.IOException;

import static com.github.wechatgzh.biz.TokenBiz.isValidAccessToken;

/**
 * @author 13439
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuBiz menuBiz;

    @PostMapping("/create")
    public String createMenu() throws IOException {
        AccessToken accessToken = isValidAccessToken();
        return menuBiz.createMenu(accessToken);
    }

    @GetMapping("/delete")
    public String deleteMenu() throws IOException {
        AccessToken accessToken = isValidAccessToken();
        return menuBiz.deleteMenu(accessToken);
    }
    @PostMapping("/auto")
    public String autoCreate() throws IOException {
        AccessToken accessToken = isValidAccessToken();
        menuBiz.deleteMenu(accessToken);
        return menuBiz.createMenu(accessToken);
    }
}
