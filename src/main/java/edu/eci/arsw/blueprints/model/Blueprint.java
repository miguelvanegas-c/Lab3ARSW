package edu.eci.arsw.blueprints.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Blueprint {

    @Id
    private String id;
    private String author;
    private String name;
    @OneToMany(mappedBy = "blueprint, cascade = cascade")
    private List<Point> points = new ArrayList<>();

    public Blueprint(String author, String name, List<Point> points){
        this.id = author + ":" + name;
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
