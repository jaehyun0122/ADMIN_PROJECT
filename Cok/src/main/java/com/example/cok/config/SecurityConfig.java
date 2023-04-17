package com.example.cok.config;

import com.example.cok.service.PrincipalDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // https://victorydntmd.tistory.com/328
    private final PrincipalDetailsService principalDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("configure http");
        http
                .authorizeRequests()
                .antMatchers( "/login","/loginFail","/").permitAll()
                .antMatchers("/css/**","/js/**","/img/**").permitAll()
                .anyRequest().hasRole("USER")
                    .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/",true)
                .failureUrl("/loginFail")
                    .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // security에서 제공하는 기본 로그아웃페이지 말고
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true); // HTTP 세션 초기화
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("passwordEncoder");
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("configure AuthenticationManagerBuilder");
        auth.userDetailsService(principalDetailsService).passwordEncoder(passwordEncoder());
    }

}
