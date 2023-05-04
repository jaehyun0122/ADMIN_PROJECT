package com.example.finalproject.exeption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class GlobalException {
//    private static final Logger logger = LoggerFactory.getLogger(GlobalException.class);

    /**
     * 이미지 파일 업로드 실패시
     * @param ex
     * @param model
     * @return
     */
    @ExceptionHandler(ImageInvalidException.class)
    public String handleInternalAuthenticationServiceException(ImageInvalidException ex, Model model) {

        log.error("error {}",ex.getMessage());
        model.addAttribute("errorMessage", ex.getErrorMessageEnum().getMessage());

        return "/programmer/service_register";
    }

    /**
     * sql 실행 실패 시
     * @param ex
     * @param model
     * @return
     */
    @ExceptionHandler(SqlFailException.class)
    public String handleInternalAuthenticationServiceException(SqlFailException ex, Model model) {

        log.error("error {}",ex.getMessage());
        model.addAttribute("errorMessage", ex.getErrorMessageEnum().getMessage());

        return "/programmer/service_register";
    }
}