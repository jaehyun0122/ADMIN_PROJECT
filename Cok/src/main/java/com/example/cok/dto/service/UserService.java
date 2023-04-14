package com.example.cok.dto.service;

import com.example.cok.dto.user.UserDto;

import java.util.Optional;

public interface UserService {
    Optional<UserDto> isUser(String userId);
}
