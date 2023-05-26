package com.tfg.AchieveIt.domain;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "user_table")
public class User {

    public User() {

    }

    public enum Provider {
        LOCAL, GOOGLE
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_User")
    private Long id;
    @Column(name = "userName")
    String userName;
    @Column(name = "name")
    String name;
    @Column(name = "email")
    String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private Provider provider;

    @ManyToMany(targetEntity = Videogame.class)
    @JoinTable(
            name = "user_videogame",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "videogame_id")
    )
    Set<Videogame> videogames;

    public User(String userName, String name, String email) {
        this.userName = userName;
        this.name = name;
        this.email = email;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Provider getProvider() {return provider;}

    public void setProvider(Provider provider) {this.provider = provider;}

    public Set<Videogame> getVideogames() {
        return videogames;
    }

    public void setVideogames(Set<Videogame> videogames) {
        this.videogames = videogames;
    }

    public void addVideogame(Videogame videogame){this.videogames.add(videogame);}
}
