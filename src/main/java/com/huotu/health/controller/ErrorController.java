package com.huotu.health.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 错误处理
 * Created by slt on 2016/11/23.
 */
@Controller
@RequestMapping("/html")
public class ErrorController {

    @RequestMapping("/error")
    public String errorEdit(String errorMessage, Model model) throws Exception{

        model.addAttribute("errorMessage",errorMessage);

        return "/html/error";
    }


}
