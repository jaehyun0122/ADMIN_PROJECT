package com.example.finalproject.config;

import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
    @Value("${server.servlet.session.timeout}")
    private int sessionTime;
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setMaxInactiveInterval(sessionTime);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // 세션이 만료될 때 실행될 로직
        // 알림창을 띄우는 JavaScript 코드
        String alertScript = "swal('세션이 만료되었습니다.', '로그인 페이지로 이동합니다.', 'info').then(function() { window.location.href = '/login'; });";
        se.getSession().setAttribute("alertScript", alertScript);
    }
}
