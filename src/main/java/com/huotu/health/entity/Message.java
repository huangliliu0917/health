package com.huotu.health.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 咨询
 * Created by slt on 2016/11/22.
 */
@Entity
@Getter
@Setter
@Table(name = "Health_Message")
public class Message {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商家
     */
    private Long customerId;

    /**
     * 是否可用
     */
    private boolean enabled = true;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片
     */
    @Column(length = 200)
    private String pictureUrl;

    /**
     * 简介
     */
    @Column(length = 500)
    private String summary;

    /**
     * 资讯内容
     */
    @Lob
    private String content;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

}
