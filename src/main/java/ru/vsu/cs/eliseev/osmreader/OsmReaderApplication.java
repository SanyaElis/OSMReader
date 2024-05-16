package ru.vsu.cs.eliseev.osmreader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.xml.sax.SAXException;
import ru.vsu.cs.eliseev.osmreader.models.ElementOnMap;
import ru.vsu.cs.eliseev.osmreader.models.Relation;
import ru.vsu.cs.eliseev.osmreader.parser.OSMParser;
import ru.vsu.cs.eliseev.osmreader.repositories.RelationRepository;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@SpringBootApplication
public class OsmReaderApplication {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        ApplicationContext context = SpringApplication.run(OsmReaderApplication.class, args);
        OSMParser parser = context.getBean(OSMParser.class);
        RelationRepository relationRepository = context.getBean(RelationRepository.class);

        Map<String, ElementOnMap> result = parser.parse(new File("src/main/java/ru/vsu/cs/eliseev/osmreader/osmdata/output.osm"));
        for ( Map.Entry<String, ElementOnMap> entry : result.entrySet() ) {
            String key = entry.getKey();
            if (key.charAt(0) == 'R'){
                relationRepository.insert((Relation) entry.getValue());
            }
        }

//            ObjectMapper objectMapper = new ObjectMapper();
//            ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
//            writer.writeValue(Paths.get("src/main/java/ru/vsu/cs/eliseev/osmreader/osmdata/test1.json").toFile(), result.values());

    }

}
