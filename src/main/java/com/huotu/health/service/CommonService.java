package com.huotu.health.service;

import com.huotu.health.common.LoginType;

import javax.servlet.http.HttpServletRequest;

/**
 * 公共服务
 * Created by slt on 2016/11/17
 */
public interface CommonService {


    /**
     * app登录获取userId
     * @param request   request请求
     * @return
     */
    Long getUserIdByApp(HttpServletRequest request);

    /**
     * 根据request请求获取登录类型
     * @param request   request请求
     * @return
     */
    LoginType getLoginType(HttpServletRequest request);

    /**
     * 获取商户ID
     * @param request
     * @return
     */
    Long currentCustomerId(HttpServletRequest request);


}
