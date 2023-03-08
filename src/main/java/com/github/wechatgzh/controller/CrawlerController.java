package com.github.wechatgzh.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;

/**
 * @author 13439
 */
@RestController
public class CrawlerController {

    @Autowired
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RequestMapping("/search")
    public String search(String keyword) throws IOException {
        URL url = new URL("https://lajifenleiapp.com/sk/" + keyword);
        Document document = Jsoup.parse(url, 30000);
        String className="[class=row]";
        Elements div = document.getElementsByTag("div").select(className);
        String result = div.get(5).text();
        System.out.println(result);
        return result;
    }
}
