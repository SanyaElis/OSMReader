package ru.vsu.cs.eliseev.osmreader.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.eliseev.osmreader.dto.ResolvedOSMDTO;
import ru.vsu.cs.eliseev.osmreader.entities.Node;
import ru.vsu.cs.eliseev.osmreader.entities.OsmRelation;
import ru.vsu.cs.eliseev.osmreader.entities.Relation;
import ru.vsu.cs.eliseev.osmreader.entities.Way;
import ru.vsu.cs.eliseev.osmreader.enums.OSMType;
import ru.vsu.cs.eliseev.osmreader.kafka.producer.KafkaSender;
import ru.vsu.cs.eliseev.osmreader.repositories.OsmRelationRepository;
import ru.vsu.cs.eliseev.osmreader.services.NodeService;
import ru.vsu.cs.eliseev.osmreader.services.OsmRelationService;
import ru.vsu.cs.eliseev.osmreader.services.RelationService;
import ru.vsu.cs.eliseev.osmreader.services.WayService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class OsmRelationImpl implements OsmRelationService {

    private final NodeService nodeService;
    private final OsmRelationRepository osmRelationRepository;
    private final WayService wayService;
    private final RelationService relationService;
    private final KafkaSender kafkaSender;
    @Value("${spring.kafka.topic.name.for-input-topic}")
    private String topic;

    /**
     * Checks if all child nodes (Node) of the given Way object are present in the database.
     * If any node is missing, it is added to the auxiliary collection `osm_relation`
     * to track incomplete parent objects.
     *
     * @param way a Way object containing a list of child nodes (Nodes).
     */
    @Override
    public void addChildren(Way way) {
        Set<String> nodesInWay = new HashSet<>(way.getNodes());
        int addedCount = 0;
        for (String nodeReference : nodesInWay) {
            if (addRelation(nodeReference, way.getId(),
                    OSMType.NODE, OSMType.WAY)) {
                addedCount++;
            }
        }
        if (addedCount == 0) {
            sendMessage(way.getId(), OSMType.WAY);
        }
    }

    @Override
    public void addChildren(Relation relation) {
        List<Relation.Member> members = relation.getMembers();
        int addedCount = 0;
        for (Relation.Member member : members) {
            if (addRelation(member.refMember(), relation.getId(),
                    OSMType.fromString(member.type()), OSMType.RELATION)) {
                addedCount++;
            }
        }
        if (addedCount == 0) {
            sendMessage(relation.getId(), OSMType.RELATION);
            log.info("Collecting parent with id: {} and type {}",
                    relation.getId(), OSMType.RELATION);
        }
    }

    @Override
    @Transactional
    public void removePairs(String childId, OSMType childType) {
        if (childType.equals(OSMType.NODE)) {
            sendMessage(childId, OSMType.NODE);
        }
        List<OsmRelation> pairs = osmRelationRepository.findByChildId(childId);
        osmRelationRepository.deleteAllByChildId(childId);
        for (OsmRelation osmRelation : pairs) {
            if (OSMType.RELATION.equals(osmRelation.getParentType()) &&
                    (OSMType.WAY.equals(childType) || OSMType.RELATION.equals(childType))) {
                osmRelationRepository.saveAll(getInnerChildren(osmRelation.getParentId(),
                        osmRelation.getParentType(), childId));
            }
            long countRecords = osmRelationRepository.countByParentId(osmRelation.getParentId());
            if (countRecords > 0)
                continue;
            log.info("Collecting parent with id: {} and type {}",
                    osmRelation.getParentId(), osmRelation.getParentType());
            sendMessage(osmRelation.getParentId(), osmRelation.getParentType());
        }
    }

    private boolean addRelation(String childId, String parentId,
                                OSMType childType, OSMType parentType) {
        List<OsmRelation> osmRelations = new ArrayList<>();
        switch (childType) {
            case NODE:
                Node currNode = nodeService.findById(childId);
                if (currNode != null)
                    return false;
                break;
            case WAY:
                Way currWay = wayService.findById(childId);
                if (currWay != null) {
                    List<OsmRelation> osmRelationsInWay = getInnerChildren(parentId, parentType, currWay.getId());
                    osmRelations.addAll(osmRelationsInWay);
                    if (!osmRelationsInWay.isEmpty()) {
                        return false;
                    }
                }
                break;
            case RELATION:
                Relation relation = relationService.findById(childId);
                if (relation != null) {
                    List<OsmRelation> osmRelationsInRelation = getInnerChildren(parentId, parentType, relation.getId());
                    osmRelations.addAll(osmRelationsInRelation);
                    if (!osmRelationsInRelation.isEmpty()) {
                        return false;
                    }
                }
                break;
            default:
                log.error("Unknown child id: {}", childId);
        }
        OsmRelation osmRelation = OsmRelation.builder()
                .childId(childId)
                .parentId(parentId)
                .childType(childType)
                .parentType(parentType)
                .build();
        osmRelations.add(osmRelation);
        osmRelationRepository.saveAll(osmRelations);
        return true;
    }

    private List<OsmRelation> getInnerChildren(String parentId, OSMType parentType,
                                               String childId) {
        List<OsmRelation> relationsInWay = osmRelationRepository.findByParentId(childId);
        List<OsmRelation> innerChildRelations = new ArrayList<>();
        for (OsmRelation osmRelation : relationsInWay) {
            OsmRelation curRelation = OsmRelation.builder()
                    .childId(osmRelation.getChildId())
                    .parentId(parentId)
                    .childType(osmRelation.getChildType())
                    .parentType(parentType)
                    .build();
            innerChildRelations.add(curRelation);
        }
        return innerChildRelations;
    }

    private void sendMessage(String parentId, OSMType type) {
        ResolvedOSMDTO resolvedOsmDTO = new ResolvedOSMDTO(parentId, type);
        kafkaSender.send(topic, resolvedOsmDTO);
    }
}
