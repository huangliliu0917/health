package com.huotu.health.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 资讯列表model
 * Created by Administrator on 2016/11/17.
 */
@Getter
@Setter
public class MessageListModel {
    /**
     * ID
     */
    private Long id;

    /**
     * 是否可用
     */
    private boolean enabled = true;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String summary;

    /**
     * 封面图片
     */
    private String pictureUrl;

    /**
     * 创建时间
     */
    private Date date;
}
