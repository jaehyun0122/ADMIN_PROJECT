package com.example.finalproject.dto.user;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements UserDetails {
    private String phoneNo;
    private String email;
    private String password;
    private String name;
    private boolean isLock; // 계정 잠김 여부. 비밀번호 5회 이상 틀릴 시 잠김.
    private boolean isPause; // 계정 중지 여부.
    private boolean isQuit; // 계정 탈퇴 여부.
    private boolean isPermit; // 계정 승인 여부.
    private String roles; // 유저 권한. ADMIN, USER, ...
    private LocalDateTime createdAt;
    private LocalDateTime passwordChangeDate;
    private LocalDateTime passwordLockDate;
    private LocalDateTime lastLoginDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        String[] userRoles = this.roles.split(",");

        for (String userRole : userRoles) {
            authorities.add(new SimpleGrantedAuthority(userRole));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isLock;
    }

    /**
     * 비밀번호 변경 후 90일 이상 확인
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        // 현재 시간 년, 월, 일, 시, 분, 초(소숫점 제거)
        LocalDateTime now = LocalDateTime.now().withNano(0);

        // 로그인 기록 없다면 로그인 가능
        if(this.lastLoginDate == null) {
            return true;
        }

        Duration duration = Duration.between(now, this.passwordChangeDate);
        // 로그인 후 90일 지나면 로그인 불가
        if(Math.abs(duration.toDays()) > 90){
            return false;
        }

        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isPause;
    }
}
