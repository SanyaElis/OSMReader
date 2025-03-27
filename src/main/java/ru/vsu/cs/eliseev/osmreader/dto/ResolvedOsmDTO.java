package ru.vsu.cs.eliseev.osmreader.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResolvedOsmDTO {
    private String id;
    private String type;
}