package ru.vsu.cs.eliseev.osmreader.components;

public class FileReader {
//    public void writeDataFromFileToDB(){
//        OSMParser parser = context.getBean(OSMParser.class);
//        RelationRepository relationRepository = context.getBean(RelationRepository.class);
//        WayRepository wayRepository = context.getBean(WayRepository.class);
//        NodeRepository nodeRepository = context.getBean(NodeRepository.class);
//        relationRepository.deleteAll();
//        wayRepository.deleteAll();
//        nodeRepository.deleteAll();
//        Map<String, ElementOnMap> result = parser.parse(new File("src/main/java/ru/vsu/cs/eliseev/osmreader/osmdata/output.osm"));
//        for (Map.Entry<String, ElementOnMap> entry : result.entrySet()) {
//            String key = entry.getKey();
//            try {
//                switch (key.charAt(0)) {
//                    case 'R':
//                        relationRepository.insert((Relation) entry.getValue());
//                        break;
//                    case 'W':
//                        wayRepository.insert((Way) entry.getValue());
//                        break;
//                    case 'N':
//                        nodeRepository.insert((Node) entry.getValue());
//                        break;
//                    default:
//                        System.out.println("Something go wrong");
//
//                }
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }
//        List<Node> res = nodeRepository.findByLocationNear(new Point(51.6776060, 39.1908793), new Distance(1, Metrics.KILOMETERS));
//        System.out.println("");
//    }
}
