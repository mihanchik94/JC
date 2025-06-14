package ru.itk.kafka.orders.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class KafkaProperties {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.acks}")
    private String acks;

    @Value("${spring.kafka.producer.retries}")
    private int retries;

    @Value("${spring.kafka.producer.linger-ms}")
    private String linger;

    @Value("${spring.kafka.producer.batch-size}")
    private String batchSize;

    @Value("${spring.kafka.topics.orders.name}")
    private String orderNewTopicName;

    @Value("${spring.kafka.topics.orders.replicas}")
    private int orderNewTopicReplicas;

    @Value("${spring.kafka.topics.orders.partitions}")
    private int orderNewTopicPartitions;

    @Value("${spring.kafka.topics.orders-dlq.name}")
    private String orderNewDlqTopicName;

    @Value("${spring.kafka.topics.orders-dlq.replicas}")
    private int orderNewDlqTopicReplicas;

    @Value("${spring.kafka.topics.orders-dlq.partitions}")
    private int orderNewTopicDlqPartitions;

}
