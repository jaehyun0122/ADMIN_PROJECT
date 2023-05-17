package com.example.finalproject.controller;

import com.example.finalproject.dto.user.UserDto;
import com.example.finalproject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 유저 프로필 보기
     * @return
     */
    @GetMapping("profile")
    public String profile(Authentication authentication, Model model){
        UserDto userProfile = userService.getUserProfile(authentication.getPrincipal().toString());
        model.addAttribute("userProfile", userProfile);

        return "/programmer/user_profile";
    }

    /**
     * 이름 변경 페이지
     */
    @GetMapping("/modify/userName")
    public String modifyUserName(){
        return "/programmer/user_profile_modify_userName";
    }

    /**
     * 비밀번호 변경 페이지
     */
    @GetMapping("/modify/password")
    public String modifyPassword(Model model, @RequestParam(required = false)String message){
        if(message != null){
            model.addAttribute("message", message);
        }

        return "/programmer/user_profile_modify_password";
    }

    /**
     * 비밀번호 수정
     */
    @PostMapping("/modify/password")
    @ResponseBody
    public ResponseEntity<Boolean> modifyPassword(Authentication authentication, @RequestBody Map<String, String> reqData){
        userService.modifyPassword(authentication, reqData.get("password"));

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /**
     * 유저 이름 수정
     */
    @PostMapping("/modify/userName")
    @ResponseBody
    public ResponseEntity<Boolean> modifyUserName(Authentication authentication, Model model, @RequestBody Map<String, String> reqData){
        userService.modifyUserName(authentication, reqData.get("userName"));

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
