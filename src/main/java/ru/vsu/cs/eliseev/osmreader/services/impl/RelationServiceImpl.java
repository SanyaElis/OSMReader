package ru.vsu.cs.eliseev.osmreader.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.cs.eliseev.osmreader.models.Relation;
import ru.vsu.cs.eliseev.osmreader.repositories.RelationRepository;
import ru.vsu.cs.eliseev.osmreader.services.RelationService;

@Service
public class RelationServiceImpl implements RelationService {

    private final RelationRepository repository;

    @Autowired
    public RelationServiceImpl(RelationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Relation relation) {
        repository.insert(relation);
    }
}
