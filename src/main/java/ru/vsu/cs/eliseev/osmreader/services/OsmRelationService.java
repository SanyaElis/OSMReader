package ru.vsu.cs.eliseev.osmreader.services;

import org.springframework.stereotype.Component;
import ru.vsu.cs.eliseev.osmreader.entities.ElementOnMap;
import ru.vsu.cs.eliseev.osmreader.entities.Relation;
import ru.vsu.cs.eliseev.osmreader.entities.Way;

import java.util.List;

@Component
public interface OsmRelationService {
    boolean addChildren(Way way);
    boolean addChildren(Relation relation);
    List<ElementOnMap> removePairs(String childId);
}
