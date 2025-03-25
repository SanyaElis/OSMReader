package ru.vsu.cs.eliseev.osmreader.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.eliseev.osmreader.entities.Way;

import java.util.List;

@Repository
public interface WayRepository extends MongoRepository<Way, String> {
    List<Way> findByNodesContaining(String refNode);
}
