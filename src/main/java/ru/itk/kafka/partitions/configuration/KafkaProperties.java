package ru.itk.kafka.partitions.configuration;

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
    private String webLogsGroupId;

    @Value("${spring.kafka.consumer.json-trusted-packages}")
    private String trustedPackages;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Value("${spring.kafka.consumer.concurrency}")
    private int consumerConcurrency;


    @Value("${spring.kafka.topics.web_logs.name}")
    private String webLogsTopicName;

    @Value("${spring.kafka.topics.web_logs.replicas}")
    private int webLogsTopicReplicas;

    @Value("${spring.kafka.topics.web_logs.partitions}")
    private int webLogsTopicPartitions;



}
