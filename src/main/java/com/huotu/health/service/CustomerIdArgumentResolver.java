/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.health.service;

import com.huotu.health.annotation.CustomerId;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by slt on 2016/10/31.
 */
@Component
public class CustomerIdArgumentResolver implements HandlerMethodArgumentResolver {
    Log log = LogFactory.getLog(CustomerIdArgumentResolver.class);


    @Autowired
    private CommonService commonService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(CustomerId.class) != null && (parameter.getParameterType().isAssignableFrom(Long.class));
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Long customerId=commonService.currentCustomerId(webRequest.getNativeRequest(HttpServletRequest.class));
        log.info("customerId:"+customerId);
        if(customerId==null){
            throw new Exception("have no customerId");
        }
        return customerId;
    }
}
