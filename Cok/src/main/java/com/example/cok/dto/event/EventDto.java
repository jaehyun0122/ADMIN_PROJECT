package com.example.cok.dto.event;

import lombok.Data;

@Data
public class EventDto {
    private int id;
    private String title;
    private String content;
    private String regDate;
    private String startDate;
    private String endDate;
    private String url;
}
