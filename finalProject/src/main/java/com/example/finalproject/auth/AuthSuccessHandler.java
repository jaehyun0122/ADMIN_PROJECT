package com.example.finalproject.auth;

import com.example.finalproject.dto.user.UserDto;
import com.example.finalproject.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 로그인 성공 시 수행 로직
 */
@Component
@RequiredArgsConstructor
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(AuthSuccessHandler.class);
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String defaultUrl = "/";

        // 사용자 최근 접속일 설정
        // CustomAuthenticationProvider 에서 계정 유효성 체크 진행
        Map<String, Object> reqData = new HashMap<>();
        reqData.put("email", authentication.getPrincipal().toString());
        reqData.put("lastLoginDate", LocalDateTime.now());
        userMapper.updateLastLoginDate(reqData);

        UserDto userProfile = userMapper.getUserProfile(authentication.getPrincipal().toString());
        logger.info(">>> 로그인 유저");
        logger.info(userProfile.getName()+","+userProfile.getPhoneNo());
        logger.info("<<< 로그인 유저");

        // 비밀번호 변경 후 90일 지났으면 변경 페이지로
        if(!userProfile.isCredentialsNonExpired()){
            String message = URLEncoder.encode( "비밀번호 변경일로 90일이 지났습니다. 비밀번호를 변경해 주세요.", "UTF-8");
            defaultUrl = "/user/modify/password?message="+message;
        }else{
            // ADMIN 권한 사용자인 경우 ADMIN 페이지 URL 설정.
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if(authority.getAuthority().equals("ROLE_ADMIN")){
                    defaultUrl = "/admin";
                }
            }
        }

        response.sendRedirect(defaultUrl);
    }
}