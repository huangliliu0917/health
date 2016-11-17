package com.huotu.health.repository;

import com.huotu.health.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 疗程
 * Created by slt on 2016/11/17.
 */
@Repository
public interface TreatmentRepository extends JpaRepository<Treatment,Long>,JpaSpecificationExecutor<Treatment> {
}
