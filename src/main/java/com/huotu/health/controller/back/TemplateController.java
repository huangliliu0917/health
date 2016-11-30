package com.huotu.health.controller.back;

import com.huotu.health.annotation.CustomerId;
import com.huotu.health.entity.Template;
import com.huotu.health.model.TemplateListModel;
import com.huotu.health.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板
 * Created by slt on 2016/11/17.
 */
@Controller
@RequestMapping("/back")
public class TemplateController {
    @Autowired
    private TemplateRepository templateRepository;

    /**
     * 根据商户ID获取模板列表
     * @param customerId    商户ID
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showTemplateList")
    public String showTemplateList(@CustomerId Long customerId, Model model) throws Exception{

        List<Template> templates=templateRepository.findByCustomerIdAndEnabled(customerId,true);

        List<TemplateListModel> templateListModels=new ArrayList<>();

        templates.forEach(template -> {
            TemplateListModel m=new TemplateListModel();
            m.setId(template.getId());
            m.setName(template.getName());
            templateListModels.add(m);
        });
        model.addAttribute("list",templateListModels);
        return "/back/template_list";
    }

    /**
     * 根据模板ID获取模板详情
     * @param id    模板id
     * @param customerId 商户ID
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modifyTemplate")
    public String modifyTemplate(@CustomerId Long customerId, Long id, Model model) throws Exception{
        Template template=new Template();
        template.setCustomerId(customerId);
        if(id!=null){
            template=templateRepository.findOne(id);
        }
        model.addAttribute("template",template);
        return "/back/template_modify";
    }

    /**
     * 保存模板
     * @param template    模板
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveTemplate",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap saveTemplate(@CustomerId Long customerId,@RequestBody Template template) throws Exception{
        if(template.getCustomerId()==null){
            template.setCustomerId(customerId);
        }
        template=templateRepository.save(template);
        ModelMap modelMap=new ModelMap();
        modelMap.addAttribute("id",template.getId());
        return modelMap;
    }

    /**
     * 删除
     * @param id    模板id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delTemplate",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap delTemplate(@RequestParam Long id) throws Exception{
        Template template=templateRepository.findOne(id);
        template.setEnabled(false);
        templateRepository.save(template);

        return new ModelMap();
    }


}
