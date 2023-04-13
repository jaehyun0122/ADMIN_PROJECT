package com.example.cok.dao;

import com.example.cok.dto.customer.CustomerDto;
import com.example.cok.dto.customer.MainInfoDto;
import com.example.cok.dto.service.ServiceQnaDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomerDao {
    List<CustomerDto> getCustomerList();
    List<CustomerDto> getCustomerContent(String type);
    List<MainInfoDto> getMainInfoList();

    List<ServiceQnaDto> getServiceQnaList();
    List<CustomerDto> getInfoList(String subject);

    CustomerDto getInfoDetail(int id);
}
