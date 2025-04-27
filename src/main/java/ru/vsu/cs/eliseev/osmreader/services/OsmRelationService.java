package ru.vsu.cs.eliseev.osmreader.services;

import ru.vsu.cs.eliseev.osmreader.entities.Relation;
import ru.vsu.cs.eliseev.osmreader.entities.Way;
import ru.vsu.cs.eliseev.osmreader.enums.OSMType;

public interface OsmRelationService {
    void addChildren(Way way);

    void addChildren(Relation relation);

    void removePairs(String childId, OSMType childType);
}
