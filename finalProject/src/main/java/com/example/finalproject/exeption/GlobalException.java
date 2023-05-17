package com.example.finalproject.exeption;

import com.sun.mail.smtp.SMTPSenderFailedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Slf4j
public class GlobalException {
    private static final Logger logger = LoggerFactory.getLogger(GlobalException.class);

    /**
     *  이외 에러 처리
     */
    @ExceptionHandler(Exception.class)
    public String handleInternalAuthenticationServiceException(Exception ex) {

        logger.error("error {}",ex.getMessage());

        return "/common/notFoundError";
    }


    /**
     * 서비스 등록시 파일 확장자가 다를 때
     */
    @ExceptionHandler(NotValidFileException.class)
    public String handleInternalAuthenticationServiceException(NotValidFileException ex, Model model) {

        logger.error("error {}",ex.getErrorMessageEnum().getMessage());
        model.addAttribute("errorMessage", ex.getErrorMessageEnum().getMessage());

        return "/programmer/service_register";
    }

    /**
     * 이미지 파일 업로드 실패시
     */
    @ExceptionHandler(ImageInvalidException.class)
    public String handleInternalAuthenticationServiceException(ImageInvalidException ex, Model model) {

        logger.error("error {}",ex.getMessage());
        model.addAttribute("errorMessage", ex.getErrorMessageEnum().getMessage());

        return "/programmer/service_register";
    }

    /**
     *  서비스 승인 메일 발송 실패
     */
    @ExceptionHandler(SMTPSenderFailedException.class)
    public String handleInternalAuthenticationServiceException(SMTPSenderFailedException ex, Model model) {

        logger.error("error {}",ex.getMessage());

        return "/common/serverError";
    }

    /**
     * sql 실행 실패 시
     */
    @ExceptionHandler(SqlFailException.class)
    public String handleInternalAuthenticationServiceException(SqlFailException ex, Model model) {

        logger.error("error {}",ex.getMessage());
        model.addAttribute("errorMessage", ex.getErrorMessageEnum().getMessage());

        return "/common/serverError";
    }
}
