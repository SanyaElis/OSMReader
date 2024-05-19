package ru.vsu.cs.eliseev.osmreader.services;

import org.springframework.stereotype.Component;
import ru.vsu.cs.eliseev.osmreader.models.Node;
import ru.vsu.cs.eliseev.osmreader.models.Way;

import java.util.List;

@Component
public interface WayService {
    void create(Way way);
    Way findById(String ref);

    List<Node> getNodesInWay(Way way);
}
