package com.github.wechatgzh.biz;

import com.alibaba.fastjson2.JSONObject;
import com.github.wechatgzh.config.Constant;
import com.github.wechatgzh.entity.AccessToken;
import com.github.wechatgzh.entity.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;

import static com.github.wechatgzh.biz.TokenBiz.isValidAccessToken;
import static com.github.wechatgzh.config.StaticUrl.GET_ACCESS_TOKEN;
import static com.github.wechatgzh.config.StaticUrl.GET_TICKET;

/**
 * @author 13439
 */
//@Service
@Slf4j
public class TicketBiz {
    /**
     * 保存accessToken
     *
     * @param ticket accessToken
     * @throws IOException 保存文件异常
     */
    public static void saveTicket(Ticket ticket) throws IOException {
        String tick = JSONObject.toJSONString(ticket);
        File file = new File("ticket.txt");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(tick);
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    /**
     * 获取accessToken
     *
     * @return accessToken
     */
    public static Ticket getTicket() throws IOException {
        AccessToken validAccessToken = isValidAccessToken();
        final RestTemplate restTemplate = new RestTemplate();
        String url = String.format(GET_TICKET, validAccessToken.getAccessToken());
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        String body = forEntity.getBody();
        Ticket ticket = JSONObject.parseObject(body, Ticket.class);
        String expiresIn = ticket.getExpiresIn();
        long now = System.currentTimeMillis();
        String exp = String.valueOf(now + Long.parseLong(expiresIn) * 1000);
        ticket.setExpiresIn(exp);
        saveTicket(ticket);
        return ticket;
    }

    /**
     * 读取accessToken
     *
     * @return accessToken
     * @throws IOException 读取文件异常
     */
    public static Ticket readTicket() throws IOException {
        try {
            FileReader fileReader = new FileReader("ticket.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s = bufferedReader.readLine();
            bufferedReader.close();
            return JSONObject.parseObject(s, Ticket.class);
        } catch (Exception e) {
            // 如果读取文件失败，重新获取ticket
            log.warn("读取文件失败，重新获取ticket");
            Ticket ticket = getTicket();
            // 保存accessToken
            log.info("保存ticket");

            return ticket;
        }
    }

    public static Ticket isValidTicket() throws IOException {
        Ticket ticket = readTicket();
        long now = System.currentTimeMillis();
        log.info("now: " + now);
        long expires = Long.parseLong(ticket.getExpiresIn());
        log.info("expires: " + expires);

        if (now < expires) {
            return ticket;
        } else {
            return getTicket();
        }
    }

    @Test
    public static void main(String[] args) throws IOException {
        Ticket ticket = isValidTicket();
        System.out.println(ticket);
    }
}
