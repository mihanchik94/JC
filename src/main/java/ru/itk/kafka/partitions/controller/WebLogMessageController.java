package ru.itk.kafka.partitions.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itk.kafka.partitions.dto.WebLogMessage;
import ru.itk.kafka.partitions.message.producer.WebLogMessageProducer;
import ru.itk.kafka.partitions.service.KafkaWebLogService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kafka")
public class WebLogMessageController {

    private final KafkaWebLogService kafkaWebLogService;

    private final WebLogMessageProducer webLogMessageProducer;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody WebLogMessage message) throws JsonProcessingException {
        webLogMessageProducer.sendMessage(String.valueOf(message.getId()), message);
        return ResponseEntity.ok("Message was sent to kafka");
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebLogMessage> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(kafkaWebLogService.getById(id));
    }

}
