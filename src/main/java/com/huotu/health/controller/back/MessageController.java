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
import com.huotu.health.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("list",messages);
        model.addAttribute("pageNo",pageNo);
        model.addAttribute("title",title);
        return "/back/message_list";

    }

//    /**
//     * 根据资讯id,打开编辑页面,如果id为空则是新建
//     *
//     * @param id    资讯ID
//     * @param model 返回的model
//     * @return 修改资讯的视图模板
//     * @throws Exception
//     */
//    @RequestMapping(value = "/editCircle", method = RequestMethod.GET)
//    public String editCircle(Long id, Model model) throws Exception {
//        String view = "/admin/circle/modifyCircle";
//        Circle circle = null;
//        if (id != null) {
//            circle = circleRepository.findOne(id);
//        }
//        if (circle == null) {
//            circle = new Circle();
//        }
//        CircleListModel circleListModel = circleService.circleToDetailsCircleModel(circle);
//        model.addAttribute("circleListModel", circleListModel);
//        return view;
//    }
//
//    /**
//     * 保存资讯
//     *
//     * @param circleListModel 资讯的model
//     * @return 只要正常返回就说明保存成功
//     * @throws Exception
//     */
//    @RequestMapping(value = "saveCircle", method = RequestMethod.POST)
//    @ResponseBody
//    public ModelMap saveCircle(@CustomerId Long customerId, @RequestBody CircleListModel circleListModel) throws Exception {
//        ModelMap modelMap = new ModelMap();
//        circleListModel.setCustomerId(customerId);
//        if (circleListModel.getCircleId() == null) {
//            circleService.addCircle(circleListModel);
//        } else {
//            circleService.updateCircle(circleListModel);
//        }
//        return modelMap;
//    }


}
