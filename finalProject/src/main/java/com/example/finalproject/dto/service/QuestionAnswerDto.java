package com.example.finalproject.dto.service;

import lombok.Data;

@Data
public class QuestionAnswerDto {
    private int id;
    private int questionId;
    private String answer;
    private String regUser;
}
