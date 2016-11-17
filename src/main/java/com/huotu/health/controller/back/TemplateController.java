package com.huotu.health.controller.back;

import com.huotu.health.annotation.CustomerId;
import com.huotu.health.entity.Template;
import com.huotu.health.model.TemplateListModel;
import com.huotu.health.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        List<Template> templates=templateRepository.findByCustomerId(customerId);

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
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modifyTemplate")
    public String modifyTemplate(@RequestParam(required = true) Long id, Model model) throws Exception{
        Template template=templateRepository.findOne(id);
        model.addAttribute("template",template);
        return "/back/template_modify";
    }

    /**
     * 保存模板
     * @param template    模板
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveTemplate")
    public String saveTemplate(Template template) throws Exception{
        return "";
    }


}
