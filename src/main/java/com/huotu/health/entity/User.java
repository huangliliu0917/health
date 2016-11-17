package com.huotu.health.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/11/17.
 */
@Entity
@RestResource
@Setter
@Getter
@Cacheable(value = false)
@Table(name = "Hot_UserBaseInfo")
@Description("用户")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UB_UserID")
    @Description("会员号")
    private Long id;

    /**
     * 商家
     */
    @JoinColumn(name = "UB_CustomerID")
    @Description("所在商户")
    private Long merchantId;


    @Column(name = "UB_WxNickName")
    @Description("微信昵称")
    private String wxNickName;


}
