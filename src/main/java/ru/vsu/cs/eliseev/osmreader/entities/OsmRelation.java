package ru.vsu.cs.eliseev.osmreader.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.vsu.cs.eliseev.osmreader.enums.OSMType;

@Document(collection = "osm_relation")
@TypeAlias("OsmRelation")
@Getter
@Setter
@Builder
public class OsmRelation {
    @Id
    private String id;

    @Indexed
    private String childId;
    @Indexed
    private String parentId;

    private OSMType childType;

    private OSMType parentType;

}
