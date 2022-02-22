package com.niravvarma.eventlogger.repository;

import com.niravvarma.eventlogger.domain.EventLog;
import org.springframework.data.repository.CrudRepository;

public interface EventLogRepository extends CrudRepository<EventLog, Long> {
}
