package edu.eci.arsw.blueprints.model;

import jakarta.persistence.*;

import lombok.Data;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Entity
@Data
public class Blueprint {

    @Id
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
        points.add(p);
    }

}
