package com.tfg.AchieveIt.domain;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "genre_table")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_Genre")
    @Column(name = "id")
    Long id;

    @Column(name = "name")
    String name;

    @ManyToMany(mappedBy = "genres")
    Set<Videogame> videogames;
    public Genre(String name) {
        this.name = name;
    }

    public Genre() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
