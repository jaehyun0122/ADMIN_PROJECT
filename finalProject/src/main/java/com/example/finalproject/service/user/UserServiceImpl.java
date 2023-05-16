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

        if(userMapper.modifyPassword(dataMap) != 1){
            throw new SqlFailException(ErrorMessageEnum.SQL_ERROR);
        }

    }

    /**
     * 유저 이름 변경
     */
    @Override
    public void modifyUserName(Authentication authentication, String userName) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("email", authentication.getPrincipal().toString());
        dataMap.put("name", userName);

        if(userMapper.modifyUserName(dataMap) != 1){
            throw new SqlFailException(ErrorMessageEnum.SQL_ERROR);
        };
    }

    // admin 유저 목록 가져오기
    @Override
    public List<UserDto> getUserList(String type, int page, int pagePerData) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("pagePerData", pagePerData);
        map.put("offset", page * pagePerData);

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

}
