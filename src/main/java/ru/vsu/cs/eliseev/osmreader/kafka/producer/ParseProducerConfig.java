package ru.vsu.cs.eliseev.osmreader.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.vsu.cs.eliseev.osmreader.dto.ResolvedOSMDTO;
import ru.vsu.cs.eliseev.osmreader.properties.KafkaProducerProperties;

import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableKafka
@RequiredArgsConstructor
public class ParseProducerConfig {

    private final KafkaProducerProperties properties;


    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        props.put(ProducerConfig.RETRIES_CONFIG, properties.getRetries());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, properties.getBatchSize());
        props.put(ProducerConfig.ACKS_CONFIG, properties.getAcks());
        props.put(ProducerConfig.LINGER_MS_CONFIG, properties.getLinger().getMs());
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, properties.getBufferMemory());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, properties.getKeySerializer());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, properties.getValueSerializer());
        return props;
    }

    public ProducerFactory<String, ResolvedOSMDTO> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, ResolvedOSMDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
