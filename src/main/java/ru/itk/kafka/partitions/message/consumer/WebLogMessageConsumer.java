package ru.itk.kafka.partitions.message.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.itk.kafka.partitions.dto.WebLogMessage;
import ru.itk.kafka.partitions.service.KafkaWebLogService;


@Component
@Slf4j
@RequiredArgsConstructor
public class WebLogMessageConsumer {
    private final KafkaWebLogService kafkaWebLogService;
    private final ObjectMapper objectMapper;


    @KafkaListener(topics = "${spring.kafka.topics.web_logs.name}", groupId = "${spring.kafka.consumer.group-id}", containerFactory = "listenerContainerFactory")
    public void listen(ConsumerRecord<String, String> record) throws JsonProcessingException {
        String message = record.value();
        WebLogMessage webLogMessage = objectMapper.readValue(message,  WebLogMessage.class);
        kafkaWebLogService.add(webLogMessage);
    }


}
