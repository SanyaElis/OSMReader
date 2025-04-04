package ru.vsu.cs.eliseev.osmreader.services;

import ru.vsu.cs.eliseev.osmreader.entities.Node;
import ru.vsu.cs.eliseev.osmreader.entities.Relation;
import ru.vsu.cs.eliseev.osmreader.entities.Way;

import java.util.List;

public interface RelationService {
    void create(Relation relation);

    List<Relation> getRelationsInRadius(Relation relation, double radius);

    List<Node> getNodesInRelation(Relation relation);

    List<Way> getWaysInRelation(Relation relation);

    List<Relation> getRelationsInRelation(Relation relation);

    List<Relation> findRelationByMemberId(String refMember);

    Relation findById(String ref);
}
