package ru.vsu.cs.eliseev.osmreader.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

//@Document(collection = "TestOSM")
@Getter
@Setter
public abstract class ElementOnMap {

    @Id
    private String id;

    private String user;

    private long uid;

    private boolean visible;

    private String timestamp;

    private long changeset;

    private int version;

    private Map<String, String> tags;

    public ElementOnMap(String id) {
        this.id = id;
        version = 1;
        visible = true;
        tags = new HashMap<>();
    }

    public void addTag(String key, String value) {
        tags.put(key, value);
    }

    public void deleteTag(String key) {
        tags.remove(key);
    }
}
