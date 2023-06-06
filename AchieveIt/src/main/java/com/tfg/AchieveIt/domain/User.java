package com.tfg.AchieveIt.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(name = "isLoggedIn")
    boolean isLoggedIn;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private Provider provider;

    @ManyToMany(targetEntity = Videogame.class)
    @JoinTable(
            name = "user_videogame",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "videogame_id")
    )
    @JsonIgnore
    Set<Videogame> videogames;

    @ManyToMany(targetEntity = Achievement.class)
    @JoinTable(
            name = "user_achievement",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "achievement_id")
    )
    @JsonIgnore
    private Set<Achievement> achievements;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    Set<PersonalizedAchievement> personalizedAchievements;

    private String token;

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
    public void removeVideogame(Videogame videogame){this.videogames.remove(videogame);}

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<Achievement> getAchievements() {
        return achievements;
    }

    public void addAchievement(Achievement achievement) {
        this.achievements.add(achievement);
    }

    public void removeAchievement(Achievement achievement){
        this.achievements.remove(achievement);
    }
    public void addPersonalizedAchievement(PersonalizedAchievement achievement) {
        this.personalizedAchievements.add(achievement);
    }

    public void removePersonalizedAchievement(PersonalizedAchievement achievement){
        this.personalizedAchievements.remove(achievement);
    }
}
