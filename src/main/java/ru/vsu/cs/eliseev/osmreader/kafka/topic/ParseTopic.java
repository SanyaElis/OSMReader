package ru.vsu.cs.eliseev.osmreader.kafka.topic;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
//@Configuration
public class ParseTopic {
    @Value("${kafka.topic.name.for-input-topic}")
    private String topicName;

    @Value("${kafka.topic.partitions.for-input-topic}")
    private int numPartitions;

    @Value("${kafka.topic.replication-factor.for-input-topic}")
    private short replicationFactor;

    @Value("${kafka.producer.servers}")
    private String servers;

}
