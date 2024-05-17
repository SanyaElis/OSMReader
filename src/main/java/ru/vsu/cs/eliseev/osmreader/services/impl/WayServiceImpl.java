package ru.vsu.cs.eliseev.osmreader.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.cs.eliseev.osmreader.models.Way;
import ru.vsu.cs.eliseev.osmreader.repositories.WayRepository;
import ru.vsu.cs.eliseev.osmreader.services.WayService;

@Service
public class WayServiceImpl implements WayService {

    private final WayRepository repository;

    @Autowired
    public WayServiceImpl(WayRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Way way) {
        repository.insert(way);
    }
}
