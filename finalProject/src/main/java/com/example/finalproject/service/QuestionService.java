package com.example.finalproject.service;

import com.example.finalproject.dto.service.QuestionDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface QuestionService {
    // 문의 내용 전부 리턴
    List<QuestionDto> getQuestionList(Authentication authentication);

    // 문의 등록
    void registerQuestion(Authentication authentication, QuestionDto questionDto);
}
