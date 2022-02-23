package com.niravvarma.eventlogger.service;

import com.niravvarma.eventlogger.domain.EventLog;
import com.niravvarma.eventlogger.repository.EventLogRepository;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.StreamSupport;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventLogServiceTest {
    @Autowired
    private EventLogRepository repository;

    @Autowired
    private EventLogService eventLogService;

    @Before
    public void setUp() {
        eventLogService = new EventLogService(repository);
    }

    @Test
    @DisplayName("Test save() and list() method - save and fetch list of saved EventLog objects")
    public void test_save_and_list_Methods() {
        // given
        EventLog eventLog = new EventLog();
        eventLog.setDuration(3L);
        eventLog.setAlert(false);

        EventLog eventLog2 = new EventLog();
        eventLog2.setDuration(4L);
        eventLog2.setAlert(true);

        List<EventLog> lists = Arrays.asList(eventLog, eventLog2);

        // when
        CompletableFuture<Iterable<EventLog>> cf = eventLogService.save(lists);

        // then
        verifyResults(cf, lists.size());

        // then
        CompletableFuture<Iterable<EventLog>> cf2 = eventLogService.list();
        verifyResults(cf2, lists.size());
    }

    private void verifyResults(CompletableFuture<Iterable<EventLog>> cf, final int size) {
        cf.thenRun(() -> {
            try {
                assertEquals(StreamSupport.stream(cf.get().spliterator(), false).count(), size);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

}
