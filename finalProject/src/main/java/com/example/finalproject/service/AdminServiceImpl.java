package com.example.finalproject.service;

import com.example.finalproject.dto.service.FindServiceDto;
import com.example.finalproject.dto.user.UserDto;
import com.example.finalproject.exeption.ErrorMessageEnum;
import com.example.finalproject.exeption.SqlFailException;
import com.example.finalproject.mapper.AdminMapper;
import com.example.finalproject.mapper.UserMapper;
import com.example.finalproject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
    private final UserMapper userMapper;
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;

    // admin 사용자 등록
    @Override
    public void adminRegister(UserDto userDto) {
        // 계정 로그인 허용
        userDto.setPermit(true);
        // ADMIN 권한 설정
        userDto.setRoles("ROLE_ADMIN");
        // 패스워드 암호화.
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        if(userMapper.insertAdminUser(userDto) != 1){
            throw new SqlFailException(ErrorMessageEnum.SQL_ERROR);
        }
    }

    // 가입 요청 승인
    @Override
    public void permitSignIn(String email) {
        if(adminMapper.permitSignIn(email) != 1){
            throw new SqlFailException(ErrorMessageEnum.SQL_ERROR);
        }
    }

    // 계정 잠그기
    @Override
    public void lockUser(String email) {
        if(adminMapper.lockUser(email) != 1){
            throw new SqlFailException(ErrorMessageEnum.SQL_ERROR);
        }
    }

    // 계정 잠금 해제
    @Override
    public void unlockUser(String email) {
        if(adminMapper.unlockUser(email) != 1){
            throw new SqlFailException(ErrorMessageEnum.SQL_ERROR);
        }
    }

    // 계정 중지
    @Override
    public void pauseUser(String email) {
        if(adminMapper.pauseUser(email) != 1){
            throw new SqlFailException(ErrorMessageEnum.SQL_ERROR);
        }
    }

    // 계정 중지 해제
    @Override
    public void unPauseUser(String email) {
        if(adminMapper.unPauseUser(email) != 1){
            throw new SqlFailException(ErrorMessageEnum.SQL_ERROR);
        }
    }

    // 계정 탈퇴
    @Override
    public void quitUser(String email) {
        if(adminMapper.quitUser(email) != 1){
            throw new SqlFailException(ErrorMessageEnum.SQL_ERROR);
        }
    }

    // 계정 탈퇴 해제
    @Override
    public void unQuitUser(String email) {
        if(adminMapper.unQuitUser(email) != 1){
            throw new SqlFailException(ErrorMessageEnum.SQL_ERROR);
        }
    }

    // 서비스 목록 가져오기

    @Override
    public List<FindServiceDto> findServiceList() {

        return null;
    }

}
