package ru.vsu.cs.eliseev.osmreader.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.cs.eliseev.osmreader.models.Node;
import ru.vsu.cs.eliseev.osmreader.models.Relation;
import ru.vsu.cs.eliseev.osmreader.models.Way;
import ru.vsu.cs.eliseev.osmreader.repositories.RelationRepository;
import ru.vsu.cs.eliseev.osmreader.services.NodeService;
import ru.vsu.cs.eliseev.osmreader.services.RelationService;
import ru.vsu.cs.eliseev.osmreader.services.WayService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RelationServiceImpl implements RelationService {

    private final RelationRepository repository;
    private final WayService wayService;
    private final NodeService nodeService;

    @Autowired
    public RelationServiceImpl(RelationRepository repository, WayService wayService, NodeService nodeService) {
        this.repository = repository;
        this.wayService = wayService;
        this.nodeService = nodeService;
    }

    @Override
    public void create(Relation relation) {
        repository.insert(relation);
    }

    @Override
    public List<Relation> getRelationsInRadius(Relation relation, double radius) {//todo
        return null;
    }

    @Override
    public List<Node> getNodesInRelation(Relation relation) {
        List<Node> nodes = new ArrayList<>();
        Node foundNode = null;
        for (Relation.Member member : relation.getMembers()) {
            if (member.type().equals("node")) {
                foundNode = nodeService.findById(member.refMember());
            }
            if (foundNode != null) {
                nodes.add(foundNode);
            }
        }
        return nodes;
    }

    @Override
    public List<Way> getWaysInRelation(Relation relation) {
        List<Way> ways = new ArrayList<>();
        Way foundWay = null;
        for (Relation.Member member : relation.getMembers()) {
            if (member.type().equals("way")) {
                foundWay = wayService.findById(member.refMember());
            }
            if (foundWay != null) {
                ways.add(foundWay);
            }
        }
        return ways;
    }

    @Override
    public Relation findById(String ref) {
        Optional<Relation> relation = repository.findById(ref);
        return relation.orElse(null);
    }
}
