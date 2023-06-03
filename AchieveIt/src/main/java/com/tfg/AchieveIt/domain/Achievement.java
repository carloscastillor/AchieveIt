package com.tfg.AchieveIt.domain;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "achievement_table")
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_Achievement")
    @Column(name = "id")
    Long id;
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(targetEntity = Videogame.class)
    @JoinColumn(name = "videogame_id")
    private Videogame videogame;

    @ManyToMany(mappedBy = "achievements")
    private Set<User> users;

    public Achievement(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Achievement() {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Videogame getVideogame() {
        return videogame;
    }

    public void setVideogame(Videogame videogame) {
        this.videogame = videogame;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
