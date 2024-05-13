package ru.vsu.cs.eliseev.osmreader.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.eliseev.osmreader.models.entity.TempDocument;

import java.util.List;

@Repository
public class TestRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void test() {
        List<TempDocument> testList = mongoTemplate.findAll(TempDocument.class);
        TempDocument tempDocument1 = new TempDocument();
        tempDocument1.setHello("Vasya");
        tempDocument1.setId("W12321321");
        mongoTemplate.insert(tempDocument1);
        for (TempDocument tempDocument : testList) {
            System.out.println(tempDocument.getId());
            System.out.println(tempDocument.getHello());
        }
    }
}
