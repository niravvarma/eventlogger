package com.niravvarma.eventlogger.controller;

import com.niravvarma.eventlogger.domain.EventLog;
import com.niravvarma.eventlogger.repository.EventLogRepository;
import com.niravvarma.eventlogger.service.EventLogService;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class EventLogControllerTest {

    @Autowired
    private EventLogRepository repository;

    @Autowired
    private EventLogService eventLogService;

    private EventLogController eventLogController;

    @Before
    public void init(){
        eventLogService = new EventLogService(repository);
        eventLogController = new EventLogController(eventLogService);
    }

    //given
    private List<EventLog> prepareDummyData() {
        EventLog eventLog = new EventLog();
        eventLog.setDuration(3L);
        eventLog.setAlert(false);

        EventLog eventLog2 = new EventLog();
        eventLog2.setDuration(4L);
        eventLog2.setAlert(true);

        return Arrays.asList(eventLog, eventLog2);
    }

    @Test
    @DisplayName("Test list() method - fetch list of saved EventLog objects")
    void test_list_Method() {
        //given
        List<EventLog> lists = prepareDummyData();

        // when
        CompletableFuture<Iterable<EventLog>> cf = eventLogService.save(lists);

        //then
        cf.thenRun(() -> {
            CompletableFuture<Iterable<EventLog>> cf2 = eventLogController.list();
            verifyResults(cf2, lists.size());
        });
    }

    private void verifyResults(CompletableFuture<Iterable<EventLog>> cf2, int size) {
        cf2.thenRun(() -> {
            try {
                assertEquals(StreamSupport.stream(cf2.get().spliterator(), false).count(), size);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

}
