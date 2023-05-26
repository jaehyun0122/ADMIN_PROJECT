package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

class Test {

	@org.junit.jupiter.api.Test
	void test() throws ParseException {
		
//		String from = "20220418000000";
//        SimpleDateFormat fDate = new SimpleDateFormat("yyyyMMddhhmmss"); //같은 형식으로 맞춰줌
//        Date n = fDate.parse(from);
//        
//        SimpleDateFormat transDate = new SimpleDateFormat("yyyy.MM.dd");
//        String dtime = transDate.format(n);
//        System.out.println(dtime);
		
		Date today = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		String now = formatter.format(today);
		
		System.out.println(now);
	}
	
	@org.junit.jupiter.api.Test
	void urlparsing()  {
		String url = "https://www.google.com/";
		
		if(url.contains("https")) {
			System.out.println(true);
		}
	}

}
