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

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void insertUser(UserRegisterDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
        dataMap.put("userName", userName);

        if(userMapper.modifyUserName(dataMap) != 1){
            throw new SqlFailException(ErrorMessageEnum.SQL_ERROR);
        };
    }

    // admin 유저 목록 가져오기
    @Override
    public List<UserDto> getUserList(String type) {
        Optional<List<UserDto>> findAdminList = Optional.ofNullable(userMapper.getUserList(type));
        if(findAdminList.isEmpty()){
            return new ArrayList<>();
        }

        return findAdminList.get();
    }
}
