package ru.vsu.cs.eliseev.osmreader.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.kafka.producer")
public class KafkaProducerProperties {

    private Long bufferMemory;
    private Integer retries;
    private Integer batchSize;
    private String acks;
    private LingerProperties linger;
    private String bootstrapServers;
    private String keySerializer;
    private String valueSerializer;

    @Getter
    @Setter
    public static class LingerProperties {
        private Integer ms;
    }
}
