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


    List<Message> findByCustomerIdAndEnabled(Long customerId,boolean enabled, Pageable pageable);

    List<Message> findByCustomerIdAndEnabledAndTitleLike(Long customerId,boolean enabled,String title,Pageable pageable);

    List<Message> findTop20ByCustomerIdAndEnabledAndPutAwayOrderByIdDesc(Long customerId,boolean enabled,boolean putaway);

    List<Message> findByCustomerIdAndEnabledAndPutAwayAndStickOrderByIdDesc(Long customerId,boolean enabled,boolean putaway,boolean stick);

    List<Message> findTop20ByCustomerIdAndEnabledAndPutAwayAndIdLessThanOrderByIdDesc(Long customerId,boolean enabled,boolean putaway,Long id);

}
