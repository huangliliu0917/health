package com.huotu.health.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/11/17.
 */
@Entity
@Setter
@Getter
@Table(name = "Health_VipUser")
public class VipUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商家
     */
    private Long merchantId;


    /**
     * vip用户
     */
    @ManyToOne
    private User user;


}
