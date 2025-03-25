package ru.vsu.cs.eliseev.osmreader.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.eliseev.osmreader.entities.Relation;

import java.util.List;

@Repository
public interface RelationRepository extends MongoRepository<Relation, String> {
    List<Relation> findByMembersRefMember(String refMember);
}
