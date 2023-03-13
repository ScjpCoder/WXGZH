package com.github.wechatgzh.config;

/**
 * @author 13439
 */
public class StaticUrl {


    private static final String BASE_URL = "https://api.weixin.qq.com/cgi-bin/";


    public static final String GET_ACCESS_TOKEN = BASE_URL + "token?grant_type=client_credential&appid=%s&secret=%s";


    public static final String CREATE_MENU = BASE_URL + "menu/create?access_token=%s";


    public static final String DELETE_MENU = BASE_URL + "menu/delete?access_token=%s";
}
