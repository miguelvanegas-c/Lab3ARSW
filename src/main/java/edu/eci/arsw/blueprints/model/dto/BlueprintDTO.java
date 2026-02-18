package edu.eci.arsw.blueprints.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class BlueprintDTO {
    private String author;
    private String name;
    private List<PointDTO> points = new ArrayList<>();
}
