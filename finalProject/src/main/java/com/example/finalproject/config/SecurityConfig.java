package com.example.finalproject.config;

import com.example.finalproject.auth.AuthFailHandler;
import com.example.finalproject.auth.AuthSuccessHandler;
import com.example.finalproject.auth.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomAuthenticationProvider authenticationProvider;
    private final AuthSuccessHandler successHandler;
    private final AuthFailHandler failHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/");
        http
            .authorizeRequests()
            .antMatchers( "/login","/register","/email/check").permitAll() // 해당 경로 모두 접근 허용
            .antMatchers("/assets/**").permitAll() // 해당 경로 모두 접금 허용
            .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // /admin 요청은 어드민 권한 사용자만
            .anyRequest().access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')") // 나머지 요청들은 USER 이상 권한을 가지고 있어야 접근할 수 있다.
                .and()
            .formLogin()
            .usernameParameter("email")
            .loginPage("/login") // security에서 제공하는 로그인 페이지 안 쓰려면 controller에 등록한 url 써주면 됨.
            .successHandler(successHandler) // 로그인 성공 시 실행 로직
            .failureHandler(failHandler) // 로그인 실패 시 실행 로직
                .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // security에서 제공하는 기본 로그아웃페이지 말고
            .logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 페이지
            .invalidateHttpSession(true); // HTTP 세션 초기화
    }

    /**
     * password encoding 방법
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } // 패스워드 암호화 방식

    /**
     * 인증 관련 로직 처리
     * @param auth the {@link AuthenticationManagerBuilder} to use
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

}
