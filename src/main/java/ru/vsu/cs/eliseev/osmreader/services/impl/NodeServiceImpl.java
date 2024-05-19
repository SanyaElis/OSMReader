package ru.vsu.cs.eliseev.osmreader.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import ru.vsu.cs.eliseev.osmreader.models.Node;
import ru.vsu.cs.eliseev.osmreader.repositories.NodeRepository;
import ru.vsu.cs.eliseev.osmreader.services.NodeService;

import java.util.Optional;

@Service
public class NodeServiceImpl implements NodeService {
    private final NodeRepository repository;

    @Autowired
    public NodeServiceImpl(NodeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Node node) {
        repository.insert(node);
    }

    @Override
    public Node findById(String ref) {
        Optional<Node> node = repository.findById(ref);
        return node.orElse(null);
    }
}
