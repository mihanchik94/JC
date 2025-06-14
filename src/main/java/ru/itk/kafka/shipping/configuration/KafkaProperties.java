package ru.itk.kafka.shipping.configuration;

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

    @Value("${spring.kafka.consumer.group-id}")
    private String orderGroupId;

    @Value("${spring.kafka.consumer.json-trusted-packages}")
    private String trustedPackages;


    @Value("${spring.kafka.topics.sent_orders.name}")
    private String sentOrdersTopicName;

    @Value("${spring.kafka.topics.sent_orders.replicas}")
    private int sentOrdersTopicReplicas;

    @Value("${spring.kafka.topics.sent_orders.partitions}")
    private int sentOrdersTopicPartitions;

    @Value("${spring.kafka.topics.sent_orders-dlq.name}")
    private String sentOrdersDlqTopicName;

    @Value("${spring.kafka.topics.sent_orders-dlq.replicas}")
    private int sentOrdersDlqTopicReplicas;

    @Value("${spring.kafka.topics.sent_orders-dlq.partitions}")
    private int sentOrdersDlqTopicPartitions;



}
