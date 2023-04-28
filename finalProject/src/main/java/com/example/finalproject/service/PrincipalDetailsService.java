package com.example.finalproject.service;

import com.example.finalproject.dto.user.UserDto;
import com.example.finalproject.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {
    private final UserMapper userMapper;
//    private static final Logger logger = LoggerFactory.getLogger(PrincipalDetailsService.class);

    /**
     * 로그인 요청 후 사용자 검증
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("loadUser {}", email);
        Optional<UserDto> member = userMapper.isMember(email);

        // 로그인 요청한 사용자가 존재할 경우
        if(member.isPresent()){
            UserDto userDto = member.get();
            log.info("login user {}", userDto);

            // username, password,
            // enabled(계정활성화), accountNonExpired(계정 만료), credentialsNonExpired(자격증명 만료. 비밀번호 등)
            // accountNonLocked(계정 잠김), authorities(권한)
            return new User(userDto.getEmail(), userDto.getPassword(),
                    userDto.isEnabled(), true, true,
                    userDto.isLock(), userDto.getAuthorities());
        }
        else {
            log.info(">>> 유저 정보 없음");
            throw new UsernameNotFoundException("유저 정보가 없습니다.");
        }
    }
}