/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.health.common;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理服务工具类，包括验证app登录的签名
 * Created by slt on 2016/11/17.
 */
public class StringHelper {

    private static String appSecret = "1165a8d240b29af3f418b8d10599d0da";

    public static final String SECRET="08afd6f9ae0c6017d105b4ce580de885";


    /**
     * 通过request 返回一个签名字符串
     *
     * @param request request请求
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getSign(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> resultMap = new TreeMap<String, String>();
        resultMap.put("appSecret", appSecret);
        Map map = request.getParameterMap();
        for (Object key : map.keySet()) {
            resultMap.put(key.toString(), request.getParameter(key.toString()));
        }

        StringBuilder strB = new StringBuilder();

        resultMap.keySet().stream().filter(key -> !"sign".equals(key)).forEach(key -> strB.append(resultMap.get(key)));

        return DigestUtils.md5DigestAsHex(strB.toString().getBytes("UTF-8")).toLowerCase();
    }

    /**
     * 返回app信息
     * @param userAgent 字符串
     * @return
     */
    public static String[] getRequestAppInfo(String userAgent){
        if(StringUtils.isEmpty(userAgent)){
            return null;
        }
        Pattern p = Pattern.compile(";hottec:([^;]+)");
        Matcher matcher=p.matcher(userAgent);
        StringBuilder builder=new StringBuilder();
        while (matcher.find()){
            builder.append(matcher.group(1));
        }
        return builder.toString().split(":");

    }

    /**
     * 判断签名是否正确
     * @param data  参数
     * @return
     */
    public static boolean isTrueSign(String[] data) {
        for(String s:data){
            if(StringUtils.isEmpty(s)){
                return false;
            }
        }
        StringBuilder s=new StringBuilder();
        for(int i=1;i<data.length;i++){
            s.append(data[i]);
        }
        s.append(SECRET);
        String sign;
        try{
            sign= DigestUtils.md5DigestAsHex(s.toString().getBytes("UTF-8")).toLowerCase();
        }catch (UnsupportedEncodingException ex){
            return false;
        }
        return sign.equals(data[0]);
    }
}