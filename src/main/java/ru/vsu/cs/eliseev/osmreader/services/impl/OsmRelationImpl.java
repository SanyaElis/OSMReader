package ru.vsu.cs.eliseev.osmreader.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.eliseev.osmreader.dto.ResolvedOsmDTO;
import ru.vsu.cs.eliseev.osmreader.entities.Node;
import ru.vsu.cs.eliseev.osmreader.entities.OsmRelation;
import ru.vsu.cs.eliseev.osmreader.entities.Relation;
import ru.vsu.cs.eliseev.osmreader.entities.Way;
import ru.vsu.cs.eliseev.osmreader.kafka.producer.KafkaSender;
import ru.vsu.cs.eliseev.osmreader.repositories.OsmRelationRepository;
import ru.vsu.cs.eliseev.osmreader.services.NodeService;
import ru.vsu.cs.eliseev.osmreader.services.OsmRelationService;
import ru.vsu.cs.eliseev.osmreader.services.RelationService;
import ru.vsu.cs.eliseev.osmreader.services.WayService;

import java.util.List;

@Service
@Slf4j
public class OsmRelationImpl implements OsmRelationService {

    private final NodeService nodeService;
    private final OsmRelationRepository osmRelationRepository;
    private final WayService wayService;
    private final RelationService relationService;
    private final String WAY_CHILD_TYPE = "node";
    private final KafkaSender kafkaSender;

    @Autowired
    public OsmRelationImpl(NodeService nodeService, OsmRelationRepository osmRelationRepository, WayService wayService, RelationService relationService, KafkaSender kafkaSender) {
        this.nodeService = nodeService;
        this.osmRelationRepository = osmRelationRepository;
        this.wayService = wayService;
        this.relationService = relationService;
        this.kafkaSender = kafkaSender;
    }

    /**
     * Checks if all child nodes (Node) of the given Way object are present in the database.
     * If any node is missing, it is added to the auxiliary collection `osm_relation`
     * to track incomplete parent objects.
     *
     * @param way a Way object containing a list of child nodes (Nodes).
     */
    @Override
    public void addChildren(Way way) {
        List<String> nodesInWay = way.getNodes();
        int addedCount = 0;
        for (String nodeReference : nodesInWay) {
            if (addRelation(nodeReference, way.getId(), WAY_CHILD_TYPE)) {
                addedCount++;
            }
        }
        if (addedCount == 0) {
            sendMessage(way.getId(), "way");
        }
    }

    @Override
    public void addChildren(Relation relation) {
        List<Relation.Member> members = relation.getMembers();
        int addedCount = 0;
        for (Relation.Member member : members) {
            if (addRelation(member.refMember(), relation.getId(), member.type())) {
                addedCount++;
            }
        }
        if (addedCount == 0) {
            sendMessage(relation.getId(), "relation");
        }
    }

    @Override
    @Transactional
    public void removePairs(String childId) {
        List<OsmRelation> pairs = osmRelationRepository.findByChildId(childId);
        osmRelationRepository.deleteAllByChildId(childId);
        for (OsmRelation osmRelation : pairs) {
            long countRecords = osmRelationRepository.countByParentId(osmRelation.getParentId());
            if (countRecords > 0)
                continue;
            //todo send to kafka topic
            log.info("Collecting parent with id: {}", osmRelation.getParentId());
            sendMessage(osmRelation.getParentId(), "Test kafka");//todo узнать тип
        }
    }

    private boolean addRelation(String childId, String parentId, String childType) {
        OsmRelation osmRelation;
        switch (childType) {
            case "node":
                Node currNode = nodeService.findById(childId);
                if (currNode != null)
                    return false;
                break;
            case "way":
                Way currWay = wayService.findById(childId);
                if (currWay != null)
                    return false;
                break;
            case "relation":
                Relation relation = relationService.findById(childId);
                if (relation != null)
                    return false;
                break;
            default:
                log.error("Unknown child id: {}", childId);
        }
        osmRelation = OsmRelation.builder()
                .childId(childId)
                .parentId(parentId)
                .build();
        osmRelationRepository.insert(osmRelation);
        return true;
    }

    private void sendMessage(String parentId, String type) {
        ResolvedOsmDTO resolvedOsmDTO = new ResolvedOsmDTO(parentId, type);
        kafkaSender.send("resolved_osm", resolvedOsmDTO);//todo topic name
    }
}
