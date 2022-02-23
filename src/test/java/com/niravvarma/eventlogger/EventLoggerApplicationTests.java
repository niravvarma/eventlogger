package com.niravvarma.eventlogger;

import com.niravvarma.eventlogger.controller.EventLogController;
import com.niravvarma.eventlogger.runner.AppRunner;
import org.apache.logging.log4j.EventLogger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class EventLoggerApplicationTests {

	@Autowired
	EventLoggerApplication eventLoggerApplication;

	@Autowired
	private EventLogController controller;

	@SpyBean
	AppRunner appRunner;

	@Test
	@DisplayName("basic smoke test")
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	@DisplayName("check Runner runs after the context is loaded")
	void whenContextLoads_thenRunnersRun() throws Exception {
		verify(appRunner, times(1)).run(any());
	}

	@Test
	@DisplayName("Test Run")
	void testRun() throws Exception {
		String filePath = ClassLoader.getSystemResource("eventlogs-test.txt").getPath();
		appRunner.run(filePath);
	}

}
