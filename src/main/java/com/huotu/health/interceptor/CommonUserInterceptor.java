package com.huotu.health.interceptor;

import com.huotu.health.common.LoginType;
import com.huotu.health.common.PublicParameterHolder;
import com.huotu.health.common.PublicParameterModel;
import com.huotu.health.entity.User;
import com.huotu.health.repository.UserRepository;
import com.huotu.health.service.CommonConfigsService;
import com.huotu.health.service.CommonService;
import com.huotu.health.service.SecurityService;
import com.huotu.health.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用用户拦截
 * 当用户是非app登录的情况下需要单点登录，巡回逻辑服务
 * 没有登录的情况下进行调整
 * Created by lgh on 2015/12/30.
 */
public class CommonUserInterceptor implements HandlerInterceptor {

    private static Log log = LogFactory.getLog(CommonUserInterceptor.class);
    @Autowired
    private CommonConfigsService commonConfigService;
    @Autowired
    private Environment env;
    @Autowired
    private CommonService commonService;
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //过滤器
        Long userId;

        //判断是app登录还是微信端登录
        LoginType loginType=commonService.getLoginType(request);
        switch (loginType){
            case app:
                userId=commonService.getUserIdByApp(request);
                break;
            case weChat:
                userId = userService.getUserId(request);

                String paramUserId = request.getParameter("mainUserId");

                log.debug("enter interceptor");

                if (!env.acceptsProfiles("develop")) {
                    String customerIdStr=request.getParameter("customerId");
                    if(customerIdStr==null){
                        customerIdStr=request.getParameter("customerid");
                    }
                    if(customerIdStr==null){
                        throw new Exception("未获取到商户ID");
                    }
                    Long customerId = Long.parseLong(customerIdStr);


                    Boolean toSSO = false;
                    //强制刷新用户
                    String forceRefresh = "0";
                    //强制授权
                    if (userId == null || userId == 0) {
                        toSSO = true;
                    } else if (!StringUtils.isEmpty(paramUserId) && !userId.toString().equals(paramUserId)) {
                        //用户切换 强制刷新
                        toSSO = true;
                        forceRefresh = "1";
                    } else {
                        //商家切换 强制刷新
                        User user = userRepository.findOne(userId);
                        if (!user.getMerchantId().equals(customerId)) {
                            toSSO = true;
                            forceRefresh = "1";
                        }

                    }

                    //进行单点登录
                    if (toSSO) {
                        //                log.info(userId);
//                log.info(paramUserId);
//                log.info(!userId.toString().equals(paramUserId));

                        //todo customerId为空
                        String redirectUrl = commonConfigService.getWebUrl() + "/back/auth?redirectUrl=" + URLEncoder.encode(request.getRequestURL()
                                + (StringUtils.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString()), "utf-8");

                        //生成sign
                        Map<String, String> map = new HashMap<>();
                        map.put("customerId", customerId.toString());
                        map.put("redirectUrl", redirectUrl);
                        map.put("forceRefresh", forceRefresh);
                        String sign = securityService.getMapSign(map);

                        //生成toUrl
                        String toUrl = "";
                        for (String key : map.keySet()) {
                            toUrl += "&" + key + "=" + URLEncoder.encode(map.get(key), "utf-8");
                        }
                        toUrl = commonConfigService.getAuthWebUrl() + "/api/login?" + (toUrl.length() > 0 ? toUrl.substring(1) : "");

//                log.info("interceptor " + toUrl + " " + sign);

                        response.sendRedirect(toUrl + "&sign=" + sign);
                        return false;
                    }
                }


                break;
            default:
                userId=null;
        }

        PublicParameterModel publicParameterModel = new PublicParameterModel();
        publicParameterModel.setUserId(userId);
        PublicParameterHolder.set(publicParameterModel);

        //        todo 调用王明的找回逻辑服务
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
