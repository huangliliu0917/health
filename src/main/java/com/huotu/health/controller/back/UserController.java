package com.huotu.health.controller.back;

import com.huotu.health.annotation.CustomerId;
import com.huotu.health.entity.VipUser;
import com.huotu.health.repository.UserRepository;
import com.huotu.health.repository.VipUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板
 * Created by slt on 2016/11/17.
 */
@Controller
@RequestMapping("/back")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VipUserRepository vipUserRepository;

    /**
     * 根据商户ID获取模板列表
     * @param customerId    商户ID
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showVipUserList")
    public String showVipUserList(@CustomerId Long customerId, String name, Model model) throws Exception{
        List<VipUser> users=new ArrayList<>();
        if(name!=null){
            users=vipUserRepository.findByMerchantIdAndWxNickNameLike(customerId,"%"+name+"%",new PageRequest(0,20));//todo 读取vip用户
        }
        model.addAttribute("list",users);
        return "/back/vip_user_list";
    }


    /**
     * 用户列表
     * @param customerId
     * @param userName
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showUserList")
    public String showUserList(@CustomerId Long customerId,Integer pageNo, String userName, Model model) throws Exception{

        if(pageNo==null){
            pageNo=0;
        }
        List<Object> users;

        if(StringUtils.isEmpty(userName)){
            users=userRepository.findUser(customerId,new PageRequest(pageNo,20));
        }else {
            users=userRepository.findUserByName(customerId,"%"+userName+"%",new PageRequest(pageNo,20));
        }
        model.addAttribute("list",users);
        model.addAttribute("pageNo",pageNo);
        model.addAttribute("userName",userName);
        return "/back/user_list";
    }


}
