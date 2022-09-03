package com.cs.assignment.service.impl;

import com.cs.assignment.entity.EventsLogEntity;
import com.cs.assignment.model.Record;
import com.cs.assignment.repository.EventsLogRepository;
import com.cs.assignment.service.EventPersistence;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EventPersistenceImpl implements EventPersistence {
    @Autowired
    private EventsLogRepository repository;
    private ObjectMapper mapper;

    public EventPersistenceImpl() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public void persistEvents(String file) throws IOException {
        log.info("**********File import process started**********");

        try {
            Paths.get(file);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        List<Record> records = getRecords(file);

        Map<String, List<Record>> eventsGroupedById = records.stream().collect(Collectors.groupingBy(Record::getId));

        List<EventsLogEntity> events = new LinkedList<>();

        eventsGroupedById.forEach((key, value) -> {
                    Optional<Record> startRecord = value.stream().filter(x -> x.getState().equalsIgnoreCase("STARTED")).findAny();
                    Optional<Record> endRecord = value.stream().filter(x -> x.getState().equalsIgnoreCase("FINISHED")).findAny();
                    long duration = -1L;

                    if (startRecord.isPresent() && endRecord.isPresent()) {
                        duration = endRecord.get().getTimestamp() - startRecord.get().getTimestamp();
                    }

                    events.add(EventsLogEntity.builder()
                            .eventId(key)
                            .eventDuration(duration)
                            .type(startRecord.get().getType())
                            .host(startRecord.get().getHost())
                            .alert(duration > 40L)
                            .build());
                }
        );
        repository.saveAll(events);

        log.info("**********File import process completed successfully!!!**********");
    }

    private List<Record> getRecords(String file) throws IOException {
        List<Record> records = new LinkedList<>();
        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                int index = 0;
                while ((line = reader.readLine()) != null) {
                    index++;
                    try {
                        records.add(mapper.readValue(line, Record.class));
                    } catch (Exception e) {
                        log.error(String.format("Invalid record %s. Exception: %s", index, e.getMessage()));
                    }
                }
            }
        } catch (Exception e) {
            String error = "Unable to read input file. Make sure given path is correct.";
            log.error(error);
            throw e;
        }
        return records;
    }
}