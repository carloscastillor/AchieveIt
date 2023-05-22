package com.tfg.AchieveIt.domain;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "publisher_table")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_Publisher")
    @Column(name = "id")
    Long id;
    @Column(name = "name")
    String name;
    @ManyToMany(mappedBy = "developers")
    Set<Videogame> videogames;

    public Publisher(String name) {
        this.name = name;
    }

    public Publisher() {

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
