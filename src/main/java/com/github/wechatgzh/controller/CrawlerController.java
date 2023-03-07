package com.github.wechatgzh.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
        String className="[class=col-md-offset-1 col-md-10 col-xs-12]";
        Elements div = document.getElementsByTag("div").select(className);
        Element element = div.get(1).getElementsByTag("div").get(0);
        Element child = element.getElementsByTag("div").get(0);
        String result = child.getElementsByTag("h1").text();
        System.out.println(result);
        return result;
    }
}
