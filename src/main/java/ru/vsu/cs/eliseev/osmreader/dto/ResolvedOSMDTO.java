package ru.vsu.cs.eliseev.osmreader.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vsu.cs.eliseev.osmreader.enums.OSMType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResolvedOSMDTO {
    private String id;
    private OSMType type;
}