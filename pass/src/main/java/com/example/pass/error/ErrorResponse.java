package com.example.pass.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse extends Throwable {

    private String errorCd;
    private String errorMessage;
    private String errorPointCd;

}
