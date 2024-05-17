package ru.vsu.cs.eliseev.osmreader.services;

import org.springframework.stereotype.Component;
import ru.vsu.cs.eliseev.osmreader.models.Node;

@Component
public interface NodeService {
    void create(Node node);
}
