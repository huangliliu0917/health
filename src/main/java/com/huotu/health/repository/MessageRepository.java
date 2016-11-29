package com.huotu.health.repository;

import com.huotu.health.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 资讯
 * Created by slt on 2016/11/22.
 */
public interface MessageRepository extends JpaRepository<Message,Long>,JpaSpecificationExecutor<Message> {
    List<Message> findByCustomerId(Long customerId, Pageable pageable);

    List<Message> findByCustomerIdAndTitleLike(Long customerId,String title,Pageable pageable);

    List<Message> findTop20ByCustomerIdAndEnabledOrderByIdDesc(Long customerId,boolean enabled);

    List<Message> findTop20ByCustomerIdAndEnabledAndIdLessThanOrderByIdDesc(Long customerId,boolean enabled,Long id);

}
