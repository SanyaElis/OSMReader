package ru.vsu.cs.eliseev.osmreader.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import ru.vsu.cs.eliseev.osmreader.entities.Node;
import ru.vsu.cs.eliseev.osmreader.repositories.NodeRepository;
import ru.vsu.cs.eliseev.osmreader.services.NodeService;

import java.util.List;
import java.util.Optional;

@Service
public class NodeServiceImpl implements NodeService {
    private final NodeRepository nodeRepository;

    @Autowired
    public NodeServiceImpl(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Override
    public void create(Node node) {
        nodeRepository.insert(node);
        nodeRepository.save(node);
    }

    @Override
    public Node findById(String ref) {
        Optional<Node> node = nodeRepository.findById(ref);
        return node.orElse(null);
    }

    @Override
    public List<Node> findNodesInRadius(Node node, double radius) {
        Point point = new Point(node.getLocation()[0], node.getLocation()[1]);
        Distance distance = new Distance(radius, Metrics.KILOMETERS);
        return nodeRepository.findByLocationNear(point, distance);
    }
}
