package ru.vsu.cs.eliseev.osmreader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.vsu.cs.eliseev.osmreader.repositories.NodeRepository;
import ru.vsu.cs.eliseev.osmreader.repositories.OsmRelationRepository;
import ru.vsu.cs.eliseev.osmreader.repositories.RelationRepository;
import ru.vsu.cs.eliseev.osmreader.repositories.WayRepository;
import ru.vsu.cs.eliseev.osmreader.services.*;

@SpringBootApplication
public class OsmReaderApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OsmReaderApplication.class, args);
        NodeRepository nodeRepository = context.getBean(NodeRepository.class);
        WayRepository wayRepository = context.getBean(WayRepository.class);
        RelationRepository relationRepository = context.getBean(RelationRepository.class);
        OsmRelationRepository osmRelationRepository = context.getBean(OsmRelationRepository.class);
        DataImportService dataImportService = context.getBean(DataImportService.class);

        nodeRepository.deleteAll();
        wayRepository.deleteAll();
        relationRepository.deleteAll();
        osmRelationRepository.deleteAll();
        dataImportService.importData();
    }

}
