package com.example.finalproject.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * 로그인 성공 시 수행 로직
 */
@Component
@Slf4j
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("로그인 성공");
        String defaultUrl = "/";

        // ADMIN 권한 사용자인 경우 ADMIN 페이지 URL 설정.
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if(authority.getAuthority().equals("ROLE_ADMIN")){
                defaultUrl = "/admin";
            }
        }

        response.sendRedirect(defaultUrl);
    }
}