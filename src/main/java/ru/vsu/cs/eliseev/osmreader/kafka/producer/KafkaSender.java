package ru.vsu.cs.eliseev.osmreader.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.vsu.cs.eliseev.osmreader.dto.ResolvedOsmDTO;

@Component
public class KafkaSender {

    @Qualifier("kafkaTemplate")
    private final KafkaTemplate<String, ResolvedOsmDTO> kafkaTemplate;


    @Autowired
    public KafkaSender(KafkaTemplate<String, ResolvedOsmDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, ResolvedOsmDTO resolvedOsmDTO) {
        kafkaTemplate.send(topic, resolvedOsmDTO);
    }
}
