package com.tfg.AchieveIt.domain;
import jakarta.persistence.*;

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
}
