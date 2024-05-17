package ru.vsu.cs.eliseev.osmreader.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.eliseev.osmreader.models.Way;

@Repository
public interface WayRepository extends MongoRepository<Way, String> {

}
