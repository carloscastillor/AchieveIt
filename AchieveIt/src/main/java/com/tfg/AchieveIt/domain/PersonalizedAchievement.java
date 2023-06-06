package com.tfg.AchieveIt.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "personalized_achievement_table")
public class PersonalizedAchievement{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_PersonalizedAchievement")
    @Column(name = "id")
    Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(targetEntity = Videogame.class)
    @JoinColumn(name = "videogame_id")
    @JsonBackReference
    private Videogame videogame;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Column(name = "likes")
    private int likes;

    @ManyToMany(mappedBy = "personalizedAchievements")
    private Set<User> users;

    public PersonalizedAchievement() {
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}