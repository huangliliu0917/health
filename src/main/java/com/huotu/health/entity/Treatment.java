package com.huotu.health.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 疗程
 * Created by slt on 2016/11/17.
 */
@Entity
@Setter
@Getter
@Table(name = "Health_Treatment")
public class Treatment {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * 用户ID
     */
    private Long userId;

//    /**
//     * 当前记录的步骤
//     */
//    private Integer step;


}
