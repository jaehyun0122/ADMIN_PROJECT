package com.example.finalproject.service;

import com.example.finalproject.dto.service.FindServiceDto;
import com.example.finalproject.mapper.ServiceMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;

@SpringBootTest
class FileServiceImplTest {
    @Autowired
    private FileService fileService;

    @Autowired
    private SqlSession sqlSession;

    @Test
    void findServiceTest() throws IOException {
        FindServiceDto serviceDetail = sqlSession.getMapper(ServiceMapper.class).getServiceDetail(3);

        System.out.println(serviceDetail);

    }

    public MultipartFile convertToMultipartFile(byte[] bytes, String fileName) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        return new MockMultipartFile(fileName, fileName, "image/jpeg", inputStream);
    }

}