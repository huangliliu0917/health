package com.huotu.health.service.impl;

import com.huotu.health.entity.Template;
import com.huotu.health.entity.TemplateGroup;
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
            model.setName(templateGroup.getName());
            model.setTemplates(extractTemplatesName(templateGroup.getTemplates()));
            models.add(model);
        });

        return models;
    }

    @Override
    public void saveTemplateGroup(TemplateGroup group) throws Exception {
        List<Template> templates=new ArrayList<>();
        group.getTemplates().forEach(template -> {
            templates.add(templateRepository.findOne(template.getId()));
        });

//        templates.forEach(template -> {
//            template=templateRepository.findOne(template.getId());
//        });
        group.setTemplates(templates);
        templateGroupRepository.save(group);

    }

    @Override
    public List<Template> filterNotChoice(List<Template> templates,Long customerId) {

        List<Template> allTemplates=templateRepository.findByCustomerId(customerId);

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

    private String extractTemplatesName(List<Template> templates){
        StringBuilder templatesName=new StringBuilder("");
        if(templates==null){
            return templatesName.toString();
        }

        Iterator<Template> ite=templates.iterator();
        while (ite.hasNext()){
            Template template=ite.next();
            templatesName.append(template.getName());
            if(ite.hasNext()){
                templatesName.append(",");
            }
        }

        return templatesName.toString();
    }
}
