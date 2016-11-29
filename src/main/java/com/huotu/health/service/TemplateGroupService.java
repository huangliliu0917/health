package com.huotu.health.service;

import com.huotu.health.entity.Template;
import com.huotu.health.entity.TemplateGroup;
import com.huotu.health.model.TemplateGroupListModel;

import java.util.List;

/**
 * 表单服务
 * Created by slt on 2016/11/17.
 */
public interface TemplateGroupService {

    /**
     * 实体转换model
     * @param templateGroups
     * @return
     */
    List<TemplateGroupListModel> convertTemplateGroups(List<TemplateGroup> templateGroups);

    /**
     * 保存模板组
     * @param group
     * @throws Exception
     */
    TemplateGroup saveTemplateGroup(TemplateGroup group) throws Exception;

    /**
     * 找出该模板组未选择的模板
     * @param templates
     * @return
     */
    List<Template> filterNotChoice(List<Template> templates,Long customerId);
}
