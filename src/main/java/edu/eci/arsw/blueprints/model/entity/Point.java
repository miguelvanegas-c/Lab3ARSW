package edu.eci.arsw.blueprints.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int x;
    private int y;
    @ManyToOne
    @JoinColumn(name = "blueprint_id")
    private Blueprint blueprint;

    public Point(int x,int y){
        this.x = x;
        this.y = y;
    }



}