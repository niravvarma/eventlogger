package com.niravvarma.eventlogger.service;

import com.niravvarma.eventlogger.domain.EventLog;
import com.niravvarma.eventlogger.repository.EventLogRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class EventLogService {

    private EventLogRepository eventLogRepository;

    public EventLogService(EventLogRepository eventLogRepository) {
        this.eventLogRepository = eventLogRepository;
    }

    @Async
    public CompletableFuture<Iterable<EventLog>> list() {
        return CompletableFuture.completedFuture(eventLogRepository.findAll());
    }

    @Async
    public CompletableFuture<Iterable<EventLog>> save(List<EventLog> eventLogList) {
        return CompletableFuture.completedFuture(eventLogRepository.saveAll(eventLogList));
    }
}
