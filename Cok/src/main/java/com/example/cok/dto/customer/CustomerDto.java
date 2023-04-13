package com.example.cok.dto.customer;

import lombok.Data;

@Data
public class CustomerDto {
    private int id;
    private String subject;
    private String title;
    private String content;
    private String url;
    private String detail_url;
    private String reg_date;
    private String img_url;

}
