package com.huotu.health.controller.back;

import com.huotu.health.annotation.CustomerId;
import com.huotu.health.entity.User;
import com.huotu.health.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    /**
     * 根据商户ID获取模板列表
     * @param customerId    商户ID
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showUserList")
    public String showUserList(@CustomerId Long customerId, String name, Model model) throws Exception{
        List<User> users=new ArrayList<>();
        if(name!=null){
            users=userRepository.findByMerchantIdAndWxNickNameLike(customerId,"%"+name+"%",new PageRequest(0,20));
        }
        model.addAttribute("list",users);
        return "/back/user_list";
    }
}
