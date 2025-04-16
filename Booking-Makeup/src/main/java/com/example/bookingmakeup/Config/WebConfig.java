package com.example.bookingmakeup.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ManagerAccessInterceptor())
                .addPathPatterns("/manager/**"); // Áp dụng cho toàn bộ /manager/*
        registry.addInterceptor(new AdminAccessInterceptor())
                .addPathPatterns("/admin/**");

        registry.addInterceptor(new MakeupArtistAccessInterceptor())
                .addPathPatterns("/makeup_artist/**");

        registry.addInterceptor(new ReceptionistAccessInterceptor())
                .addPathPatterns("/receptionist/**");
    }
}
