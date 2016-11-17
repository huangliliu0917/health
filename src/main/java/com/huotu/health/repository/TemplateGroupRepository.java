package com.huotu.health.repository;

import com.huotu.health.entity.TemplateGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 模板组
 * Created by slt on 2016/11/17.
 */
@Repository
public interface TemplateGroupRepository extends JpaRepository<TemplateGroup,Long>,JpaSpecificationExecutor<TemplateGroup> {
}
