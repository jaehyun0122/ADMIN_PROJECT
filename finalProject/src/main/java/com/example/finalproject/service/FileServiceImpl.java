package com.example.finalproject.service;

import com.example.finalproject.dto.service.FindFileDto;
import com.example.finalproject.dto.service.FindServiceDto;
import com.example.finalproject.dto.service.ServiceRegisterDto;
import com.example.finalproject.exeption.ErrorMessageEnum;
import com.example.finalproject.exeption.ImageInvalidException;
import com.example.finalproject.exeption.SqlFailException;
import com.example.finalproject.mapper.ServiceMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{
    private final ServiceMapper serviceMapper;
    private final SqlSession sqlSession;
    // 파일 확장자 리스트
    static private ArrayList<String> mediaTypeList = new ArrayList<>(Arrays.asList("pdf", "png", "jpeg"));
    @Override
    public boolean imageSizeCheck(List<MultipartFile> images) throws IOException {
        // MultipartFile에서 File로 변환
        for (MultipartFile multipartFile : images) {
            File convertedFile = convertMultiPartToFile(multipartFile);

            // BufferedImage를 이용하여 이미지 파일 정보 읽어오기
            BufferedImage bufferedImage = ImageIO.read(convertedFile);

            // 이미지 가로, 세로 사이즈 구하기
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();

            // image size check
            isPassSize(width, height);
        }

        return true;
    }

    /**
     * 서비스 등록 요청
     * @param serviceRegisterDto
     */
    @Override
    public void serviceRegister(ServiceRegisterDto serviceRegisterDto, Authentication authentication, MultipartFile pdf, List<MultipartFile> images) throws IOException {
        // 등록 요청 사용자 설정
        serviceRegisterDto.setEmail(authentication.getPrincipal().toString());

        // file 정보 제외 INSERT 후 해당 서비스 PK 가져오기
        serviceMapper.serviceRegister(serviceRegisterDto);
        int servicePk = serviceRegisterDto.getId();

        Map<String, Object> pdfMap = new HashMap<>();
        Map<String, Object> imgMap = new HashMap<>();

        pdfMap.put("servicePk", servicePk);
        imgMap.put("servicePk", servicePk);
        imgMap.put("type", "img");
        pdfMap.put("type", "pdf");
        // 첨부 파일의 개수 만큼 file 테이블에 INSERT
        // 1. pdf 업로드
        pdfMap.put("fileByte", pdf.getBytes());
        pdfMap.put("fileName", convertMultiPartToFile(pdf).getName());
        serviceMapper.uploadFile(pdfMap);

        // 2. image 업로드
        for (MultipartFile image : images) {
            File convertFile = convertMultiPartToFile(image);
            imgMap.put("fileByte", image.getBytes());
            imgMap.put("fileName", convertFile.getName());

            serviceMapper.uploadFile(imgMap);
        }
    }

    /**
     * 서비스 목록 가져오기
     * @return
     */
    @Override
    public List<FindServiceDto> getServiceList(Authentication authentication, String status) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("email", authentication.getPrincipal().toString());
        paramMap.put("status", status);

        Optional<List<FindServiceDto>> findResult
                = Optional.ofNullable(sqlSession.selectList("getServiceList", paramMap));

        if(findResult.isEmpty()){ // 결과가 null 일 경우.
            return new ArrayList<>();
        }

        return findResult.get();
    }

    // 모든 서비스 가져오기. 어드민 용
    @Override
    public List<FindServiceDto> getServiceList(int page, int pagePerData) {
        System.out.println(page+" "+pagePerData);
        Map<String, Integer> map = new HashMap<>();
        map.put("pagePerData", pagePerData);
        map.put("offset", page*pagePerData);

        return serviceMapper.allServiceList(map);
    }

    // 서비스 상세 정보
    @Override
    public FindServiceDto getServiceDetail(int id) {
        FindServiceDto serviceDetail = serviceMapper.getServiceDetail(id);

        return serviceDetail;
    }

    // 모든 서비스 개수 가져오기


    @Override
    public int getListSize() {
        return serviceMapper.getListSize();
    }

    // 메일 발송을 위한 서비스 등록자 이메일 가져오기
    @Override
    public String getServiceRegUser(int id) {
        return serviceMapper.getServiceRegUser(id);
    }

    // 파일 다운로드
    @Override
    public FindFileDto downloadFile(int id) {

        return serviceMapper.getFile(id);
    }

    // 파일 다운로드 Http Response 생성
    @Override
    public ResponseEntity createHttpResponse(FindFileDto findFile) throws UnsupportedEncodingException {
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
                case "xlsx":
                    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                    break;
            }
        }

        // 파일 데이터와 헤더를 포함한 ResponseEntity 반환
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);

    }

    // 서비스 승인
    @Override
    public void servicePermit(int id) {
        Map<String, Object> reqData = new HashMap<>();
        reqData.put("id", id);
        reqData.put("confirmAt", LocalDateTime.now());

        serviceMapper.updateIsPermit(reqData);
    }

    // 서비스 승인 후 키값 저장
    @Override
    public void insertKey(String encryptKey, String contactKey, int serviceId) {
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put("encryptKey", encryptKey);
        keyMap.put("contactKey", contactKey);
        keyMap.put("serviceId", serviceId);

        serviceMapper.insertKey(keyMap);
    }

    // 서비스 키 존재시 없데이트
    @Override
    public void updateServiceKey(String encryptKey, String contactKey, int serviceId) {
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put("encryptKey", encryptKey);
        keyMap.put("contactKey", contactKey);
        keyMap.put("serviceId", serviceId);

        serviceMapper.updateServiceKey(keyMap);
    }

    // 서비스 반려
    @Override
    public void serviceDeny(Map<String, Object> reqData, Authentication authentication) {
        reqData.put("regUser", authentication.getPrincipal().toString());
        reqData.put("confirmAt", LocalDateTime.now());

        serviceMapper.denyService(reqData);
    }

    @Override
    public List<FindFileDto> getFile(int id) {
        // 이미지, pdf 미리보기 기능을 위해 base64 encoding
        List<FindFileDto> fileList = serviceMapper.getFileList(id);
        fileList.forEach(file -> file.setBase64Encode("data:image/jpeg;base64," + Base64.getEncoder().encodeToString(file.getFileByte())));

        return fileList;
    }

    /**
     * 이미지 사이즈 확인. 168*168 또는 192*600(이하) 가능
     * @param width
     * @param height
     * @return
     */
    private boolean isPassSize(int width, int height){
        // 168*168 || 192*600(이하)
        if((width != 168 && height != 168) || (width == 192 && height > 600)){
            throw new ImageInvalidException(ErrorMessageEnum.IMAGE_SIZE_EXCEED);
        }

        return true;
    }

    /**
     * MultiPartFile => File
     * @return File
     * @throws IOException
     */
    private File convertMultiPartToFile(MultipartFile multipartFile) throws IOException {
        File convertedFile = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(multipartFile.getBytes());
        fos.close();

        return convertedFile;
    }

}
