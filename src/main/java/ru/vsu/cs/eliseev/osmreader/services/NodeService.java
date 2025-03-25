package ru.vsu.cs.eliseev.osmreader.services;

import ru.vsu.cs.eliseev.osmreader.entities.Node;

import java.util.List;

public interface NodeService {
    void create(Node node);

    Node findById(String ref);

    List<Node> findNodesInRadius(Node node, double radius);
}
