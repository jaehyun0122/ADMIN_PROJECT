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

import java.util.ArrayList;
import java.util.HashMap;
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
        // 서비스 현황
        int serviceWaitCount = adminService.getServiceCount("0");
        int servicePermitCount = adminService.getServiceCount("1");
        int serviceReturnCount = adminService.getServiceCount("2");
        // 문의 현황
        int questionWaitCount = adminService.getQuestionCount("0");
        int questionPermitCount = adminService.getQuestionCount("1");
        // 가입 요청
        int regWaitCount = adminService.getRegCount("0");
        int regPermitCount = adminService.getRegCount("1");

        model.addAttribute("serviceWaitCount", serviceWaitCount);
        model.addAttribute("servicePermitCount", servicePermitCount);
        model.addAttribute("serviceReturnCount", serviceReturnCount);

        model.addAttribute("questionWaitCount", questionWaitCount);
        model.addAttribute("questionPermitCount", questionPermitCount);

        model.addAttribute("regWaitCount", regWaitCount);
        model.addAttribute("regPermitCount", regPermitCount);

        return "/admin/admin_index";
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

    // 서비스 목록 페이지
    @GetMapping("service")
    public String serviceListPage(Authentication authentication, Model model, @RequestParam(name = "page", required = false)Integer page){
        List<FindServiceDto> serviceList = new ArrayList<>();

        // 화면에 페이지 버튼 만들기 위해 모든 서비스 개수 가져오기
        int listSize = fileService.getListSize();
        // 초기 페이지에서 0~9의 데이터 가져오기
        if (page==null) serviceList = fileService.getServiceList(0);
        else serviceList = fileService.getServiceList(page-1);

        model.addAttribute("serviceList", serviceList);
        model.addAttribute("total", listSize);

        return "/admin/admin_service_list";
    }


    // 관리자 목록 페이지
    @GetMapping("admins")
    public String adminListPage(Model model, @RequestParam(name = "page", required = false)Integer page){
        List<UserDto> adminList = new ArrayList<>();
        // admin 유저 총 인원 가져오기
        int adminUserCount = userService.getUserCount("ROLE_ADMIN");

        if(page == null) adminList = userService.getUserList("admin", 0);
        else adminList = userService.getUserList("admin", page-1);

        model.addAttribute("total", adminUserCount);
        model.addAttribute("adminList", adminList);

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
    public String regRequestPage(Model model, @RequestParam(name = "page", required = false)Integer page){
        List<UserDto> userList = new ArrayList<>();
        // 요청 목록 가져오기
        int userCount = userService.getUserCount("ROLE_USER");

        if(page == null) userList = userService.getUserList("user", 0);
        else userList = userService.getUserList("user", page - 1);

        model.addAttribute("total", userCount);
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
    public String questionPage(Model model, @RequestParam(name = "page", required = false)Integer page){
        List<QuestionDto> questions = new ArrayList<>();
        // 모든 문의 내역 가져오기
        int questionCount = questionService.getQuestionCount();

        if(page == null) questions = questionService.getQuestion(0);
        else questions = questionService.getQuestion(page - 1);

        model.addAttribute("total", questionCount);
        model.addAttribute("questionList", questions);

        return "/admin/admin_question_list";
    }

    // 문의 답변 등록
    @PostMapping("/question/answer")
    @ResponseBody
    public ResponseEntity<Boolean> questionAnswer(Authentication authentication, @RequestBody Map<String, Object> reqData){
        questionService.insertAnswer(authentication, reqData);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
