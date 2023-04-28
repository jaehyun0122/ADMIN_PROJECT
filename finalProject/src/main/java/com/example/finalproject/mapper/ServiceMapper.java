package com.example.finalproject.mapper;

import com.example.finalproject.dto.service.FindServiceDto;
import com.example.finalproject.dto.service.ServiceRegisterDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface ServiceMapper {
    // 서비스 등록
    int serviceRegister(Map<String, Object> map);
    // 서비스 목록 가져오기
    List<FindServiceDto> getServiceList(Map<String, String> paramMap);
}
