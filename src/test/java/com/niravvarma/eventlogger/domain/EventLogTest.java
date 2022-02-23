package com.niravvarma.eventlogger.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EventLogTest {

    @Test
    @DisplayName("Test EventLog object creation")
    public void test_create_EventLog_objects() {
        EventLog eventLog = new EventLog(1L, 3L, false, null, null);
        assertEquals(1L, eventLog.getId());
        assertEquals(3L, eventLog.getDuration());
        assertNull(eventLog.getType());
        assertNull(eventLog.getHost());
        assertFalse(eventLog.isAlert());

        eventLog = new EventLog(2L, 5L, true, null, null);
        assertEquals(2L, eventLog.getId());
        assertEquals(5L, eventLog.getDuration());
        assertNull(eventLog.getType());
        assertNull(eventLog.getHost());
        assertTrue(eventLog.isAlert());

        eventLog = new EventLog(3L, 2L, false, "123", "APPLICATION_LOG");
        assertEquals(3L, eventLog.getId());
        assertEquals(2L, eventLog.getDuration());
        assertEquals("APPLICATION_LOG", eventLog.getType());
        assertEquals("123", eventLog.getHost());
        assertFalse(eventLog.isAlert());
    }
}
