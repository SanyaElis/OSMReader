package ru.vsu.cs.eliseev.osmreader.kafka.topic;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Getter
@Configuration
@Slf4j
public class ParseTopic {
    @Value("${spring.kafka.topic.name.for-input-topic}")
    private String topicName;

    @Value("${spring.kafka.topic.partitions.for-input-topic}")
    private int numPartitions;

    @Value("${spring.kafka.topic.replication-factor.for-input-topic}")
    private short replicationFactor;

    @Bean
    public NewTopic topic() {
        log.info("Topic with name {} created", topicName);
        return TopicBuilder.name(topicName)
                .partitions(numPartitions)
                .replicas(replicationFactor)
                .build();
    }
}
