package com.huotu.health.repository;

import com.huotu.health.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 表单
 * Created by slt on 2016/11/17.
 */
@Repository
public interface FormRepository extends JpaRepository<Form,Long>,JpaSpecificationExecutor<Form> {

    List<Form> findByTreatment_IdOrderByStep(Long id);

    @Query("update Form as f set f.content=?1 where f.id=?2")
    @Modifying
    @Transactional
    int updateFormContent(String content,Long id);


}
