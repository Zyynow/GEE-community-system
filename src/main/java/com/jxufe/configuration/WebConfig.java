package com.jxufe.configuration;

import com.jxufe.interceptor.LoginInterceptor;
import com.jxufe.interceptor.MyInterceptor;
import com.jxufe.interceptor.OtherInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 设定为配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/admin/**") //拦截所有/admin/...的请求
        .excludePathPatterns("/admin", "/admin/login", "/admin/register",
                "/admin/login/success", "/admin/register/success");

        registry.addInterceptor(new MyInterceptor()).addPathPatterns(
                "/about/**",
                "/feedback/**",
                "/user_index/**",
                "/user_types/**",
                "/user_search/**",
                "/user_resource/**",
                "/user_forum/**",
                "/user_other_about/**",
                "/edit/**",
                "/forum/join/**",
                "/fblog/input/**",
                "/archives/**",
                "/consumption_record/**"
                );

//        registry.addInterceptor(new OtherInterceptor())
//                .addPathPatterns("/index", "/forum", "/types", "/search", "/resource");
    }
}
