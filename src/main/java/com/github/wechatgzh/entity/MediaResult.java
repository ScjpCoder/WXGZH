package com.github.wechatgzh.entity;

import lombok.Data;

import java.util.List;

@Data
public class MediaResult {

    private String type;

    private String media_id;

    private String created_at;


    private List<Object> item;
}
