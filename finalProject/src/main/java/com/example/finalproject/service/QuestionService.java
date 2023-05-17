package com.example.finalproject.service;

import com.example.finalproject.dto.service.QuestionDto;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

public interface QuestionService {
    // 접속자 문의 내용 list
    List<QuestionDto> getQuestionList(Authentication authentication, int page);

    // 문의 등록
    void registerQuestion(Authentication authentication, QuestionDto questionDto);

    //  문의 상세 내역
    QuestionDto getQuestionDetail(int id);

    // admin 문의 내용 가져오기
    List<QuestionDto> getQuestion(int page);

    // 모든 문의 내역 개수 가져오기
    int getQuestionCount();

    // 로그인 유저 문의 내역 개수 가져오기
    int getUserQuestionCount(Authentication authentication);

    // 문의 답변 등록
    void insertAnswer(Authentication authentication, Map<String, Object> reqData);

    // 답변 리스트 가져오기
    List<QuestionDto> getAnswerList(int id);
}
