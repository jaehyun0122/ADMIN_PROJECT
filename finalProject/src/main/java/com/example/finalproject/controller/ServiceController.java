package com.example.finalproject.controller;

import com.example.finalproject.dto.service.FindFileDto;
import com.example.finalproject.dto.service.FindServiceDto;
import com.example.finalproject.dto.service.QuestionDto;
import com.example.finalproject.dto.service.ServiceRegisterDto;
import com.example.finalproject.service.FileService;
import com.example.finalproject.service.MailService;
import com.example.finalproject.service.QuestionService;
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
    public String viewService(Authentication authentication, Model model){
        List<FindServiceDto> serviceList = fileService.getServiceList(authentication, "all");
        model.addAttribute("serviceList", serviceList);

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
     * 서비스 등록 상태에 따른 리턴
     * @param statusMap
     * @param model
     * @param authentication
     * @return
     */
    @PostMapping()
    @ResponseBody
    public List<FindServiceDto> viewServiceStatus(@RequestBody Map<String, String> statusMap, Model model, Authentication authentication){
        List<FindServiceDto> serviceList = fileService.getServiceList(authentication, statusMap.get("status"));

        return serviceList;
    }

    /**
     * 문의하기 페이지
     * @return
     */
    @GetMapping("question")
    public String question(Model model, Authentication authentication){

        List<QuestionDto> questionList = questionService.getQuestionList(authentication);
        model.addAttribute("questionList", questionList);

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

        return "/programmer/question_list";
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

        fileService.imageSizeCheck(images);
        fileService.serviceRegister(serviceRegisterDto, authentication, pdf, images);

        return "/programmer/service_register";
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
