package com.tfg.achieveit.domain.VGproperties;

public class Developer {

    Integer id;
    String name;

    String surname;

    public Developer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Developer(Integer id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
