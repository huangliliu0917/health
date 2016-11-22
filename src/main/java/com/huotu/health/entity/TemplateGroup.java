package com.huotu.health.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 模板组，用来生成一个疗程
 * Created by slt on 2016/11/17.
 */
@Entity
@Setter
@Getter
@Table(name = "Health_TemplateGroup")
public class TemplateGroup {
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
     * 模板组名称
     */
    private String name;


    /**
     * 模板列表
     */
    @OneToMany
    private List<Template> templates;

}
