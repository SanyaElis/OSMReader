package ru.vsu.cs.eliseev.osmreader.components;

import ru.vsu.cs.eliseev.osmreader.kafka.producer.ParseProducer;
import ru.vsu.cs.eliseev.osmreader.kafka.topic.ParseTopic;
import ru.vsu.cs.eliseev.osmreader.entities.Way;
import ru.vsu.cs.eliseev.osmreader.services.WayService;

import java.util.List;

//@Component

public class Runner {

    private final ParseProducer producer;

    private final ParseTopic topic;

    private final WayService wayService;

//    @Autowired
    public Runner(ParseProducer producer, ParseTopic topic, WayService wayService) {
        this.producer = producer;
        this.topic = topic;
        this.wayService = wayService;
    }

    public void run() {
        List<Way> wayList = wayService.findWaysByNodeId("2541344095");
        sendMessagesToKafka(wayList);
    }

    public void sendMessagesToKafka(List<Way> ways) {
        for (Way way : ways) {
            producer.send(topic.getTopicName(), way);
        }
    }
}
