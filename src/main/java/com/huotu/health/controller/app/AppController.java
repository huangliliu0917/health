package com.huotu.health.controller.app;

import com.huotu.health.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/11/28.
 */
@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    private MessageRepository messageRepository;

    @RequestMapping("/showMessageList")
    @ResponseBody
    public ModelMap showMessageList(@RequestParam Long customerId, Long lastId) throws Exception{
        ModelMap modelMap=new ModelMap();
        if(lastId==null){

        }

        return modelMap;
    }
}
