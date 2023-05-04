package com.example.finalproject.mapper;

import com.example.finalproject.dto.service.QuestionDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionMapper {
    // 문의 내용 전부 리턴
    List<QuestionDto> getQuestionList(String email);

    // 문의 등록
    int registerQuestion(QuestionDto questionDto);
}
