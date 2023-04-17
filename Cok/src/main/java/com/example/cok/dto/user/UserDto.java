package com.example.cok.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@Slf4j
public class UserDto implements UserDetails{
    private int id;
    private String username;
    private String password;
    private String roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info(">>> getAuthorities {}", this.roles);
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
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
