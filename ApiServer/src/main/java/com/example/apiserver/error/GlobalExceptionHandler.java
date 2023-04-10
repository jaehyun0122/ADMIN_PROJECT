package com.example.apiserver.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoDataException.class)
    public ResponseEntity<ErrorResponse> handleException(NoDataException ex){
      ErrorResponse errorResponse = new ErrorResponse(ex.getErrorInfoEnum());
      log.error("Error !!! {}", errorResponse);
      return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getErrorInfoEnum().getHttpStatus()));
    }

    @ExceptionHandler(NoMandatoryOrValidException.class)
    public ResponseEntity<ErrorResponse> handleException(NoMandatoryOrValidException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorInfoEnum(), ex.getContent());
        log.error("Error !!! {}", errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getErrorInfoEnum().getHttpStatus()));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleException(NullPointerException ex){
        ErrorResponse errorResponse = new ErrorResponse(ErrorInfoEnum.SYSTEM_ERROR);
        log.info("Error !!! {}", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ErrorInfoEnum.SYSTEM_ERROR.getHttpStatus()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleException(HttpRequestMethodNotSupportedException ex){
        ErrorResponse errorResponse = new ErrorResponse(ErrorInfoEnum.NOT_SUPPORT_HTTP_METHOD);
        log.info("Error !!! {}", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ErrorInfoEnum.NOT_SUPPORT_HTTP_METHOD.getHttpStatus()));
    }
// signTarget을 암호화해서 digitalsign
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException ex){
//        ErrorResponse errorResponse = new ErrorResponse(ErrorInfoEnum.SYSTEM_ERROR);
//        log.info("Error !!! {}", ex.getMessage());
//        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ErrorInfoEnum.SYSTEM_ERROR.getHttpStatus()));
//    }
}