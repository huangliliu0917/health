package com.huotu.health.repository;

import com.huotu.health.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 资讯
 * Created by slt on 2016/11/22.
 */
public interface MessageRepository extends JpaRepository<Message,Long>,JpaSpecificationExecutor<Message> {


    Page<Message> findByCustomerIdAndEnabledOrderByIdDesc(Long customerId, boolean enabled, Pageable pageable);

    Page<Message> findByCustomerIdAndEnabledAndTitleLikeOrderByIdDesc(Long customerId,boolean enabled,String title,Pageable pageable);

    List<Message> findTop20ByCustomerIdAndEnabledAndPutAwayAndStickOrderByIdDesc(Long customerId,boolean enabled,boolean putaway,boolean stick);

    List<Message> findByCustomerIdAndEnabledAndPutAwayAndStickOrderByIdDesc(Long customerId,boolean enabled,boolean putaway,boolean stick);

    List<Message> findTop20ByCustomerIdAndEnabledAndPutAwayAndStickAndIdLessThanOrderByIdDesc(Long customerId,boolean enabled,boolean putaway,boolean stick,Long id);

}
