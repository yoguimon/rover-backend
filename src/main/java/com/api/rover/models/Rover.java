package com.api.rover.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rover")
public class Rover {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "posx")
    private Integer x;
    @Column(name = "posy")
    private Integer y;
    @Enumerated(EnumType.STRING)
    @Column(name = "direction")
    private Direction direction;

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Rover{" +
                "x=" + x +
                ", y=" + y +
                ", direction=" + direction +
                '}';
    }
}
