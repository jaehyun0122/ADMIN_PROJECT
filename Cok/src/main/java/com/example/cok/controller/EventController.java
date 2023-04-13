package com.example.cok.controller;

import com.example.cok.dto.event.EventDto;
import com.example.cok.dto.GnbDto;
import com.example.cok.service.ContentService;
import com.example.cok.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("event")
public class EventController {
    private final ContentService service;
    private final EventService eventService;

    @GetMapping()
    public String event(Model model, @RequestParam String type){
        List<GnbDto> gnbList = service.getGnbList();

        List<EventDto> eventList = eventService.getEventList(type);
        model.addAttribute("gnbList", gnbList);
        model.addAttribute("eventList", eventList);

        return "/main/index_event";
    }

    @GetMapping("{id}")
    public String eventDetail(Model model,@PathVariable("id") int id){
        EventDto eventDetail = eventService.getEventDetail(id);
        model.addAttribute("eventDetail", eventDetail);

        return "event/event_view";
    }
}
