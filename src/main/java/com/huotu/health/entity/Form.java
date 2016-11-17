package com.huotu.health.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 表单
 * Created by slt on 2016/11/17.
 */
@Entity
@Setter
@Getter
@Table(name = "Health_Form")
public class Form {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 所属疗程
     */
    @ManyToOne
    private Treatment treatment;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板内容
     */
    @Lob
    private String content;
//    /**
//     * 所属步骤
//     */
//    private Integer step;

    /**
     * 完成情况，0：未完成，1：已完成，2：进行中
     */
    private Integer status;


}
