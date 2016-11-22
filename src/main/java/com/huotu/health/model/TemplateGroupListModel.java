package com.huotu.health.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 模板组
 * Created by slt on 2016/11/17.
 */
@Getter
@Setter
public class TemplateGroupListModel {
    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 模板名称组
     */
    private String templates;
}
