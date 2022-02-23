package com.niravvarma.eventlogger.repository;

import com.niravvarma.eventlogger.domain.EventLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventLogRepository extends CrudRepository<EventLog, Long> {
}
