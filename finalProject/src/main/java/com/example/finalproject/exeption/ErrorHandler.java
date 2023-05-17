package com.example.finalproject.exeption;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * 에러 발생시 처리할 컨트롤러
 */
@Controller
public class ErrorHandler implements ErrorController {
    @GetMapping("/error")
    public String handleError(HttpServletRequest request){
        // 상태 코드 가져오기
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null){
            Integer statusCode = Integer.valueOf(status.toString());

            // Not Found Error
            if(statusCode == HttpStatus.NOT_FOUND.value()){
                return "/common/4xx";
            }
            // Internal Server Error
            if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                return "/common/5xx";
            }
        }

        return "/common/4xx";
    }
}
