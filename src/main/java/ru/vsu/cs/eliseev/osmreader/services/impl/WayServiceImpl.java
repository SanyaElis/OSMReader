package ru.vsu.cs.eliseev.osmreader.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.cs.eliseev.osmreader.entities.Node;
import ru.vsu.cs.eliseev.osmreader.entities.Way;
import ru.vsu.cs.eliseev.osmreader.repositories.WayRepository;
import ru.vsu.cs.eliseev.osmreader.services.NodeService;
import ru.vsu.cs.eliseev.osmreader.services.WayService;

import java.util.*;

@Service
public class WayServiceImpl implements WayService {

    private final WayRepository repository;

    private final NodeService nodeService;

    @Autowired
    public WayServiceImpl(WayRepository repository, NodeService nodeService) {
        this.repository = repository;
        this.nodeService = nodeService;
    }

    @Override
    public void create(Way way) {
        repository.insert(way);
    }

    @Override
    public Way findById(String ref) {
        Optional<Way> way = repository.findById(ref);
        return way.orElse(null);
    }

    @Override
    public List<Node> getNodesInWay(Way way) {
        List<Node> nodes = new ArrayList<>();
        Node foundNode;
        for (String nodeRef : way.getNodes()) {
            foundNode = nodeService.findById(nodeRef);
            if (foundNode != null)
                nodes.add(foundNode);
        }
        return nodes;
    }

    @Override
    public List<Way> findWaysInDistance(Way way, double distance) {
        Set<Way> ways = new HashSet<>();
        List<Node> nodesInWay = getNodesInWay(way);
        List<Node> nodesNearNode;
        for (Node node : nodesInWay) {
            nodesNearNode = nodeService.findNodesInRadius(node, distance);
            for (Node nearNode : nodesNearNode) {
                ways.addAll(repository.findByNodesContaining(nearNode.getId()));
            }
        }
        return new ArrayList<>(ways);
    }

    @Override
    public List<Way> findWaysByNodeId(String refNode) {
        return repository.findByNodesContaining(refNode);
    }
}
