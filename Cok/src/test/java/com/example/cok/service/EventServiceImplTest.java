package com.example.cok.service;

import com.example.cok.dao.EventDao;
import com.example.cok.dto.event.EventDto;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class EventServiceImplTest {
    @Autowired
    private EventService eventService;
    @Autowired
    private SqlSession sqlSession;
    @Test
    void eventDateTest(){
        List<EventDto> eventList = sqlSession.getMapper(EventDao.class).getEventList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        LocalDateTime now = LocalDateTime.now();

        List<EventDto> collect = eventList.stream().
                filter(d -> now.isBefore(LocalDateTime.parse(d.getEndDate(), formatter))).collect(Collectors.toList());
        for (EventDto eventDto : collect) {
            System.out.println(eventDto);
        }
    }

}