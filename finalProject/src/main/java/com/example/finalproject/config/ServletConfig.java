package com.example.finalproject.config;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;

public class ServletConfig {
    @Bean
    public ServletListenerRegistrationBean<SessionListener> sessionListener() {
        ServletListenerRegistrationBean<SessionListener> listenerBean =
                new ServletListenerRegistrationBean<>();
        listenerBean.setListener(new SessionListener());
        return listenerBean;
    }
}
