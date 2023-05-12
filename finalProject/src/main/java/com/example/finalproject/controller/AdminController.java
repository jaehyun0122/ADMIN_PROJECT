package com.example.finalproject.controller;

import com.example.finalproject.dto.service.FindServiceDto;
import com.example.finalproject.dto.service.QuestionDto;
import com.example.finalproject.dto.user.UserDto;
import com.example.finalproject.service.AdminService;
import com.example.finalproject.service.FileService;
import com.example.finalproject.service.QuestionService;
import com.example.finalproject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {

    private final UserService userService;
    private final AdminService adminService;
    private final QuestionService questionService;
    private final FileService fileService;

    // admin 페이지 리턴
    @GetMapping()
    public String adminIndex(Model model){

        return "admin/admin_index";
    }
    // 관리자 등록 페이지
    @GetMapping("register")
    public String registerPage(){
        return "/admin/admin_user_register";
    }
    // 관리자 등록 요청
    @PostMapping("register")
    public String registerRequest(UserDto userDto){
        adminService.adminRegister(userDto);

        return "redirect:/admin/admins";
    }

    // 가입 요청 상세 페이지
    @GetMapping("regDetail")
    public String regDetail(@RequestParam(name = "email") String email, Model model){
        List<FindServiceDto> serviceList = fileService.getServiceList("all");
        model.addAttribute("serviceList", serviceList);

        return "/admin/admin_reg_detail";
    }

    // 서비스 목록 페이지
    @GetMapping("service")
    public String serviceListPage(Model model){
        List<FindServiceDto> serviceList = fileService.getServiceList();
        model.addAttribute("serviceList", serviceList);

        return "/programmer/service_list";
    }


    // 관리자 목록 페이지
    @GetMapping("admins")
    public String adminListPage(Model model){
        model.addAttribute("adminList", userService.getUserList("admin"));
        return "/admin/admin_list";
    }
    // 관리자 상세 페이지
    @GetMapping("detail")
    public String detailPage(@RequestParam(name = "email") String email, Model model){
        UserDto userInfo = userService.getUserProfile(email);
        model.addAttribute("userInfo", userInfo);

        return "/admin/admin_detail";
    }
    // 가입 요청 목록 페이지
    @GetMapping("reg")
    public String regRequestPage(Model model){
        List<UserDto> userList = userService.getUserList("user");
        model.addAttribute("userList", userList);

        return "/admin/admin_reg_request_list";
    }

    // 가입 승인
    @PostMapping("regPermit")
    @ResponseBody
    public ResponseEntity<Boolean> regPermit(@RequestBody Map<String, String> reqData){
        adminService.permitSignIn(reqData.get("email"));

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    // 계정 잠금
    @PostMapping("lock")
    @ResponseBody
    public ResponseEntity<Boolean> lockUser(@RequestBody Map<String, String> reqData){
        System.out.println(reqData.get("email"));
        adminService.lockUser(reqData.get("email"));

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
    // 계정 잠금 해제
    @PostMapping("unlock")
    @ResponseBody
    public ResponseEntity<Boolean> unlockUser(@RequestBody Map<String, String> reqData){
        adminService.unlockUser(reqData.get("email"));

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
    // 계정 탈퇴
    @PostMapping("quit")
    @ResponseBody
    public ResponseEntity<Boolean> quitUser(@RequestBody Map<String, String> reqData){
        adminService.quitUser(reqData.get("email"));

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    // 계정 중지
    @PostMapping("pause")
    @ResponseBody
    public ResponseEntity<Boolean> pauseUser(@RequestBody Map<String, String> reqData){
        adminService.pauseUser(reqData.get("email"));

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    // 계정 중지 해제
    @PostMapping("unPause")
    @ResponseBody
    public ResponseEntity<Boolean> unPauseUser(@RequestBody Map<String, String> reqData){
        adminService.unPauseUser(reqData.get("email"));

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    // 계정 탈퇴 해제
    @PostMapping("unQuit")
    @ResponseBody
    public ResponseEntity<Boolean> unQuitUser(@RequestBody Map<String, String> reqData){
        adminService.unQuitUser(reqData.get("email"));

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
    // 문의 목록 페이지
    @GetMapping("question")
    public String questionPage(Model model){
        List<QuestionDto> questions = questionService.getQuestion();
        model.addAttribute("questionList", questions);

        return "/programmer/question_list";
    }

    // 문의 답변 등록
    @PostMapping("/question/answer")
    @ResponseBody
    public ResponseEntity<Boolean> questionAnswer(Authentication authentication, @RequestBody Map<String, Object> reqData){
        questionService.insertAnswer(authentication, reqData);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
