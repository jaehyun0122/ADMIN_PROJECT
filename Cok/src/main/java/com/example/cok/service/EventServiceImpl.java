package com.example.cok.service;

import com.example.cok.dao.EventDao;
import com.example.cok.dto.event.EventDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{
    private final SqlSession sqlSession;
    @Override
    public List<EventDto> getEventList(String type) {
        List<EventDto> eventList = new ArrayList<>();
        eventList = sqlSession.getMapper(EventDao.class).getEventList();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

        switch (type){
            case "all":
                return eventList;
            case "ing":
                List<EventDto> ingFilterList = eventList.stream().
                        filter(d -> now.isBefore(LocalDateTime.parse(d.getEndDate(), formatter)))
                        .collect(Collectors.toList());
                return ingFilterList;
            case "end":
                List<EventDto> endFilterList = eventList.stream().
                        filter(d -> now.isAfter(LocalDateTime.parse(d.getEndDate(), formatter)))
                        .collect(Collectors.toList());
                return endFilterList;
        }

        return eventList;
    }

    @Override
    public EventDto getEventDetail(int id) {
        return sqlSession.getMapper(EventDao.class).getEventDetail(id);
    }
}
