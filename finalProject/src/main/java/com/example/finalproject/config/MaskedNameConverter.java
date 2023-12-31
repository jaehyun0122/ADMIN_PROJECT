package com.example.finalproject.config;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * 유저 이름, 전화번호 마스킹 클래스
 */
public class MaskedNameConverter extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent event) {
        String message = event.getMessage();
        if(message.startsWith(">") || message.startsWith("<")){
            return message;
        }

        String[] parts = message.split(",");
        String userName = parts[0];
        String phoneNo = parts[1];

        StringBuilder sb = new StringBuilder();
        // userName 마스킹 처리
        // 첫 글자 + * + 마지막 글자
        sb.append("이름 : ");
        sb.append(userName.charAt(0))
                .append("*".repeat(userName.substring(1, userName.length()-1).length()))
                .append(userName.charAt(userName.length() - 1));

        // phoneNo 마스킹 처리
        // 010 + **** + 뒷 번호
        sb.append("  전화번호 : ");
        sb.append(phoneNo.substring(0, 3))
                .append("****");
//                .append(phoneNo.substring(7, phoneNo.length()));

        return sb.toString();
    }
}
