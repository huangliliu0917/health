package com.huotu.health.controller.app;

import com.huotu.health.entity.Form;
import com.huotu.health.entity.Message;
import com.huotu.health.entity.TemplateGroup;
import com.huotu.health.entity.Treatment;
import com.huotu.health.model.MessageListModel;
import com.huotu.health.repository.FormRepository;
import com.huotu.health.repository.MessageRepository;
import com.huotu.health.repository.TemplateGroupRepository;
import com.huotu.health.repository.TreatmentRepository;
import com.huotu.health.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("/showMessageList")
    @ResponseBody
    public ModelMap showMessageList(@RequestParam Long customerId, Long lastId) throws Exception{
        ModelMap modelMap=new ModelMap();
        List<Message> messageList=new ArrayList<>();
        if(lastId==null){
            messageList=messageRepository.findTop20ByCustomerIdOrderByIdDesc(customerId);

        }else {
            messageList=messageRepository.findTop20ByCustomerIdAndIdLessThanOrderByIdDesc(customerId, lastId);
        }

        List<MessageListModel> models=messageService.getMessagesModel(messageList);
        modelMap.addAttribute("list",models);

        return modelMap;
    }


    @RequestMapping("/showMessage")
    public String showMessage(@RequestParam Long id, Long customerId, Model model)throws Exception{

        Message message=messageRepository.findOne(id);

        model.addAttribute("message",message);
        return "";
    }

    @RequestMapping("/showFormList")
    public String showFormList(@RequestParam Long id,Long customerId, Model model)throws Exception{

        List<Form> forms=formRepository.findByTreatment_IdOrderByStep(id);

        return "";
    }

    @RequestMapping("/showForm")
    public String showForm(@RequestParam Long id, Long customerId, Model model)throws Exception{

        Form form=formRepository.findOne(id);

        model.addAttribute("form",form);
        return "";
    }



}
