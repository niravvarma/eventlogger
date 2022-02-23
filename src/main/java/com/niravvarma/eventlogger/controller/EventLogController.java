package com.niravvarma.eventlogger.controller;

import com.niravvarma.eventlogger.domain.EventLog;
import com.niravvarma.eventlogger.service.EventLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/events")
public class EventLogController {

    private EventLogService eventLogService;

    public EventLogController(EventLogService eventLogService) {
        this.eventLogService = eventLogService;
    }

    @GetMapping("/list")
    public CompletableFuture<Iterable<EventLog>> list() {
        return eventLogService.list();
    }
}
