package com.huotu.health.controller.back;

import com.huotu.common.base.HttpHelper;
import com.huotu.health.annotation.CustomerId;
import com.huotu.health.entity.User;
import com.huotu.health.entity.VipUser;
import com.huotu.health.repository.UserRepository;
import com.huotu.health.repository.VipUserRepository;
import com.huotu.health.service.CommonConfigsService;
import com.huotu.health.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CommonConfigsService commonConfigsService;

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

    /**
     * 操作用户权限
     * @param userId
     * @param type  0：降级，1：升级
     * @return
     * @throws Exception
     */
    @RequestMapping("/modifyUserLevel")
    @ResponseBody
    public ModelMap modifyUserLevel(Long userId,Integer type) throws Exception{

        ModelMap modelMap=new ModelMap();
        VipUser vipUser=vipUserRepository.findByUser_Id(userId);
        switch (type){
            case 0:
                if(vipUser!=null){
                    vipUserRepository.delete(vipUser.getId());
                    modelMap.addAttribute("status",200);
                }
                break;
            case 1:
                if(vipUser==null){
                    vipUser=new VipUser();
                    User user=userRepository.findOne(userId);
                    vipUser.setUser(user);
                    vipUser.setMerchantId(user.getMerchantId());
                    vipUserRepository.save(vipUser);
                    modelMap.addAttribute("status",200);
                }
                break;
        }
        return modelMap;
    }


    /**
     * 安全授权返回
     * 授权成功后，进行passport安全校验，成功后设置cookie
     *
     * @param token
     * @param sign
     * @param code
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/auth")
    public String auth(String token, String sign, Integer code, String redirectUrl, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        //进行校验
        if (sign == null || !sign.equals(securityService.getSign(request))) {
            return "redirect:/html/error";
        }


        if (code == 1) {
            //进行授权校验
            //生成sign
            Map<String, String> map = new HashMap<>();
            map.put("token", token);
            String toSign = securityService.getMapSign(map);
            //生成toUrl

            String toUrl = "";
            for (String key : map.keySet()) {
                toUrl += "&" + key + "=" + URLEncoder.encode(map.get(key), "utf-8");
            }

            toUrl = commonConfigsService.getAuthWebUrl() + "/api/check?" + (toUrl.length() > 0 ? toUrl.substring(1) : "");
            String responseText = HttpHelper.getRequest(toUrl + "&sign=" + toSign);


//            if (JsonPath.read(responseText, "$.resultCode").equals(1)) {
//                Long userId = Long.parseLong(JsonPath.read(responseText, "$.resultData.data").toString());
//                userService.setUserId(userId, request, response);
//                log.debug("get userId and save in cookie");
//                return "redirect:" + redirectUrl;
//            }
        }

        return "redirect:/html/error";
    }


}
