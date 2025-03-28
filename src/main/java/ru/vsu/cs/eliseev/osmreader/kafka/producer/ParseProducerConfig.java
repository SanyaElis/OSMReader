package ru.vsu.cs.eliseev.osmreader.kafka.producer;

import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.vsu.cs.eliseev.osmreader.dto.ResolvedOsmDTO;

import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableKafka
public class ParseProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;
    @Value("${spring.kafka.producer.retries}")
    private int retries;
    @Value("${spring.kafka.producer.batch-size}")
    private int batchSize;
    @Value("${spring.kafka.producer.linger.ms}")
    private int linger;
    @Value("${spring.kafka.producer.buffer-memory}")
    private int bufferMemory;
    @Value("${spring.kafka.producer.acks}")
    private String acks;
    @Value("${spring.kafka.producer.enable-idempotence}")
    private boolean enableIdempotence;


    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.put(ProducerConfig.ACKS_CONFIG, acks);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,enableIdempotence);
        props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    public ProducerFactory<String, ResolvedOsmDTO> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, ResolvedOsmDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
