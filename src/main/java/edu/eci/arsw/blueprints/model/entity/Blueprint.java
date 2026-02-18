package edu.eci.arsw.blueprints.model.entity;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
public class Blueprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    private String name;
    @OneToMany(mappedBy = "blueprint", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Point> points = new ArrayList<>();

    public Blueprint(String author, String name, List<Point> points){
        this.author = author;
        this.name = name;
        this.points.addAll(points);
    }

    public Blueprint(String author, String name) {
        this.author = author;
        this.name = name;
    }


    public List<Point> getPoints() { return Collections.unmodifiableList(points); }


    public void addPoint(Point p) {
        p.setBlueprint(this);
        points.add(p);
    }

}
