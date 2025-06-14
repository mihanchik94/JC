package ru.itk.kafka.partitions.service;

import org.springframework.stereotype.Service;
import ru.itk.kafka.partitions.dto.WebLogMessage;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaWebLogService {

    private final List<WebLogMessage> LOGS = new ArrayList<>();

    public void add(WebLogMessage message){
        LOGS.add(message);
    }


    public WebLogMessage getById(Long id){
        return LOGS.stream()
                .filter(log -> log.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("not found"));
    }
}
