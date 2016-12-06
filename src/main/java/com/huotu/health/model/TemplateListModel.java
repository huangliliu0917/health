package com.huotu.health.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/17.
 */
@Getter
@Setter
public class TemplateListModel {
    /**
     * ID
     */
    private Long id;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 创建时间
     */
    private Date date;
}
