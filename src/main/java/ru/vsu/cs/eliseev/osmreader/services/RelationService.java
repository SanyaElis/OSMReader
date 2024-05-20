package ru.vsu.cs.eliseev.osmreader.services;

import org.springframework.stereotype.Component;
import ru.vsu.cs.eliseev.osmreader.models.Node;
import ru.vsu.cs.eliseev.osmreader.models.Relation;
import ru.vsu.cs.eliseev.osmreader.models.Way;

import java.util.List;

@Component
public interface RelationService {
    void create(Relation relation);

    List<Relation> getRelationsInRadius(Relation relation, double radius);

    List<Node> getNodesInRelation(Relation relation);

    List<Way> getWaysInRelation(Relation relation);

    List<Relation> getRelationsInRelation(Relation relation);

    List<Relation> findRelationByMemberId(String refMember);

    Relation findById(String ref);
}
