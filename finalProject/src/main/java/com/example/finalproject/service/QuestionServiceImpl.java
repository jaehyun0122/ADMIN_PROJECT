package com.example.finalproject.service;

import com.example.finalproject.dto.service.QuestionDto;
import com.example.finalproject.mapper.QuestionMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService{
    private final SqlSession sqlSession;

    /**
     * 모든 문의 내용 가져오기
     * @return
     */
    @Override
    public List<QuestionDto> getQuestionList(Authentication authentication) {
        Optional<List<QuestionDto>> questionList = Optional.ofNullable(sqlSession.selectList("getQuestionList", authentication.getPrincipal()));
        // 조회 결과 없을 시 새 리스트 만들어 리턴
        if(questionList.isEmpty()){
            return new ArrayList<>();
        }

        return questionList.get();
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
}
