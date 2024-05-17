package ru.vsu.cs.eliseev.osmreader.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Ways")
@TypeAlias("Way")
@Getter
@Setter
public class Way extends ElementOnMap{

    private final List<Node> nodes;

    public Way(String id) {
        super(id);
        this.nodes = new ArrayList<>();
    }

    public void addNode(Node node) {
        if (node != null)
            nodes.add(node);
    }
}
