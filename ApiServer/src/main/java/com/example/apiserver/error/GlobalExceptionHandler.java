package com.example.apiserver.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(ErrorInfoEnum errorInfoEnum){
        ErrorResponse errorResponse = new ErrorResponse(errorInfoEnum);
      log.error("Error !!! {}", errorResponse);

      return null;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(ErrorInfoEnum errorInfoEnum, String leakBody){
        ErrorResponse errorResponse = new ErrorResponse(errorInfoEnum, leakBody);
        log.error("Error !!! {}", errorResponse);

        return null;
    }
}