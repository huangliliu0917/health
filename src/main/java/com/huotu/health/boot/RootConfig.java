package com.huotu.health.boot;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * root
 * Created by lgh on 2016/3/22.
 */

@Configuration
@ComponentScan(basePackages = {"com.huotu.health.service","com.huotu.health.controller"})
@EnableJpaRepositories(value = {"com.huotu.health.repository"})
@ImportResource(value = {"classpath:spring-jpa.xml"})
public class RootConfig {

}
