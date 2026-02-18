package edu.eci.arsw.blueprints.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BlueprintDTO {
    private String author;
    private String name;
    private List<PointDTO> points = new ArrayList<>();
}
