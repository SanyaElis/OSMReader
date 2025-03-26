package ru.vsu.cs.eliseev.osmreader.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;
import ru.vsu.cs.eliseev.osmreader.components.OSMParser;
import ru.vsu.cs.eliseev.osmreader.entities.ElementOnMap;
import ru.vsu.cs.eliseev.osmreader.entities.Node;
import ru.vsu.cs.eliseev.osmreader.entities.Relation;
import ru.vsu.cs.eliseev.osmreader.entities.Way;
import ru.vsu.cs.eliseev.osmreader.exceptions.DataImportException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class DataImportService {
    private final OSMParser parser;
    private final NodeService nodeService;
    private final RelationService relationService;
    private final WayService wayService;
    private final OsmRelationService osmRelationService;

    @Autowired
    public DataImportService(OSMParser parser, NodeService nodeService, RelationService relationService, WayService wayService, OsmRelationService osmRelationService) {
        this.parser = parser;
        this.nodeService = nodeService;
        this.relationService = relationService;
        this.wayService = wayService;
        this.osmRelationService = osmRelationService;
    }

    @Transactional
    public void importData() {
        try {
            Map<String, ElementOnMap> elements = parser.parse();
            saveElements(elements);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            throw new DataImportException("Data import failed", e);
        }
    }

    private void saveElements(Map<String, ElementOnMap> elements) {
        elements.forEach((key, element) -> {
            try {
                switch (key.charAt(0)) {
                    case 'R':
                        Relation relation = (Relation) element;
                        relationService.create(relation);
                        osmRelationService.addChildren(relation);
                        osmRelationService.removePairs(relation.getId());
                        break;
                    case 'W':
                        Way way = (Way) element;
                        wayService.create((Way) element);
                        osmRelationService.addChildren(way);
                        osmRelationService.removePairs(way.getId());
                        break;
                    case 'N':
                        Node node = (Node) element;
                        nodeService.create((Node) element);
                        osmRelationService.removePairs(node.getId());
                        break;
                    default:
                        log.error("Unknown element type for element: {}", key);
                }
            } catch (Exception e) {
                log.error("Error while saving element: {}", key, e);
            }
        });
    }
}
