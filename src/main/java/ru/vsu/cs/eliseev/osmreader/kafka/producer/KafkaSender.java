package ru.vsu.cs.eliseev.osmreader.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.vsu.cs.eliseev.osmreader.dto.ResolvedOSMDTO;

@Component
public class KafkaSender {

    @Qualifier("kafkaTemplate")
    private final KafkaTemplate<String, ResolvedOSMDTO> kafkaTemplate;


    @Autowired
    public KafkaSender(KafkaTemplate<String, ResolvedOSMDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, ResolvedOSMDTO resolvedOsmDTO) {
        kafkaTemplate.send(topic, resolvedOsmDTO);
    }
}
