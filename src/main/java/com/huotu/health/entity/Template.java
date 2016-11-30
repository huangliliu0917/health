package com.huotu.health.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 模板，比如问卷，报告，表格等等
 * Created by slt on 2016/11/17.
 */
@Entity
@Setter
@Getter
@Table(name = "Health_Template")
@EqualsAndHashCode(exclude = "content")
public class Template {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 所属商家ID
     */
    private Long customerId;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板内容
     */
    @Lob
    private String content;

    /**
     * 是否可用
     */
    private boolean enabled = true;
}
