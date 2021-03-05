package com.example.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/read").setViewName("/contents/indexform");
        registry.addViewController("/signIn").setViewName("/login/signIn");
        registry.addViewController("/admin").setViewName("/login/admin/adminform");
        registry.addViewController("/signup").setViewName("/login/signup");
    }
}
