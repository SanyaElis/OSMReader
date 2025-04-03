package ru.vsu.cs.eliseev.osmreader.services;

import ru.vsu.cs.eliseev.osmreader.entities.Relation;
import ru.vsu.cs.eliseev.osmreader.entities.Way;

public interface OsmRelationService {
    void addChildren(Way way);
    void addChildren(Relation relation);
    void removePairs(String childId);
}
