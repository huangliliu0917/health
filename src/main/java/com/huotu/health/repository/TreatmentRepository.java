package com.huotu.health.repository;

import com.huotu.health.entity.Treatment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 疗程
 * Created by slt on 2016/11/17.
 */
@Repository
public interface TreatmentRepository extends JpaRepository<Treatment,Long>,JpaSpecificationExecutor<Treatment> {

    Page<Treatment> findByCustomerIdAndEnabledAndWxNickNameLikeOrderByIdDesc(Long customerId,boolean enabled, String name,Pageable pageable);

    Page<Treatment> findByCustomerIdAndEnabledOrderByIdDesc(Long customerId, boolean enabled, Pageable pageable);

    List<Treatment> findByCustomerIdAndEnabledAndUserIdOrderByIdDesc(Long customerId,boolean enabled,Long userId);
}
