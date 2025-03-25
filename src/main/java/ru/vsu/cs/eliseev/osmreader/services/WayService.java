package ru.vsu.cs.eliseev.osmreader.services;

import ru.vsu.cs.eliseev.osmreader.entities.Node;
import ru.vsu.cs.eliseev.osmreader.entities.Way;

import java.util.List;

public interface WayService {
    void create(Way way);
    Way findById(String ref);
    List<Node> getNodesInWay(Way way);

    List<Way> findWaysInDistance(Way way, double distance);

    List<Way> findWaysByNodeId(String refNode);
}
