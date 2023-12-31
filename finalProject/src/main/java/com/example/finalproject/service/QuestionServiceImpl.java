package com.example.finalproject.service;

import com.example.finalproject.dto.service.QuestionDto;
import com.example.finalproject.exeption.ErrorMessageEnum;
import com.example.finalproject.exeption.SqlFailException;
import com.example.finalproject.mapper.QuestionMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService{
    private final SqlSession sqlSession;
    private final QuestionMapper questionMapper;
    /**
     * 모든 문의 내용 가져오기
     * @return
     */
    @Override
    public List<QuestionDto> getQuestionList(Authentication authentication, int page) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", authentication.getPrincipal().toString());
        map.put("offset", page*10);

        return questionMapper.getQuestionList(map);
    }

    /**
     * 문의 등록
     */
    @Override
    public void registerQuestion(Authentication authentication, QuestionDto questionDto) {
        // 등록 요청 이메일 set
        questionDto.setEmail(authentication.getPrincipal().toString());

        if(sqlSession.getMapper(QuestionMapper.class).registerQuestion(questionDto) != 1){
            // 내부 서버 에러 처리
//            throw new
        }
    }

    // 문의 상세 내역
    @Override
    public QuestionDto getQuestionDetail(int id) {
        QuestionDto questionDetail = questionMapper.getQuestionDetail(id);

        return questionDetail;
    }

    // admin용 문의 내역 가져오기
    @Override
    public List<QuestionDto> getQuestion(int page) {
        Map<String, Integer> map = new HashMap<>();
        map.put("offset", page * 10);

        return questionMapper.getQuestion(map);
    }

    // 모든 문의 내역 개수 가져오기
    @Override
    public int getQuestionCount() {
        return questionMapper.getQuestionCount();
    }

    // 로그인 유저 문의 내역 개수 가져오기
    @Override
    public int getUserQuestionCount(Authentication authentication) {
        return questionMapper.getUserQuestionCount(authentication.getPrincipal().toString());
    }

    // 문의 답변 등록
    @Override
    public void insertAnswer(Authentication authentication, Map<String, Object> reqData) {
        reqData.put("regUser", authentication.getPrincipal().toString());

        if(questionMapper.insertAnswer(reqData) != 1){
            throw new SqlFailException(ErrorMessageEnum.SQL_ERROR);
        }

        if (questionMapper.updateIsAnswer(reqData.get("questionId")) != 1){
            throw new SqlFailException(ErrorMessageEnum.SQL_ERROR);
        }

    }

    // 답변 목록 가져오기

    @Override
    public List<QuestionDto> getAnswerList(int id) {
        return questionMapper.getAnswerList(id);
    }
}
