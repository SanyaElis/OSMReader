package ru.vsu.cs.eliseev.osmreader.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.eliseev.osmreader.entities.OsmRelation;

import java.util.List;

@Repository
public interface OsmRelationRepository extends MongoRepository<OsmRelation, String> {

    List<OsmRelation> findByChildId(String childId);

    List<OsmRelation> findByParentId(String parentId);

    void deleteAllByChildId(String childId);

    long countByParentId(String parentId);

}
