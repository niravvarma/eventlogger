package com.niravvarma.eventlogger.runner;

import com.niravvarma.eventlogger.domain.EventLog;
import com.niravvarma.eventlogger.service.EventLogService;
import com.niravvarma.eventlogger.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class AppRunner implements CommandLineRunner {

    private final EventLogService eventLogService;

    public AppRunner(EventLogService eventLogService) {
        this.eventLogService = eventLogService;
    }

    @Override
    public void run(String... args) throws Exception {
        String filePath;
        if (args == null || args.length != 1) {
            log.error("No argument present with filepath, using default file - eventlogs.txt from resources");
            filePath = ClassLoader.getSystemResource("eventlogs.txt").getPath();
        } else {
            filePath = args[0];
        }

        // Start the clock
        long start = System.currentTimeMillis();

        List<EventLog> eventLogList = FileUtils.parseEventLogs(filePath);
        if(!eventLogList.isEmpty()) {
            log.debug(String.format("Saving event logs, total: %d", eventLogList.size()));

            CompletableFuture<Iterable<EventLog>> it = eventLogService.save(eventLogList);
            // Wait until they are all done
            CompletableFuture.allOf(it).join();

            it.get().forEach(System.out::println);
        } else {
            throw new RuntimeException("Not able to fetch any event logs");
        }
        // Print results, including elapsed time
        log.info("Elapsed time: " + (System.currentTimeMillis() - start));
    }

}
