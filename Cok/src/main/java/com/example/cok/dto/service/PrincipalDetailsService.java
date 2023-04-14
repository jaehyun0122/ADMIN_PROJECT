package com.example.cok.dto.service;

import com.example.cok.dao.UserDao;
import com.example.cok.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
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
    private final SqlSession sqlSession;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDto> member = sqlSession.getMapper(UserDao.class).isMember(username);
        if(member.isPresent()){
            UserDto userDto = member.get();

            return new User(userDto.getUsername(), userDto.getPassword(), userDto.getAuthorities());
        }
        else throw new UsernameNotFoundException("유저 정보가 없습니다.");
    }
}
