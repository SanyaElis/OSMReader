package ru.vsu.cs.eliseev.osmreader.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Component
@Primary
public class FileDataSource implements DataSource {

    @Value("${osm.file.path}")
    private String filePath;

    @Override
    public InputSource getInputSource() throws IOException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("OSM file not found");
        }
        return new InputSource(new FileReader(file));
    }
}
