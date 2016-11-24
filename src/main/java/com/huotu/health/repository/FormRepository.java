package com.huotu.health.repository;

import com.huotu.health.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 表单
 * Created by slt on 2016/11/17.
 */
@Repository
public interface FormRepository extends JpaRepository<Form,Long>,JpaSpecificationExecutor<Form> {

    List<Form> findByTreatment_IdOrderByStep(Long id);


}
