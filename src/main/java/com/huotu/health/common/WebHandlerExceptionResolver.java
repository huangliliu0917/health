package com.huotu.health.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 异常处理解析器
 * Created by slt on 2016/3/31.
 */
public class WebHandlerExceptionResolver implements HandlerExceptionResolver {

    Log log = LogFactory.getLog(WebHandlerExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String requestURI = request.getRequestURI().substring(request.getContextPath().length());
        if (requestURI.startsWith("/app/")) {

            String message ="";
            try {
                throw ex;
            } catch (Exception e) {
                log.error("web request error", e);
                message = e.getMessage();
            }

            try {
                return new ModelAndView("redirect:/html/error?errorMessage="+ URLEncoder.encode(message,"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }else {
            try {
                throw ex;
            } catch (Exception e) {
                log.error("web request error", e);
            }
        }
        return null;
    }
}
