package com.github.wechatgzh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author 13439
 */
@ServletComponentScan
@SpringBootApplication
public class WeChatGzhApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeChatGzhApplication.class, args);
    }

}
