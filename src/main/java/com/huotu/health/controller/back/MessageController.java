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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
        Page<Message> messages;

        if(StringUtils.isEmpty(title)){
            messages=messageRepository.findByCustomerIdAndEnabled(customerId,true,new PageRequest(pageNo,20));
        }else {
            messages=messageRepository.findByCustomerIdAndEnabledAndTitleLike(customerId,true,"%"+title+"%",new PageRequest(pageNo,20));
        }
        List<MessageListModel> models=messageService.getMessagesModel(messages.getContent());
        model.addAttribute("list",models);
        model.addAttribute("pageNo",pageNo);
        model.addAttribute("pageSize",messages.getNumberOfElements());
        model.addAttribute("totalNumber",messages.getTotalElements());
        model.addAttribute("totalPage",messages.getTotalPages());
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
        if(message.isPutAway()){
            message.setPutAwayDate(new Date());
        }else {
            message.setPutAwayDate(null);
        }

        messageRepository.save(message);
        return new ModelMap();

    }

    @RequestMapping(value = "/delMessage",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap  delMessage(@RequestParam Long id) throws Exception{

        Message message=messageRepository.findOne(id);
        message.setEnabled(false);
        messageRepository.save(message);
        return new ModelMap();
    }

    /**
     * 上下架
     * @param id
     * @param putAway
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/putAwayMessage",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap  putAwayMessage(@RequestParam Long id,@RequestParam Boolean putAway) throws Exception{
        Message message=messageRepository.findOne(id);

        message.setPutAway(putAway);
        if(!putAway){
            message.setPutAwayDate(null);
        }else {
            message.setPutAwayDate(new Date());
        }
        messageRepository.save(message);
        return new ModelMap();
    }



    /**
     * 置顶
     * @param id
     * @param stick
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/stickMessage",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap  stickMessage(@RequestParam Long id,@RequestParam Boolean stick) throws Exception{
        Message message=messageRepository.findOne(id);

        message.setStick(stick);

        messageRepository.save(message);
        return new ModelMap();
    }

}
