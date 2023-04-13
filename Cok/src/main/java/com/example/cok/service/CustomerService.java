package com.example.cok.service;

import com.example.cok.dto.customer.CustomerDto;
import com.example.cok.dto.customer.MainInfoDto;
import com.example.cok.dto.service.ServiceQnaDto;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> getCustomerList();
    List<CustomerDto> getCustomerContentList(String type);
    List<MainInfoDto> getMainInfoList();
    List<ServiceQnaDto> getServiceQnaList();
    List<CustomerDto> getInfoList(String subject);
    CustomerDto getInfoDetail(int id);
}
