package ru.itk.kafka.partitions.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    private final KafkaProperties kafkaProperties;

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(ProducerConfig.ACKS_CONFIG, kafkaProperties.getAcks());
        config.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProperties.getLinger());
        config.put(ProducerConfig.RETRIES_CONFIG, kafkaProperties.getRetries());
        config.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProperties.getBatchSize());

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }


    @Bean
    public NewTopic webLogsTopic() {
        return TopicBuilder
                .name(kafkaProperties.getWebLogsTopicName())
                .replicas(kafkaProperties.getWebLogsTopicReplicas())
                .partitions(kafkaProperties.getWebLogsTopicPartitions())
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }
}
