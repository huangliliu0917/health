package com.huotu.health.service.impl;

import com.huotu.health.entity.Template;
import com.huotu.health.entity.TemplateGroup;
import com.huotu.health.entity.support.TemplateId;
import com.huotu.health.model.TemplateGroupListModel;
import com.huotu.health.repository.TemplateGroupRepository;
import com.huotu.health.repository.TemplateRepository;
import com.huotu.health.service.TemplateGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/11/17.
 */
@Service
public class TemplateGroupServiceImpl implements TemplateGroupService {
    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private TemplateGroupRepository templateGroupRepository;

    @Override
    public List<TemplateGroupListModel> convertTemplateGroups(List<TemplateGroup> templateGroups) {
        List<TemplateGroupListModel> models=new ArrayList<>();
        if(templateGroups==null){
            return models;
        }

        templateGroups.forEach(templateGroup -> {
            TemplateGroupListModel model=new TemplateGroupListModel();
            model.setId(templateGroup.getId());
            model.setDate(templateGroup.getDate());
            model.setName(templateGroup.getName());
            model.setTemplates(extractTemplatesName(templateGroup.getTemplateIds()));
            models.add(model);
        });

        return models;
    }

    @Override
    public TemplateGroup saveTemplateGroup(TemplateGroup group) throws Exception {


        return templateGroupRepository.save(group);

    }

    @Override
    public List<Template> filterNotChoice(List<Template> templates,Long customerId) {

        List<Template> allTemplates=templateRepository.findByCustomerIdAndEnabledOrderByIdDesc(customerId,true);

        if(templates==null||templates.isEmpty()){
            return allTemplates;
        }

        List<Template> notChoiseTemplates=new ArrayList<>();

        allTemplates.forEach(template -> {
            if(!templates.contains(template)){
                notChoiseTemplates.add(template);
            }
        });

        return notChoiseTemplates;
    }

    @Override
    public List<Template> getTemplate(List<TemplateId> templateIds) {
        List<Template> templates=new ArrayList<>();
        if(templateIds==null){
            return templates;
        }
        templateIds.forEach(templateId -> {
            Template template=templateRepository.findOne(templateId.getIdx());
            templates.add(template);
        });
        return templates;
    }

    private String extractTemplatesName(List<TemplateId> templates){
        StringBuilder templatesName=new StringBuilder("");
        if(templates==null){
            return templatesName.toString();
        }

        Iterator<TemplateId> ite=templates.iterator();
        while (ite.hasNext()){
            TemplateId templateId=ite.next();
            Template template=templateRepository.findOne(templateId.getIdx());
            templatesName.append(template.getName());
            if(ite.hasNext()){
                templatesName.append(",");
            }
        }

        return templatesName.toString();
    }
}
