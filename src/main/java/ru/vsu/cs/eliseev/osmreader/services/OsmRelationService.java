package ru.vsu.cs.eliseev.osmreader.services;

import ru.vsu.cs.eliseev.osmreader.entities.ElementOnMap;
import ru.vsu.cs.eliseev.osmreader.entities.Relation;
import ru.vsu.cs.eliseev.osmreader.entities.Way;

import java.util.List;

public interface OsmRelationService {
    boolean addChildren(Way way);
    boolean addChildren(Relation relation);
    void removePairs(String childId);
}
