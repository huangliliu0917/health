package com.huotu.health.controller.back;

import com.huotu.health.annotation.CustomerId;
import com.huotu.health.entity.Template;
import com.huotu.health.entity.TemplateGroup;
import com.huotu.health.model.TemplateGroupListModel;
import com.huotu.health.repository.TemplateGroupRepository;
import com.huotu.health.repository.TemplateRepository;
import com.huotu.health.service.TemplateGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 模板
 * Created by slt on 2016/11/17.
 */
@Controller
@RequestMapping("/back")
public class TemplateGroupController {
    @Autowired
    private TemplateGroupRepository templateGroupRepository;

    @Autowired
    private TemplateGroupService templateGroupService;

    @Autowired
    private TemplateRepository templateRepository;

    /**
     * 根据商户ID获取模板列表
     * @param customerId    商户ID
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showTemplateGroupList")
    public String showTemplateGroupList(@CustomerId Long customerId, Model model) throws Exception{

        List<TemplateGroup> templateGroups=templateGroupRepository.findByCustomerIdAndEnabled(customerId,true);
        List<TemplateGroupListModel> models=templateGroupService.convertTemplateGroups(templateGroups);
        model.addAttribute("list",models);
        return "/back/template_group_list";
    }

    /**
     * 根据模板组ID获取模板详情
     * @param id    模板id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modifyTemplateGroup")
    public String modifyTemplateGroup(@CustomerId Long customerId,Long id, Model model) throws Exception{
        TemplateGroup group=new TemplateGroup();
        if(id!=null){
            group=templateGroupRepository.findOne(id);
        }
        List<Template> notChoices=templateGroupService.filterNotChoice(group.getTemplates(),customerId);
        model.addAttribute("group",group);
        model.addAttribute("templates",notChoices);
        return "/back/template_group_modify";
    }

    /**
     * 保存模板组
     * @param group    模板组
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveTemplateGroup",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap saveTemplateGroup(@CustomerId Long customerId,@RequestBody TemplateGroup group) throws Exception{
        if(group.getCustomerId()==null){
            group.setCustomerId(customerId);
        }
        group=templateGroupService.saveTemplateGroup(group);
        ModelMap modelMap=new ModelMap();
        modelMap.addAttribute("id",group.getId());
        return modelMap;
    }

    /**
     * 删除
     * @param id    模板组id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delTemplateGroup",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap delTemplateGroup(@RequestParam Long id) throws Exception{

        TemplateGroup templateGroup=templateGroupRepository.findOne(id);
        templateGroup.setEnabled(false);
        templateGroupRepository.save(templateGroup);
        return new ModelMap();
    }


}
