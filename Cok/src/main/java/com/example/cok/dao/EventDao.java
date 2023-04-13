package com.example.cok.dao;


import com.example.cok.dto.event.EventDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventDao {
    List<EventDto> getEventList();
    EventDto getEventDetail(int id);
}
