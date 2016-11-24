package com.huotu.health.controller.back;

import com.huotu.health.annotation.CustomerId;
import com.huotu.health.entity.Form;
import com.huotu.health.entity.Template;
import com.huotu.health.entity.TemplateGroup;
import com.huotu.health.entity.Treatment;
import com.huotu.health.repository.FormRepository;
import com.huotu.health.repository.TemplateGroupRepository;
import com.huotu.health.repository.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 疗程
 * Created by slt on 2016/11/17.
 */
@Controller
@RequestMapping("/back")
public class TreatmentController {
    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private TemplateGroupRepository templateGroupRepository;

    @Autowired
    private FormRepository formRepository;

    /**
     * 获取疗程列表
     * @param customerId    商户ID
     * @param pageNo        第几页
     * @param userName      用户名字
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showTreatmentList")
    public String showTreatmentList(@CustomerId Long customerId,
                                    String userName,
                                    Integer pageNo, Model model) throws Exception{
        if(pageNo==null){
            pageNo=0;
        }
        List<Treatment> treatments;

        if(StringUtils.isEmpty(userName)){
            treatments=treatmentRepository.findByCustomerId(customerId,new PageRequest(pageNo,20));
        }else {
            treatments=treatmentRepository.findByCustomerIdAndWxNickNameLike(customerId,"%"+userName+"%",new PageRequest(pageNo,20));
        }
        model.addAttribute("list",treatments);
        model.addAttribute("pageNo",pageNo);
        model.addAttribute("userName",userName);
        return "/back/treatment_list";
    }

    /**
     * 根据疗程ID获取疗程详情
     * @param id    疗程id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modifyTreatment")
    public String modifyTreatment(@CustomerId Long customerId,Long id, Model model) throws Exception{
        Treatment treatment=new Treatment();
        treatment.setCustomerId(customerId);
        if(id!=null){
            treatment=treatmentRepository.findOne(id);
        }
        model.addAttribute("treatment",treatment);
        return "/back/treatment_modify";
    }


    /**
     * 查看某个用户的疗程的具体流程
     * @param id
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/showForms")
    public String showForms(@RequestParam Long id, Model model) throws Exception{
        List<Form> forms=formRepository.findByTreatment_IdOrderByStep(id);
        model.addAttribute("list",forms);
        return "/back/form_list";
    }

    /**
     * 保存疗程
     * @param treatment    疗程
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveTreatment",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap saveTreatment(@CustomerId Long customerId, @RequestBody Treatment treatment) throws Exception{
        if(treatment.getCustomerId()==null){
            treatment.setCustomerId(customerId);
        }
        treatment=treatmentRepository.save(treatment);


        //创建用户治疗过程
        TemplateGroup templateGroup=templateGroupRepository.findOne(treatment.getTemplateGroupId());
        List<Template> templates=templateGroup.getTemplates();

        List<Form> forms=new ArrayList<>();
        for(int i=0;i<templates.size();i++){
            Template t=templates.get(i);
            Form form=new Form();
            form.setName(t.getName());
            form.setContent(t.getContent());
            form.setStatus(0);
            form.setStep(i);
            form.setTreatment(treatment);
//            form.setUserId(treatment.getUserId());
            form.setDate(new Date());
            forms.add(form);
        }
        formRepository.save(forms);
        return new ModelMap();
    }


}
