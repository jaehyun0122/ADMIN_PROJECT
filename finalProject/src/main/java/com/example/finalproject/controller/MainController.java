package com.example.finalproject.controller;

import com.example.finalproject.dto.user.UserDto;
import com.example.finalproject.service.FileService;
import com.example.finalproject.service.user.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;
    private final FileService fileService;
    /**
     * main page
     * @return
     */
    @GetMapping
    public String main(Authentication authentication, Model model){
        // 대기 서비스 카운트
        int waitCount = fileService.getServiceCount(authentication, "0");
        // 승인 서비스 카운트
        int permitCount = fileService.getServiceCount(authentication, "1");
        // 반려 서비스 카운트
        int returnCount = fileService.getServiceCount(authentication, "2");

        // 문의 내역 가져오기
        int notAnswer = fileService.getQuestionCount(authentication, "0");
        int answer = fileService.getQuestionCount(authentication, "1");

        model.addAttribute("permitCount", permitCount);
        model.addAttribute("waitCount", waitCount);
        model.addAttribute("returnCount", returnCount);

        model.addAttribute("answerCount", answer);
        model.addAttribute("notAnswerCount", notAnswer);


        return "/programmer/index";
    }

    /**
     * 로그인 페이지
     * @return
     */
    @GetMapping("login")
    public String login(@ModelAttribute("errorMessage") String errorMessage
            , Model model){

        if(errorMessage != null){
            model.addAttribute("errorMessage", errorMessage);
        }

        return "/programmer/login";
    }

    /**
     * 회원가입 페이지
     * @return
     */
    @GetMapping("register")
    public String register(){
        return "/programmer/user_register";
    }

    /**
     * 로그인 요청
     */
    @PostMapping("login")
    public String login(UserDto userDto){
        return "/programmer/index";
    }

    /**
     * 회원가입 요청
     * @param user
     * @return
     */
    @PostMapping("register")
    @ResponseBody
    public ResponseEntity<Boolean> userRegister(Model model,@RequestBody UserDto user){
        userService.insertUser(user);

        return new ResponseEntity(true, HttpStatus.OK);
    }

    /**
     * 이메일 중복 체크
     */
    @PostMapping("email/check")
    @ResponseBody
    public ResponseEntity<Boolean> emailCheck(@RequestBody Map<String, String> data){
        String email = data.get("email");
        System.out.println(email);
        boolean duplicate = userService.isDuplicate(email);

        if (duplicate){
            return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /**
     * logout
     */
    @GetMapping("logout")
    public String logout(){
        return "/programmer/index";
    }
}
