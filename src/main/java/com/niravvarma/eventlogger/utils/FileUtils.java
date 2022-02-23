package com.niravvarma.eventlogger.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niravvarma.eventlogger.domain.EventLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.LineIterator;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public class FileUtils {

    private static final String ID = "id";
    private static final String TIMESTAMP = "timestamp";
    public static final String DURATION = "duration";
    public static final String ALERT = "alert";
    public static final String HOST = "host";
    public static final String TYPE = "type";
    public static final String STATE = "state";
    public static final int DURATION_THRESHOLD = 4;

    public static List<EventLog> parseEventLogs(String eventLogsPath) {
        Map<String, JSONObject> eventInputLogMap = getEventLogs(eventLogsPath);
        if(!eventInputLogMap.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            return eventInputLogMap.values().parallelStream()
                    .map(obj ->
                            {
                                try {
                                    return objectMapper.readValue(obj.toString(), EventLog.class);
                                } catch (IOException e) {
                                    log.error(String.format("Failed to map obj: %s --- ERROR: %s", obj, e.getMessage()));
                                }
                                return null;
                            }
                    ).filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private static Map<String, JSONObject> getEventLogs(String eventLogsPath) {
        Map<String, JSONObject> eventInputLogMap = new ConcurrentHashMap<>();

        /* Check if file is present in the path provided or not */
        File file = getFile(eventLogsPath);

        /* Build iterator by reading the file */
        LineIterator it = getLineIterator(file);

        /* Iterate and prepare Map of EventLog */
        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                log.debug("Processing line: " + line);
                JSONObject eventInputLog = new JSONObject(line);
                if(isValidEventInputLogObject(eventInputLog)) {
                    JSONObject eventOutputLog = getEventOutputLogObject(eventInputLogMap, eventInputLog);
                    eventInputLogMap.put(eventInputLog.getString(ID), eventOutputLog);
                } else {
                    log.error("Invalid obj: " + line);
                }
            }
        } finally {
            try {
                it.close();
            } catch (IOException e) {
                log.error(e.getMessage());
                System.exit(3);
            }
        }

        return eventInputLogMap;
    }

    private static JSONObject getEventOutputLogObject(Map<String, JSONObject> eventInputLogMap, JSONObject eventInputLog) {
        JSONObject eventNewLog = new JSONObject();
        if(eventInputLogMap.containsKey(eventInputLog.getString(ID))) {
            eventNewLog = eventInputLogMap.get(eventInputLog.getString(ID));
            long duration = Math.abs(eventInputLog.getLong(TIMESTAMP) - eventNewLog.getLong(TIMESTAMP));
            eventNewLog.put(DURATION, duration);
            eventNewLog.put(ALERT, duration >= DURATION_THRESHOLD);
            eventNewLog.remove(TIMESTAMP);
        } else {
            eventNewLog.put(TIMESTAMP, eventInputLog.getLong(TIMESTAMP));
            if(isJSONKeyValid(HOST, eventInputLog))  eventNewLog.put(HOST, eventInputLog.getString(HOST));
            if(isJSONKeyValid(TYPE, eventInputLog))  eventNewLog.put(TYPE, eventInputLog.getString(TYPE));
        }
        return eventNewLog;
    }

    private static LineIterator getLineIterator(File file) {
        LineIterator it;
        try {
            it = org.apache.commons.io.FileUtils.lineIterator(file, StandardCharsets.UTF_8.toString());
        } catch (IOException e) {
            log.error("There was an error processing event logs file");
            throw new RuntimeException(e);
        }
        return it;
    }

    private static File getFile(String eventLogsPath) {
        Path eventFilePath = Paths.get(eventLogsPath);
        if (!Files.exists(eventFilePath)) {
            log.error("Event Log file doest not exist for the path:" + eventLogsPath);
            System.exit(2);
        }
        return eventFilePath.toFile();
    }

    private static boolean isValidEventInputLogObject(JSONObject eventInputLog) {
        return isJSONKeyValid(ID, eventInputLog) && isJSONKeyValid(STATE, eventInputLog);
    }

    private static boolean isJSONKeyValid(String key, JSONObject obj) {
        return obj.has(key) && !StringUtils.isEmpty(obj.getString(key));
    }
}
