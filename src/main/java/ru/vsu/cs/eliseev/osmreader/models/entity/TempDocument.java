package ru.vsu.cs.eliseev.osmreader.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "temp")
@Getter
@Setter
public class TempDocument {
    @Id
    private String id;
    private String hello;
}
