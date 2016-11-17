package com.huotu.health.service.impl;

import com.huotu.health.common.LoginType;
import com.huotu.health.common.StringHelper;
import com.huotu.health.service.CommonService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 公共服务实现
 * Created by slt on 2016/11/17
 */

@Service
public class CommonServiceImpl implements CommonService {


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
}
