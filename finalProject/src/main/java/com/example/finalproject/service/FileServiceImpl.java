package com.example.finalproject.service;

import com.example.finalproject.dto.service.FileTransferDto;
import com.example.finalproject.dto.service.FindServiceDto;
import com.example.finalproject.dto.service.ServiceRegisterDto;
import com.example.finalproject.exeption.ErrorMessageEnum;
import com.example.finalproject.exeption.ImageInvalidException;
import com.example.finalproject.exeption.SqlFailException;
import com.example.finalproject.mapper.ServiceMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{
    private final ServiceMapper serviceMapper;
    private final SqlSession sqlSession;
    @Override
    public boolean imageSizeCheck(MultipartFile image) throws IOException {
        // MultipartFile에서 File로 변환
        File imageFile = convertMultiPartToFile(image);
        // BufferedImage를 이용하여 이미지 파일 정보 읽어오기
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        // 이미지 가로, 세로 사이즈 구하기
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        // 가로, 세로 사이즈
        System.out.println("이미지 가로 : " + width);
        System.out.println("이미지 세로 : " + height);

        // image size check
        isPassSize(width, height);

        return true;
    }

    /**
     * 서비스 등록 요청
     * @param serviceRegisterDto
     */
    @Override
    public void serviceRegister(ServiceRegisterDto serviceRegisterDto, Authentication authentication) throws IOException {
        Map<String, Object> map = new HashMap<>();

        FileTransferDto fileTransferDto = new FileTransferDto();

        // BLOB 타입에 저장하기 위해 bytes 변환
        fileTransferDto.setImageByte(serviceRegisterDto.getImage().getBytes());
        fileTransferDto.setPdfByte(serviceRegisterDto.getPdf().getBytes());

        // 원본 파일 이름
        String originImgName = convertMultiPartToFile(serviceRegisterDto.getImage()).getName();
        String originPdfName = convertMultiPartToFile(serviceRegisterDto.getPdf()).getName();

        serviceRegisterDto.setOriginImgName(originImgName);
        serviceRegisterDto.setOriginPdfName(originPdfName);
        serviceRegisterDto.setEmail(authentication.getPrincipal().toString());

        map.put("serviceInfo", serviceRegisterDto);
        map.put("fileInfo", fileTransferDto);

        // insert 실패 시 에러 발생
        if(serviceMapper.serviceRegister(map) != 1){
            throw new SqlFailException(ErrorMessageEnum.SQL_ERROR);
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

    // 모든 등록 서비스 가져오기


    @Override
    public List<FindServiceDto> getServiceList(String type) {
        return null;
    }

    @Override
    public List<FindServiceDto> getServiceList() {
        return serviceMapper.allServiceList();
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
     * @param multipartFile
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
