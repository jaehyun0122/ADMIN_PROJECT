package com.example.cok.dto.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ServiceDto {
    private int id;
    private String url;
    private String service_name;
    private String title;
}
