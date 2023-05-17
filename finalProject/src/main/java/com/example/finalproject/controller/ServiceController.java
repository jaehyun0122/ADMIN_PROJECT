package com.example.finalproject.controller;

import com.example.finalproject.dto.service.FindFileDto;
import com.example.finalproject.dto.service.FindServiceDto;
import com.example.finalproject.dto.service.QuestionDto;
import com.example.finalproject.dto.service.ServiceRegisterDto;
import com.example.finalproject.service.FileService;
import com.example.finalproject.service.MailService;
import com.example.finalproject.service.QuestionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.mail.smtp.SMTPSenderFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/service")
public class ServiceController {
    private final FileService fileService;
    private final QuestionService questionService;
    private final MailService mailService;

    /**
     * 서비스 페이지
     * @return
     */
    @GetMapping()
    public String viewService(Authentication authentication, Model model,
                              @RequestParam(name = "page", required = false)Integer page, @RequestParam(name = "pagePerData", required = false)Integer pagePerData) {
        List<FindServiceDto> serviceList = new ArrayList<>();
        // 접속 유저 총 서비스 개수 가져오기
        int userServiceCount = fileService.getUserServiceCount(authentication);

        if(page == null){
            serviceList = fileService.getServiceList(authentication, 0);
        }else{
            serviceList = fileService.getServiceList(authentication, page-1);
        }

        model.addAttribute("serviceList", serviceList);
        model.addAttribute("total", userServiceCount);

        return "/programmer/service_list";
    }

    // 서비스 상세 페이지
    @GetMapping("detail")
    public String serviceDetail(@RequestParam(name = "id") int id, Model model){
        FindServiceDto serviceDetail = fileService.getServiceDetail(id);
        // 파일 가져오기
        List<FindFileDto> fileList = fileService.getFile(id);

        model.addAttribute("serviceDetail", serviceDetail);
        model.addAttribute("fileList", fileList);

        return "/admin/admin_service_detail";
    }

    /**
     * 문의목록 페이지
     */
    @GetMapping("question")
    public String question(Model model, Authentication authentication, @RequestParam(name = "page", required = false) Integer page){
        List<QuestionDto> questionList = new ArrayList<>();

        if(page == null){
            questionList = questionService.getQuestionList(authentication, 0);
        }else{
            questionList = questionService.getQuestionList(authentication, page-1);
        }

        int userQuestionCount = questionService.getUserQuestionCount(authentication);

        model.addAttribute("questionList", questionList);
        model.addAttribute("total", userQuestionCount);

        return "/programmer/question_list";
    }

    /**
     * 문의 등록 페이지
     */
    @GetMapping("/question/register")
    public String questionReg(){
        return "/programmer/question_register";
    }

    /**
     * 문의 등록
     */
    @PostMapping("/question/register")
    public String registerQuestion(QuestionDto questionDto, Authentication authentication){
        questionService.registerQuestion(authentication, questionDto);

        return "redirect:/service/question";
    }

    // 문의 상세 페이지
    @GetMapping("/question/detail")
    public String questionDetail(@RequestParam(name = "id")int id, Model model){
        QuestionDto questionDetail = questionService.getQuestionDetail(id);
        List<QuestionDto> answerList = questionService.getAnswerList(id);
        model.addAttribute("questionDetail", questionDetail);
        model.addAttribute("answerList", answerList);

        return "/programmer/question_detail";
    }

    /**
     * 서비스 등록 페이지
     * @return
     */
    @GetMapping("register")
    public String servicePage(){
        return "/programmer/service_register";
    }

    /**
     * 서비스 등록 요청
     * @return
     */
    @PostMapping("register")
    public String serviceRegister(@RequestParam("pdf") MultipartFile pdf,
                                  @RequestParam("image") List<MultipartFile> images,
                                  ServiceRegisterDto serviceRegisterDto,
                                  Authentication authentication) throws IOException {

        // 파일 형식 확인
        fileService.imageTypeCheck(images);
        fileService.pdfTypeCheck(pdf);
        // 이미지 사이즈 확인
        fileService.imageSizeCheck(images);

        fileService.serviceRegister(serviceRegisterDto, authentication, pdf, images);

        return "/programmer/service_list";
    }

    // 서비스 승인
    @PostMapping("permit")
    @ResponseBody
    public ResponseEntity<Boolean> servicePermit(@RequestBody Map<String, Integer> reqData, Authentication authentication) throws Exception {
        int serviceId = reqData.get("id");
        // 승인 메일 발송
        // 수신자 이메일 가져오기
        String destinationEmail = fileService.getServiceRegUser(serviceId);

        mailService.sendMail(destinationEmail, authentication, serviceId); // 메일보내고 승인 컬럼 true 변경

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    // 서비스 반려
    @PostMapping("deny")
    @ResponseBody
    public ResponseEntity<Boolean> serviceDeny(@RequestBody Map<String, Object> reqData, Authentication authentication){
        fileService.serviceDeny(reqData, authentication);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
