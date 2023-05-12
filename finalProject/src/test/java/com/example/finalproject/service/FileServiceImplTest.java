package com.example.finalproject.service;

import com.example.finalproject.dto.service.FindFileDto;
import com.example.finalproject.dto.service.FindServiceDto;
import com.example.finalproject.mapper.ServiceMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

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

    static private ArrayList<String> mediaTypeList = new ArrayList<>(Arrays.asList("pdf", "png", "jpeg"));
    @Test
    void downloadTest() throws UnsupportedEncodingException {
        FindFileDto findFile = fileService.downloadFile(4);

        // 파일을 ByteArrayResource로 변환
        ByteArrayResource resource = new ByteArrayResource(findFile.getFileByte());

        // HTTP 응답 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", URLEncoder.encode(findFile.getFileName(), "UTF-8"));

        // 미디어 타입 찾기
        String[] split = findFile.getFileName().split("\\.");
        Optional<String> first = Arrays.stream(split).filter(data -> mediaTypeList.contains(data)).findFirst();
        if(first.isPresent()){
            switch (first.get()){
                case "pdf":
                    headers.setContentType(MediaType.APPLICATION_PDF);
                    break;
                case "png":
                    headers.setContentType(MediaType.IMAGE_PNG);
                    break;
                case "jpeg":
                    headers.setContentType(MediaType.IMAGE_JPEG);
                    break;
                default:

            }
        }


    }


    // multipartfile => file
    public MultipartFile convertToMultipartFile(byte[] bytes, String fileName) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        return new MockMultipartFile(fileName, fileName, "image/jpeg", inputStream);
    }

}