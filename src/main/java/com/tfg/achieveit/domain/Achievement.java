package com.tfg.achieveit.domain;

public class Achievement {

    Integer id;
    String name;
    String description;
    Videogame videogame;
    boolean completed;

    public Achievement(Integer id, String name, String description, Videogame videogame) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.videogame = videogame;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
