package com.jxufe.configuration;

import com.jxufe.filter.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        //指定过滤器注册器
        FilterRegistrationBean bean = new FilterRegistrationBean();
        //设置过滤器对象
        bean.setFilter(new Filter());
        //指定过滤url地址
        bean.addUrlPatterns("/admin", "/login", "/admin/login", "/admin/");
        return bean;
    }
}
