package ru.itk.kafka.notification.configuration;

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


    @Value("${spring.kafka.topics.user_notification.name}")
    private String userNotificationTopicName;

    @Value("${spring.kafka.topics.user_notification.replicas}")
    private int userNotificationTopicReplicas;

    @Value("${spring.kafka.topics.user_notification.partitions}")
    private int userNotificationTopicPartitions;

    @Value("${spring.kafka.topics.user_notification-dlq.name}")
    private String userNotificationDlqTopicName;

    @Value("${spring.kafka.topics.user_notification-dlq.replicas}")
    private int userNotificationDlqTopicReplicas;

    @Value("${spring.kafka.topics.user_notification-dlq.partitions}")
    private int userNotificationDlqTopicPartitions;



}
