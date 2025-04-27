package ru.vsu.cs.eliseev.osmreader.enums;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum OSMType {
    NODE,
    WAY,
    RELATION;

    public static OSMType fromString(String value) {
        if (value == null) {
            log.error("OSMType value cannot be null");
            throw new IllegalArgumentException("OSMType value cannot be null");
        }
        try {
            return OSMType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Invalid OSMType value: {}", value);
            throw new IllegalArgumentException("Unknown OSMType: " + value);
        }
    }
}
