package com.niravvarma.eventlogger.repository;

import com.niravvarma.eventlogger.domain.EventLog;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EventLogRepositoryTest {

    @Autowired
    EventLogRepository eventLogRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Test findAll() method - fetch list of saved EventLog objects from in-memory HSQLDB")
    public void test_findAll_Method(){
        // given
        EventLog eventLog = new EventLog();
        eventLog.setDuration(3L);
        eventLog.setAlert(false);
        entityManager.persist(eventLog);
        entityManager.flush();

        // when
        EventLog found = eventLogRepository.findAll().iterator().next();

        // then
        assertThat(found.getDuration()).isEqualTo(eventLog.getDuration());
    }

}
