package com.huotu.health.boot;

import com.huotu.common.api.OutputHandler;
import com.huotu.health.common.WebHandlerExceptionResolver;
import com.huotu.health.interceptor.CommonUserInterceptor;
import com.huotu.health.service.CustomerIdArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.util.List;


/**
 * mvc
 * Created by slt on 2016/1/12.
 */
@Configuration
@EnableWebMvc
public class MVCConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment environment;


    @Autowired
    private CustomerIdArgumentResolver customerIdArgumentResolver;


    @Bean
    CommonUserInterceptor commonUserInterceptor() {
        return new CommonUserInterceptor();
    }

    /**
     * 设置控制器方法参数化输出
     *
     * @param argumentResolvers
     */
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new OutputHandler());
        argumentResolvers.add(customerIdArgumentResolver);
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        super.addResourceHandlers(registry);
//        registry.addResourceHandler("/app/**/*", "/**/*.html")
//                .addResourceLocations("/app/", "/");
//    }

    /**
     * 拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(commonUserInterceptor())
                .addPathPatterns("/app/**");
    }



    /**
     *  允许访问静态资源
     * @param configurer    配置
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 添加自定义异常解析器
     * @param exceptionResolvers    解析器列表
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new WebHandlerExceptionResolver());
    }


    /**
     * 视图解析器
     * @param registry  视图解析器登记
     */
    public void configureViewResolvers(ViewResolverRegistry registry) {
        super.configureViewResolvers(registry);
        registry.viewResolver(viewResolver());
        registry.jsp();
    }

    /**
     * for upload
     * @return  文件上传
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

    /**
     * thymeleaf解析
     *
     * @return  thymeleaf解析器
     */
    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        SpringTemplateEngine engine = new SpringTemplateEngine();
        ServletContextTemplateResolver rootTemplateResolver = new ServletContextTemplateResolver();
        rootTemplateResolver.setPrefix("/");
        rootTemplateResolver.setSuffix(".html");
        rootTemplateResolver.setCharacterEncoding("UTF-8");

        if (environment.acceptsProfiles("test") || environment.acceptsProfiles("develop") ||
                environment.acceptsProfiles("development")) {
            rootTemplateResolver.setCacheable(false);
        }

        engine.setTemplateResolver(rootTemplateResolver);
        resolver.setTemplateEngine(engine);
        resolver.setOrder(100);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setContentType("text/html;charset=utf-8");
        return resolver;
    }


}
