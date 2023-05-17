package com.example.finalproject.service.user;

import com.example.finalproject.dto.user.UserDto;
import com.example.finalproject.dto.user.UserRegisterDto;
import com.example.finalproject.exeption.ErrorMessageEnum;
import com.example.finalproject.exeption.SqlFailException;
import com.example.finalproject.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // 회원 가입
    @Override
    public void insertUser(UserDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 회원 ROLE 설정
        user.setRoles("ROLE_USER");

        userMapper.userRegister(user);
    }

    /**
     * 유저 상세 정보 조히
     * @param
     * @return
     */
    @Override
    public UserDto getUserProfile(String email) {

        return userMapper.getUserProfile(email);
    }

    /**
     * 유저 비밀번호 변경
     */
    @Override
    public void modifyPassword(Authentication authentication, String password) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("email", authentication.getPrincipal().toString());
        dataMap.put("password", passwordEncoder.encode(password));

        // 비밀번호 변경
        userMapper.modifyPassword(dataMap);
        // 변경 후 패스워드 틀린 횟수 초기화
        userMapper.passwordReset(authentication.getPrincipal().toString());

    }

    /**
     * 유저 이름 변경
     */
    @Override
    public void modifyUserName(Authentication authentication, String userName) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("email", authentication.getPrincipal().toString());
        dataMap.put("name", userName);

        userMapper.modifyUserName(dataMap);
    }

    // admin 유저 목록 가져오기
    @Override
    public List<UserDto> getUserList(String type, int page) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("offset", page * 10);

        Optional<List<UserDto>> findAdminList = Optional.ofNullable(userMapper.getUserList(map));
        if(findAdminList.isEmpty()){
            return new ArrayList<>();
        }

        return findAdminList.get();
    }

    // admin or user 인원 수 가져오기
    @Override
    public int getUserCount(String type) {
        return userMapper.getUserCount(type);
    }

    // 최근 접속일 설정
    @Override
    public void setLastLoginDate(Authentication authentication) {
        Map<String, Object> reqData = new HashMap<>();
        reqData.put("email", authentication.getPrincipal().toString());
        reqData.put("lastLoginDate", LocalDateTime.now());

        userMapper.updateLastLoginDate(reqData);
    }

    // 이메일 중복 체크

    @Override
    public boolean isDuplicate(String email) {
        return false;
    }
}
