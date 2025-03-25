package ru.vsu.cs.eliseev.osmreader.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Nodes")
@TypeAlias("Node")
@Getter
@Setter
public class Node extends ElementOnMap {

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private double[] location;

    public Node(String id) {
        super(id);
    }
}
