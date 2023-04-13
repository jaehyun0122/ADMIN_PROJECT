package com.example.cok.dto.service.stock;

import lombok.Data;

@Data
public class InvestDto {
    private String subject;
    private String title;
    private float rate;
    private float sum;
    private String start_date;
    private String end_date;
    private int serviceId;
}
