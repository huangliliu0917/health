/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.health.repository;

import com.huotu.health.entity.VipUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author slt
 */
@Repository
public interface VipUserRepository extends JpaRepository<VipUser, Long>, JpaSpecificationExecutor<VipUser> {
    @Query("select vip from VipUser as vip where vip.merchantId=?1 and vip.user.wxNickName like ?2")
    List<VipUser> findByMerchantIdAndWxNickNameLike(Long customerId, String name, Pageable pageable);

}
