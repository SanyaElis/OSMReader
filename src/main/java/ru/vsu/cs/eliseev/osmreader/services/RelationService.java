package ru.vsu.cs.eliseev.osmreader.services;

import org.springframework.stereotype.Component;
import ru.vsu.cs.eliseev.osmreader.models.Relation;

@Component
public interface RelationService {
    void create(Relation relation);
}
