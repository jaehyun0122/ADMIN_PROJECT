package com.example.cok.service;

import com.example.cok.dao.CustomerDao;
import com.example.cok.dto.customer.CustomerDto;
import com.example.cok.dto.customer.MainInfoDto;
import com.example.cok.dto.service.ServiceQnaDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService{
    private final SqlSession sqlSession;
    @Override
    public List<CustomerDto> getCustomerList() {
        return sqlSession.getMapper(CustomerDao.class).getCustomerList();
    }

    @Override
    public List<CustomerDto> getCustomerContentList(String type) {
        return sqlSession.getMapper(CustomerDao.class).getCustomerContent(type);
    }

    @Override
    public List<MainInfoDto> getMainInfoList() {
        return sqlSession.getMapper(CustomerDao.class).getMainInfoList();
    }

    @Override
    public List<ServiceQnaDto> getServiceQnaList() {
        return sqlSession.getMapper(CustomerDao.class).getServiceQnaList();
    }

    /**
     *
     * subject에 따라 공지사항, FAQ, 이용약관 및 정책 데이터 가져옴.
     */
    @Override
    public List<CustomerDto> getInfoList(String subject) {
        return sqlSession.getMapper(CustomerDao.class).getInfoList(subject);
    }

    @Override
    public CustomerDto getInfoDetail(int id) {
        return sqlSession.getMapper(CustomerDao.class).getInfoDetail(id);
    }
}
