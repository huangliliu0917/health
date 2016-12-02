package com.huotu.health.controller.app;

import com.huotu.health.common.PublicParameterHolder;
import com.huotu.health.entity.*;
import com.huotu.health.model.FormAppListModel;
import com.huotu.health.model.MessageListModel;
import com.huotu.health.model.TreatmentListModel;
import com.huotu.health.repository.*;
import com.huotu.health.service.MessageService;
import com.huotu.health.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/28.
 */
@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private TemplateGroupRepository templateGroupRepository;

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private VipUserRepository vipUserRepository;


    /**
     * 资讯列表
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/showzxlb")
    public String showzxlb(Model model) throws Exception{
        model.addAttribute("customerId",getCustomerId());
        return "/app/zxlb";
    }

    /**
     * 健康管理表单列表
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/showjkgl")
    public String showjkgl(Model model) throws Exception{
        model.addAttribute("customerId",getCustomerId());
        return "/app/jkgl";
    }

    /**
     * 用户疗程管理列表
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/showxxcj")
    public String showxxcj(Model model) throws Exception{
        Long userId= PublicParameterHolder.get().getUserId();
        VipUser vipUser=vipUserRepository.findByUser_Id(userId);
        if(vipUser==null){
            model.addAttribute("errorMessage","您没有权限哦！");
            return "/html/error";
        }
        model.addAttribute("customerId",getCustomerId());
        return "/app/xxcj";
    }

    private Long getCustomerId(){
        Long userId= PublicParameterHolder.get().getUserId();
        User user=userRepository.findOne(userId);
        return user.getMerchantId();
    }



    @RequestMapping("/showMessageList")
    @ResponseBody
    public ModelMap showMessageList(@RequestParam Long customerId, Long lastId) throws Exception{
        ModelMap modelMap=new ModelMap();
        List<Message> messageList=new ArrayList<>();
        if(lastId==null||lastId<=0){
            messageList=messageRepository.findTop20ByCustomerIdAndEnabledAndPutAwayAndStickOrderByIdDesc(
                    customerId,true,true,false);

        }else {
            messageList=messageRepository.findTop20ByCustomerIdAndEnabledAndPutAwayAndStickAndIdLessThanOrderByIdDesc(
                    customerId,true,true, false,lastId);
        }

        List<MessageListModel> models=messageService.getMessagesModel(messageList);
        modelMap.addAttribute("list",models);

        return modelMap;
    }


    /**
     * 置顶文章
     * @param customerId
     * @return
     * @throws Exception
     */
    @RequestMapping("/showStickMessageList")
    @ResponseBody
    public ModelMap showStickMessageList(@RequestParam Long customerId) throws Exception{
        ModelMap modelMap=new ModelMap();
        List<Message> messageList=messageRepository.findByCustomerIdAndEnabledAndPutAwayAndStickOrderByIdDesc(customerId,true,true,true);
        List<MessageListModel> models=messageService.getMessagesModel(messageList);
        modelMap.addAttribute("list",models);
        return modelMap;
    }


    @RequestMapping("/showMessage")
    public String showMessage(@RequestParam Long id, Long customerId, Model model)throws Exception{

        Message message=messageRepository.findOne(id);

        model.addAttribute("message",message);
        return "/app/zxxq";
    }


    @RequestMapping("/showTreatmentList")
    @ResponseBody
    public ModelMap showTreatmentList(@RequestParam Long customerId) throws Exception{
        ModelMap modelMap=new ModelMap();
        Long userId= PublicParameterHolder.get().getUserId();

        List<Treatment> treatments=treatmentRepository.findByCustomerIdAndEnabledAndUserIdOrderByIdDesc(customerId,true,userId);

        List<TreatmentListModel> models=new ArrayList<>();

        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        treatments.forEach(treatment->{
            TreatmentListModel model=new TreatmentListModel();
            model.setId(treatment.getId());
            model.setDate(format.format(treatment.getDate()));
            model.setName(treatment.getName());
            models.add(model);
        });

        modelMap.addAttribute("list",models);

        return modelMap;
    }



    /**
     *
     * @param id    疗程ID
     * @param customerId
     * @return
     * @throws Exception
     */
    @RequestMapping("/showFormList")
    public String showFormList(Long id,Long customerId,Model model)throws Exception{

        List<Form> forms=formRepository.findByTreatment_IdOrderByStep(id);

        List<FormAppListModel> models=new ArrayList<>();

        forms.forEach(form -> {
            FormAppListModel formAppListModel=new FormAppListModel();
            formAppListModel.setId(form.getId());
            formAppListModel.setName(form.getName());
            models.add(formAppListModel);
        });
        model.addAttribute("list",models);
        model.addAttribute("customerId",customerId);
        return "/app/jkgl";
    }

    @RequestMapping("/showForm")
    public String showForm(@RequestParam Long id, Long customerId, Model model)throws Exception{

        Form form=formRepository.findOne(id);



        model.addAttribute("form",form);
        return "/app/form_detail";
    }



}
