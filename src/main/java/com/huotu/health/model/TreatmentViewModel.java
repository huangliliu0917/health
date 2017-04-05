package com.huotu.health.model;

import lombok.Getter;
import lombok.Setter;


import java.util.Date;

/**
 * Created by hot on 2017/4/5.
 */
@Setter
@Getter
public class TreatmentViewModel {
    private Long id;

    /**
     * 商户ID
     */
    private Long customerId;

    /**
     * 疗程名称
     */
    private String name;

    /**
     * 用户
     */
    private String userId;

    /**
     * 用户昵称
     */
    private String wxNickName;

    /**
     *  模板组
     */
    private Long templateGroupId;


    /**
     * 创建时间
     */
    private Date date;

    /**
     * 是否可用
     */
    private boolean enabled = true;
}
