package com.tfg.AchieveIt.domain;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "platform_table")
public class Platform {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_Platform")
    @Column(name = "id")
    Long id;
    @Column(name = "name")
    String name;

    @ManyToMany(mappedBy = "developers")
    Set<Videogame> videogames;
    public Platform(String name) {
        this.name = name;
    }

    public Platform() {

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
