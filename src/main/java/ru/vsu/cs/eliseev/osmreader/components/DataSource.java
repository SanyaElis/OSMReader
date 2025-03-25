package ru.vsu.cs.eliseev.osmreader.components;

import org.xml.sax.InputSource;

import java.io.IOException;

public interface DataSource {
    InputSource getInputSource() throws IOException;
}