package com.huotu.health.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户服务
 * Created by slt on 2016/11/17.
 */
public interface UserService {

    /**
     * 获取用户Id
     * 从加密的cookie中取出数据
     *
     * @param request
     * @return
     */
    Long getUserId(HttpServletRequest request) throws Exception;

    /**
     * 对用户的id进行加密
     * 放入cookie中
     *
     */
    void setUserId(Long userId, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 将登录类型type保存到cookie中去
     * @param loginType     0：微信，1 app
     * @param request       请求
     * @param response      响应
     * @throws Exception
     */
    void setLoginType(HttpServletRequest request, HttpServletResponse response, String loginType) throws Exception;

    /**
     * 获取登录类型
     * @param request
     * @return
     * @throws Exception
     */
    String getLoginType(HttpServletRequest request) throws Exception;

}
