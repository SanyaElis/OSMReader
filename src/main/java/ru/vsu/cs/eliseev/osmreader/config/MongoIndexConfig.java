package ru.vsu.cs.eliseev.osmreader.config;

import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Component;
import ru.vsu.cs.eliseev.osmreader.entities.OsmRelation;

@Component
public class MongoIndexConfig {
    private final MongoTemplate mongoTemplate;

    public MongoIndexConfig(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @PostConstruct
    public void initIndexes() {
        mongoTemplate.indexOps(OsmRelation.class).ensureIndex(new Index().on("childId", Sort.Direction.ASC));
        mongoTemplate.indexOps(OsmRelation.class).ensureIndex(new Index().on("parentId", Sort.Direction.ASC));
    }
}

