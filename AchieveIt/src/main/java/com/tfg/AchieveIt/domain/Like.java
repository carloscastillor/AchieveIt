package com.tfg.AchieveIt.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "like_table")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = PersonalizedAchievement.class)
    @JoinColumn(name = "personalized_achievement_id")
    private PersonalizedAchievement personalizedAchievement;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    public Like() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonalizedAchievement getPersonalizedAchievement() {
        return personalizedAchievement;
    }

    public void setPersonalizedAchievement(PersonalizedAchievement personalizedAchievement) {
        this.personalizedAchievement = personalizedAchievement;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
