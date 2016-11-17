package com.huotu.health.service.impl;

import com.huotu.health.common.LoginType;
import com.huotu.health.common.StringHelper;
import com.huotu.health.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 公共服务实现
 * Created by slt on 2016/11/17
 */

@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private Environment environment;
    @Override
    public Long getUserIdByApp(HttpServletRequest request) {
        String userAgent=request.getHeader("User-Agent");
        String[] data= StringHelper.getRequestAppInfo(userAgent);
        return Long.parseLong(data[1]);
    }

    @Override
    public LoginType getLoginType(HttpServletRequest request) {
        String userAgent=request.getHeader("User-Agent");
        String[] data= StringHelper.getRequestAppInfo(userAgent);
        if(data!=null&&StringHelper.isTrueSign(data)){
            return LoginType.app;
        }else {
            return LoginType.weChat;
        }
    }

    @Override
    public Long currentCustomerId(HttpServletRequest request) {
        Long defaultValue = null;
        if (environment.acceptsProfiles("develop","development") && !environment.acceptsProfiles("no_mock_customerid")) {
            defaultValue = 4471L;
        } else if (environment.acceptsProfiles("test","staging")&& !environment.acceptsProfiles("no_mock_customerid")) {
            defaultValue = 3447L;
        }

        if (request == null)
            return defaultValue;

        HttpSession session = request.getSession(true);
        Long savedCustomerId = (Long) session.getAttribute("__savedCustomerId");
        if (savedCustomerId != null) {
            defaultValue = savedCustomerId;
        }

        Cookie roleCookie = getCookie(request, "RoleID");
        if (roleCookie == null)
            return defaultValue;

        if ("-1".equals(roleCookie.getValue())) {
            String value = request.getParameter("customerid");
            if (value == null)
                return defaultValue;
            try {
                Long longValue = Long.parseLong(value);
                session.setAttribute("__savedCustomerId", longValue);
                return longValue;
            } catch (NumberFormatException ex) {
                return defaultValue;
            }
        }

        if ("-2".equals(roleCookie.getValue())) {
            Cookie userCookie = getCookie(request, "UserID");
            if (userCookie == null)
                return defaultValue;
            String value = userCookie.getValue();
            if (value == null)
                return defaultValue;
            try {
                Long longValue = Long.parseLong(value);
                session.setAttribute("__savedCustomerId", longValue);
                return longValue;
            } catch (NumberFormatException ex) {
                return defaultValue;
            }
        }

        return defaultValue;
    }

    private Cookie getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() == null)
            return null;
        for (Cookie cookie : request.getCookies()) {
            if (name.equals(cookie.getName()))
                return cookie;
        }
        return null;
    }
}
