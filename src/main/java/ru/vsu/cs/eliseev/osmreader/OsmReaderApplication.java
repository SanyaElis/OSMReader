package ru.vsu.cs.eliseev.osmreader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.xml.sax.SAXException;
import ru.vsu.cs.eliseev.osmreader.models.ElementOnMap;
import ru.vsu.cs.eliseev.osmreader.models.Node;
import ru.vsu.cs.eliseev.osmreader.models.Relation;
import ru.vsu.cs.eliseev.osmreader.models.Way;
import ru.vsu.cs.eliseev.osmreader.components.OSMParser;
import ru.vsu.cs.eliseev.osmreader.repositories.NodeRepository;
import ru.vsu.cs.eliseev.osmreader.repositories.RelationRepository;
import ru.vsu.cs.eliseev.osmreader.repositories.WayRepository;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class OsmReaderApplication {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        ApplicationContext context = SpringApplication.run(OsmReaderApplication.class, args);
        OSMParser parser = context.getBean(OSMParser.class);
        RelationRepository relationRepository = context.getBean(RelationRepository.class);
        WayRepository wayRepository = context.getBean(WayRepository.class);
        NodeRepository nodeRepository = context.getBean(NodeRepository.class);
        relationRepository.deleteAll();
        wayRepository.deleteAll();
        nodeRepository.deleteAll();
        Map<String, ElementOnMap> result = parser.parse(new File("src/main/java/ru/vsu/cs/eliseev/osmreader/osmdata/output.osm"));
        for (Map.Entry<String, ElementOnMap> entry : result.entrySet()) {
            String key = entry.getKey();
             try {
                switch (key.charAt(0)) {
                    case 'R':
                        relationRepository.insert((Relation) entry.getValue());
                        break;
                    case 'W':
                        wayRepository.insert((Way) entry.getValue());
                        break;
                    case 'N':
                        nodeRepository.insert((Node) entry.getValue());
                        break;
                    default:
                        System.out.println("Something go wrong");

                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        List<Node> res = nodeRepository.findByLocationNear(new Point(51.6776060, 39.1908793), new Distance(1, Metrics.KILOMETERS));
        System.out.println("");
    }

}
