package ru.vsu.cs.eliseev.osmreader.services;

import org.springframework.stereotype.Component;
import ru.vsu.cs.eliseev.osmreader.entities.Node;

import java.util.List;

@Component
public interface NodeService {
    void create(Node node);

    Node findById(String ref);

    List<Node> findNodesInRadius(Node node, double radius);
}
