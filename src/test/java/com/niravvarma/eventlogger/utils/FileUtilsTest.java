package com.niravvarma.eventlogger.utils;

import com.niravvarma.eventlogger.domain.EventLog;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileUtilsTest {

    @Test
    @DisplayName("Test parseEventLogs() method - check if correct number of EventLog objects are returned")
    void test_parseEventLogs_success() {
        String filePath = ClassLoader.getSystemResource("eventlogs-test.txt").getPath();
        List<EventLog> lists = FileUtils.parseEventLogs(filePath);
        assertEquals(3, lists.size());
    }

    @Test
    @DisplayName("Test parseEventLogs() method - check if zero number of EventLog objects are returned")
    void test_parseEventLogs_failed() {
        /*This file contains just one dangling line, thus, no eventLog should be written*/
        String filePath = ClassLoader.getSystemResource("eventlogs-test-fail.txt").getPath();
        List<EventLog> lists = FileUtils.parseEventLogs(filePath);
        assertEquals(0, lists.size());
    }

    @Test
    @DisplayName("Test parseEventLogs() method - check if only 1 EventLog object is returned")
    void test_parseEventLogs_failed2() {
        /*This file contains 1 valid pair and one dangling line, thus, only 1 eventLog should be written*/
        String filePath = ClassLoader.getSystemResource("eventlogs-test-fail2.txt").getPath();
        List<EventLog> lists = FileUtils.parseEventLogs(filePath);
        assertEquals(1, lists.size());
    }

}