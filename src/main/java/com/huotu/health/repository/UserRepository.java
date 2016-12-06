/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.health.repository;

import com.huotu.health.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * @author slt
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("select u.id,u.wxNickName,v.id from User as u left join VipUser as v on u=v.user where u.merchantId=?1 and u.wxNickName like ?2 order by u.id desc ")
    Page<Object> findUserByName(Long customerId,String name, Pageable pageable);


    @Query("select u.id,u.wxNickName,v.id from User as u left join VipUser as v on u=v.user where u.merchantId=?1 order by u.id desc ")
    Page<Object> findUser(Long customerId, Pageable pageable);

}
