/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.health.controller.back;

import com.huotu.health.annotation.CustomerId;
import com.huotu.health.entity.Message;
import com.huotu.health.model.MessageListModel;
import com.huotu.health.repository.MessageRepository;
import com.huotu.health.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 资讯
 * Created by slt on 2016/10/11.
 */
@Controller
@RequestMapping("/back")
public class MessageController {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;


    /**
     * 根据查询model返回资讯列表
     *
     * @param customerId        商户ID
     * @return 分页信息
     * @throws Exception
     */
    @RequestMapping(value = "/showMessageList")
    public String showMessageList(@CustomerId Long customerId,
                                  String title,
                                  Integer pageNo, Model model) throws Exception {
        if(pageNo==null){
            pageNo=0;
        }
        List<Message> messages;

        if(StringUtils.isEmpty(title)){
            messages=messageRepository.findByCustomerId(customerId,new PageRequest(pageNo,20));
        }else {
            messages=messageRepository.findByCustomerIdAndTitleLike(customerId,"%"+title+"%",new PageRequest(pageNo,20));
        }
        List<MessageListModel> models=messageService.getMessagesModel(messages);
        model.addAttribute("list",models);
        model.addAttribute("pageNo",pageNo);
        model.addAttribute("title",title);
        return "/back/message_list";

    }

    @RequestMapping("/modifyMessage")
    public String modifyMessage(@CustomerId Long customerId,Long id, Model model) throws Exception{
        Message message=new Message();
        message.setCustomerId(customerId);
        if(id!=null){
            message=messageRepository.findOne(id);
        }
        model.addAttribute("message",message);
        return "/back/message_modify";
    }


    @RequestMapping(value = "/saveMessage")
    @ResponseBody
    public ModelMap  saveMessage(@RequestBody Message message) throws Exception{

        messageRepository.save(message);
        return new ModelMap();

    }

}
