package com.example.finalproject.controller;

import com.example.finalproject.dto.service.FindFileDto;
import com.example.finalproject.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@Controller
@RequiredArgsConstructor
@RequestMapping("file")
public class FileController {
    private final FileService fileService;

    // file 다운로드
    @GetMapping()
    public ResponseEntity downloadImg(@RequestParam int id) throws IOException {
        FindFileDto findFile = fileService.downloadFile(id);

        return fileService.createHttpResponse(findFile);
    }
}
