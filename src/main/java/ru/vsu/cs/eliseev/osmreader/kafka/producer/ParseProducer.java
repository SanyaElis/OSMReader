package ru.vsu.cs.eliseev.osmreader.kafka.producer;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import ru.vsu.cs.eliseev.osmreader.entities.Way;

//@Component
public class ParseProducer {
    @Qualifier("kafkaTemplate")
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final Gson gson;

    @Autowired
    public ParseProducer(KafkaTemplate<String, String> kafkaTemplate, Gson gson) {
        this.kafkaTemplate = kafkaTemplate;
        this.gson = gson;
    }

    public void send(String topic, Way way) {
        String wayJson = convertWayToJson(way);
        System.out.println("sent");
        kafkaTemplate.send(topic, wayJson);
    }

    private String convertWayToJson(Way way) {
        return gson.toJson(way);
    }
}
