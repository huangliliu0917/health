package com.huotu.health.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/11/17.
 */
@Getter
@Setter
public class TreatmentListModel {
    /**
     * ID
     */
    private Long id;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 时间
     */
    private String date;
}
