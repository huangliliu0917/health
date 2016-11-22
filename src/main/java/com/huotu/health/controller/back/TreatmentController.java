package com.huotu.health.controller.back;

import com.huotu.health.annotation.CustomerId;
import com.huotu.health.entity.Treatment;
import com.huotu.health.repository.TreatmentRepository;
import com.huotu.health.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private TreatmentService treatmentService;

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
     * 根据模板ID获取模板详情
     * @param id    模板id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modifyTreatment")
    public String modifyTreatment(Long id, Model model) throws Exception{
        Treatment treatment=new Treatment();
        if(id!=null){
            treatment=treatmentRepository.findOne(id);
        }
        model.addAttribute("treatment",treatment);
        return "/back/treatment_modify";
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
        treatmentRepository.save(treatment);
        return new ModelMap();
    }


}
