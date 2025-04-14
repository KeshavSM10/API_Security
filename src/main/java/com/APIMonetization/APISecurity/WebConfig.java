package com.APIMonetization.APISecurity;

import monetization.UsageInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private UsageInterceptor apiGatewayInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiGatewayInterceptor)
                .addPathPatterns("/api/**") 
                .excludePathPatterns("/api/apisecurity/public/**");
    }
}