package ru.vsu.cs.eliseev.osmreader.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.cs.eliseev.osmreader.entities.*;
import ru.vsu.cs.eliseev.osmreader.repositories.OsmRelationRepository;
import ru.vsu.cs.eliseev.osmreader.services.NodeService;
import ru.vsu.cs.eliseev.osmreader.services.OsmRelationService;

import java.util.List;

@Service
public class OsmRelationImpl implements OsmRelationService {

    private final NodeService nodeService;
    private final OsmRelationRepository osmRelationRepository;

    @Autowired
    public OsmRelationImpl(NodeService nodeService, OsmRelationRepository osmRelationRepository) {
        this.nodeService = nodeService;
        this.osmRelationRepository = osmRelationRepository;
    }

    /**
     * Checks if all child nodes (Node) of the given Way object are present in the database.
     * If any node is missing, it is added to the auxiliary collection `osm_relation`
     * to track incomplete parent objects.
     *
     * @param way a Way object containing a list of child nodes (Nodes).
     * @return {@code true} if all nodes were already present in the database (the Way is complete);
     *         {@code false} if at least one node was added to the `osm_relation` collection.
     */
    @Override
    public boolean addChildren(Way way) {
        List<String> nodesInWay = way.getNodes();
        int addedCount = 0;
        for (String nodeReference : nodesInWay) {
            Node currNode = nodeService.findById(nodeReference);
            if (currNode != null)
                continue;
            OsmRelation osmRelation = OsmRelation.builder()
                    .childId(nodeReference)
                    .parentId(way.getId())
                    .build();
            osmRelationRepository.insert(osmRelation);
            addedCount++;
        }
        return addedCount == 0;
    }

    @Override
    public boolean addChildren(Relation relation) {
        return false;
    }

    @Override
    public List<ElementOnMap> removePairs(String childId) {
        return null;
    }
}
