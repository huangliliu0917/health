package com.huotu.health.controller.back;

import com.huotu.health.annotation.CustomerId;
import com.huotu.health.entity.Form;
import com.huotu.health.entity.Template;
import com.huotu.health.entity.TemplateGroup;
import com.huotu.health.entity.Treatment;
import com.huotu.health.model.TreatmentViewModel;
import com.huotu.health.repository.FormRepository;
import com.huotu.health.repository.TemplateGroupRepository;
import com.huotu.health.repository.TreatmentRepository;
import com.huotu.health.service.TemplateGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Autowired
    private TemplateGroupService templateGroupService;

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
        Page<Treatment> treatments;

        if(StringUtils.isEmpty(userName)){
            treatments=treatmentRepository.findByCustomerIdAndEnabledOrderByIdDesc(customerId,true,new PageRequest(pageNo,20));
        }else {
            treatments=treatmentRepository.findByCustomerIdAndEnabledAndWxNickNameLikeOrderByIdDesc(customerId,true,"%"+userName+"%",new PageRequest(pageNo,20));
        }
        model.addAttribute("list",treatments.getContent());
        model.addAttribute("pageNo",pageNo);
        model.addAttribute("pageSize",treatments.getNumberOfElements());
        model.addAttribute("totalNumber",treatments.getTotalElements());
        model.addAttribute("totalPage",treatments.getTotalPages());
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
    public ModelMap saveTreatment(@CustomerId Long customerId, @RequestBody TreatmentViewModel treatment) throws Exception{

        List<Treatment> list = new ArrayList<>();

        for (String userId: treatment.getUserId().split(",")) {
            if(!StringUtils.isEmpty(userId))
            {
                Treatment model = new Treatment();
                model.setName(treatment.getName());
                model.setUserId(Long.parseLong(userId));
                model.setWxNickName(treatment.getWxNickName());
                model.setTemplateGroupId(treatment.getTemplateGroupId());
                model.setCustomerId(treatment.getCustomerId());
                if(model.getCustomerId()==null){
                    model.setCustomerId(customerId);
                }

                model.setDate(new Date());
                list.add(model);
            }
        }

        list = treatmentRepository.save(list);




        //创建用户治疗过程
        TemplateGroup templateGroup=templateGroupRepository.findOne(treatment.getTemplateGroupId());
        List<Template> templates=templateGroupService.getTemplate(templateGroup.getTemplateIds());

        for(Treatment item:list) {
            List<Form> forms = new ArrayList<>();
            for (int i = 0; i < templates.size(); i++) {
                Template t = templates.get(i);
                Form form = new Form();
                form.setName(t.getName());
                form.setContent(t.getContent());
                form.setStatus(0);
                form.setStep(i);
                form.setTreatment(item);
                form.setDate(new Date());
                forms.add(form);
            }
            formRepository.save(forms);
        }
        return new ModelMap();
    }

    @RequestMapping(value = "/delTreatment",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap delTreatment(@RequestParam Long id) throws Exception{

        Treatment treatment=treatmentRepository.findOne(id);
        treatment.setEnabled(false);
        treatmentRepository.save(treatment);
        return new ModelMap();
    }


}
