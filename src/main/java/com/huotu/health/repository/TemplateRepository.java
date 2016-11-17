package com.huotu.health.repository;

import com.huotu.health.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 模板
 * Created by slt on 2016/11/17.
 */
@Repository
public interface TemplateRepository extends JpaRepository<Template,Long>,JpaSpecificationExecutor<Template> {
}
