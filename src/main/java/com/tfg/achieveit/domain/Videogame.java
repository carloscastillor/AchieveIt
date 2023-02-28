package com.tfg.achieveit.domain;

import com.tfg.achieveit.domain.VGproperties.*;

public class Videogame {

    Integer id;
    String name;
    Genre genre;
    Developer developer;
    Publisher publisher;
    Platform platform;

    public Videogame(Integer id, String name, Genre genre, Developer developer, Publisher publisher, Platform platform) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.developer = developer;
        this.publisher = publisher;
        this.platform = platform;
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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }
}
