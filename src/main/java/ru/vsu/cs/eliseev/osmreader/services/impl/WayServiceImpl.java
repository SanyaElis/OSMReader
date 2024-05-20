package ru.vsu.cs.eliseev.osmreader.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.cs.eliseev.osmreader.models.Node;
import ru.vsu.cs.eliseev.osmreader.models.Way;
import ru.vsu.cs.eliseev.osmreader.repositories.WayRepository;
import ru.vsu.cs.eliseev.osmreader.services.NodeService;
import ru.vsu.cs.eliseev.osmreader.services.WayService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<Node> nodesInWay = getNodesInWay(way);
        for (Node node : nodesInWay) {
            nodeService.findNodesInRadius(node, distance);
        }
        return null;
    }

    @Override
    public List<Way> findWaysByNodeId(String refNode) {
        return repository.findByNodesContaining(refNode);
    }
}
