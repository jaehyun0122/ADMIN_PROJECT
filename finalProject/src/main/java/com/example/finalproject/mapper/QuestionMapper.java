package com.example.finalproject.mapper;

import com.example.finalproject.dto.service.QuestionDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface QuestionMapper {
    // 문의 내용 전부 리턴
    List<QuestionDto> getQuestionList(String email);

    // 문의 등록
    int registerQuestion(QuestionDto questionDto);

    // 문의 상세 내역
    QuestionDto getQuestionDetail(int id);

    // admin 문의 내역 가져오기
    List<QuestionDto> getQuestion();

    // 문의 답변 등록
    int insertAnswer(Map<String, Object> reqData);

    // 문의 내역 답변 플래그 변경. isAnswer
    int updateIsAnswer(Object id);

    // 답변 목록 가져오기
    List<QuestionDto> getAnswerList(int id);
}
