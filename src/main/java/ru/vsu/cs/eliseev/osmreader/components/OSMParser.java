package ru.vsu.cs.eliseev.osmreader.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import ru.vsu.cs.eliseev.osmreader.entities.ElementOnMap;
import ru.vsu.cs.eliseev.osmreader.entities.Node;
import ru.vsu.cs.eliseev.osmreader.entities.Relation;
import ru.vsu.cs.eliseev.osmreader.entities.Way;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class OSMParser extends DefaultHandler {

    private final DataSource dataSource;
    private Map<String, ElementOnMap> elements;
    private ElementOnMap current;


    @Autowired
    public OSMParser(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, ElementOnMap> parse() throws IOException, SAXException, ParserConfigurationException {
        elements = new HashMap<>();
        InputSource inputSource = dataSource.getInputSource();

        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        XMLReader xr = parser.getXMLReader();
        xr.setContentHandler(this);
        xr.setErrorHandler(this);
        xr.parse(inputSource);

        return elements;
    }

    private String getId(String type, String ref) {

        return switch (type) {
            case "node" -> "N" + ref;
            case "way" -> "W" + ref;
            case "relation" -> "R" + ref;
            default -> throw new RuntimeException("Unknown element type: " + type);
        };
    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if (qName.equals("node") || qName.equals("way") || qName.equals("relation")) {
            if (current != null) {
                if ((qName.equals("way") && ((Way) current).getNodes().size() >= 2)
                        || qName.equals("node")
                        || (qName.equals("relation") && !((Relation) current).getMembers().isEmpty())) {
                    elements.put(getId(qName, current.getId()), current);
                }
                current = null;
            }
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        switch (qName) {
            case "node":
                Node n = new Node(
                        attributes.getValue("id"));
                n.setLocation(new double[]{Double.parseDouble(attributes.getValue("lat")),
                        Double.parseDouble(attributes.getValue("lon"))});
                n.setUser(attributes.getValue("user"));

                if (attributes.getValue("uid") != null) {
                    n.setUid(Long.parseLong(attributes.getValue("uid")));
                }

                n.setVisible(Boolean.parseBoolean(attributes.getValue("visible")));

                if (attributes.getValue("version") != null) {
                    n.setVersion(Integer.parseInt(attributes.getValue("version")));
                }

                if (attributes.getValue("changeset") != null) {
                    n.setChangeset(Long.parseLong(attributes.getValue("changeset")));
                }

                n.setTimestamp(attributes.getValue("timestamp"));
                current = n;
                break;
            case "way":
                Way w = new Way(attributes.getValue("id"));
                w.setUser(attributes.getValue("user"));

                if (attributes.getValue("uid") != null) {
                    w.setUid(Long.parseLong(attributes.getValue("uid")));
                }

                w.setVisible(Boolean.parseBoolean(attributes.getValue("visible")));

                if (attributes.getValue("version") != null) {
                    w.setVersion(Integer.parseInt(attributes.getValue("version")));
                }

                if (attributes.getValue("changeset") != null) {
                    w.setChangeset(Long.parseLong(attributes.getValue("changeset")));
                }

                w.setTimestamp(attributes.getValue("timestamp"));
                current = w;
                break;
            case "nd":
                ((Way) current).addNode(attributes.getValue("ref"));
                break;
            case "relation":
                Relation r = new Relation(attributes.getValue("id"));
                r.setUser(attributes.getValue("user"));

                if (attributes.getValue("uid") != null) {
                    r.setUid(Long.parseLong(attributes.getValue("uid")));
                }

                r.setVisible(Boolean.parseBoolean(attributes.getValue("visible")));

                if (attributes.getValue("version") != null) {
                    r.setVersion(Integer.parseInt(attributes.getValue("version")));
                }

                if (attributes.getValue("changeset") != null) {
                    r.setChangeset(Long.parseLong(attributes.getValue("changeset")));
                }

                r.setTimestamp(attributes.getValue("timestamp"));
                current = r;
                break;
            case "member":
                //Add member
                ((Relation) current).addMember(
                        attributes.getValue("role"),
                        attributes.getValue("type"),
                        attributes.getValue("ref")
                );
                break;
            //Case of tag
            case "tag":
                if (current != null) {
                    current.addTag(attributes.getValue("k"), attributes.getValue("v"));
                }
                break;
        }
    }
}
