package com.example.cok.service;

import com.example.cok.dto.event.EventDto;

import java.util.List;

public interface EventService {
    List<EventDto> getEventList(String type);

    EventDto getEventDetail(int id);
}
