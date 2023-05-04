package com.example.finalproject.controller;

import com.example.finalproject.dto.service.FindServiceDto;
import com.example.finalproject.dto.service.QuestionDto;
import com.example.finalproject.dto.service.ServiceRegisterDto;
import com.example.finalproject.service.FileService;
import com.example.finalproject.service.QuestionService;
import lombok.RequiredArgsConstructor;
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

    /**
     * 서비스 페이지
     * @return
     */
    @GetMapping()
    public String viewService(){
        return "/programmer/service";
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

        return "/programmer/question";
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

        return "/programmer/question";
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
                                  @RequestParam("image") MultipartFile image,
                                  ServiceRegisterDto serviceRegisterDto,
                                  Authentication authentication) throws IOException {

        fileService.imageSizeCheck(image);
        fileService.serviceRegister(serviceRegisterDto, authentication);

        return "/programmer/service_register";
    }

}
