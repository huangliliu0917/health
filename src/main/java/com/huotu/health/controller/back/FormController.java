package com.huotu.health.controller.back;

import com.huotu.health.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 表单
 * Created by slt on 2016/11/17.
 */
@Controller
@RequestMapping("/back")
public class FormController {

    @Autowired
    private FormRepository formRepository;

    /**
     * 保存表单
     * @param id
     * @param content
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveForm",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap saveForm(@RequestParam Long id, @RequestParam String content) throws Exception{
        formRepository.updateFormContent(content,id);
        return new ModelMap();
    }

}
