package com.example.cok.config;

import com.example.cok.service.PrincipalDetailsService;
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
    private final PrincipalDetailsService principalDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers( "/login","/loginFail","/").permitAll() // 해당 경로 접근 허용
                .antMatchers("/css/**","/js/**","/images/**").permitAll() // 해당 경로 접금 허용
                .anyRequest().hasRole("USER") // 나머지 요청들은 USER 권한을 가지고 있어야 접근할 수 있다.
                    .and()
                .formLogin()
                .loginPage("/login") // security에서 제공하는 로그인 페이지 안 쓰려면 controller에 등록한 url 써주면 됨.
                .defaultSuccessUrl("/",true) // 로그인 성공 시 이동할 페이지
                .failureUrl("/loginFail") // 로그인 실패 시 이동할 페이지
                    .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // security에서 제공하는 기본 로그아웃페이지 말고
                .logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 페이지
                .invalidateHttpSession(true); // HTTP 세션 초기화
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } // 패스워드 암호화 방식

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailsService).passwordEncoder(passwordEncoder());
    }

}
