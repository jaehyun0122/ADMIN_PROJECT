package com.example.finalproject.dto.service;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuestionDto {
    private int id;
    private String email;
    private String title;
    private String content;
    private boolean isAnswer; // 질문 답변 여부
    private LocalDateTime regAt; // 등록 일시
}
