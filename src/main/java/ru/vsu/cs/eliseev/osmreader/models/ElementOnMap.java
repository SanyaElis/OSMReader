package ru.vsu.cs.eliseev.osmreader.models;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public abstract class ElementOnMap {
    protected long id;
    protected String user;
    protected long uid;
    protected boolean visible;
    protected String timestamp;
    protected long changeset;
    protected int version;
    protected Map<String, String> tags;

    public ElementOnMap(long id) {
        this.id = id;
        version = 1;
        visible = true;
        tags = new HashMap<>();
    }

    public abstract String getId();

    public void addTag(String key, String value) {
        tags.put(key, value);
    }

    public void deleteTag(String key) {
        tags.remove(key);
    }
}
