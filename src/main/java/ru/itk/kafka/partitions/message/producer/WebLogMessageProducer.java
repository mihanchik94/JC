package ru.itk.kafka.partitions.message.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.itk.kafka.partitions.dto.WebLogMessage;

@Component
public class WebLogMessageProducer extends AbstractProducer<WebLogMessage> {
    public WebLogMessageProducer(KafkaTemplate<String, Object> kafkaTemplate, ObjectMapper mapper,
                                 @Value("${spring.kafka.topics.web_logs.name}") String topicName) {
        super(kafkaTemplate, mapper, topicName);
    }
}
