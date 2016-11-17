package com.huotu.health.interceptor;

import com.huotu.health.common.LoginType;
import com.huotu.health.common.PublicParameterHolder;
import com.huotu.health.common.PublicParameterModel;
import com.huotu.health.service.CommonConfigsService;
import com.huotu.health.service.CommonService;
import com.huotu.health.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

                //判断是否需要单点登录
                if(false){
                    //单点登录
                    return false;
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
