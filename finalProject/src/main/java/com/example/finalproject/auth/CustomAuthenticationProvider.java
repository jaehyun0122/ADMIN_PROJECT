package com.example.finalproject.auth;

import com.example.finalproject.dto.user.UserDto;
import com.example.finalproject.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 로그인 시도할 때 계정의 유효성 검사 ( 패스워드 일치, 계정 잠김, 중지, 탈퇴, 승인 )
 */
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserMapper userMapper;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        // 로그인 시도 아이디 존재 여부 확인 후
        UserDto userDto = userMapper.isMember(email)
                .orElseThrow(() -> new BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다."));

        // 패스워드 일치 여부 확인
        if (!passwordEncoder.matches(password, userDto.getPassword())) {
            // 비밀번호 틀린 횟수 카운트
            userMapper.passwordCount(userDto.getEmail());

            // 틀린 횟수 5회 이상이면 계정 잠김 설정
            if(userMapper.getPasswordCount(userDto.getEmail()) >= 5){
                userMapper.accountLock(userDto.getEmail());
            }

            throw new BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        // 계정 잠김 여부 확인
        if (userDto.isLock()) {
            throw new LockedException("해당 계정이 잠겼습니다. 관리자에게 문의하세요.");
        }

        // 계정 중지 여부 확인
        if (userDto.isPause()) {
            throw new DisabledException("해당 계정이 중지 됐습니다. 관리자에게 문의하세요.");
        }

        // 계정 탈퇴 여부 확인
        if (userDto.isQuit()) {
            throw new DisabledException("해당 계정이 탈퇴 됐습니다. 관리자에게 문의하세요.");
        }

        // 승인 사용자 확인
        if(!userDto.isPermit()){
            throw new DisabledException("관리자의 승인이 필요합니다.");
        }

        // 계정 권한 가져오기
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        String[] userRoles = userDto.getRoles().split(",");

        for (String userRole : userRoles) {
            authorities.add(new SimpleGrantedAuthority(userRole));
        }

        return new UsernamePasswordAuthenticationToken(email, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
