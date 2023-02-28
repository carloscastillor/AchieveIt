package com.tfg.achieveit.repository;

import org.springframework.stereotype.Repository;

@Repository
public class VideogameDAO {

    final String INSERT_VIDEOGAME = "";
    private final String DELETE_VIDEOGAME = "delete from album where titulo = ?";
    private final String FIND_ALL = "";
    private final String FIND_BY_NAME = "";
    private final String FIND_BY_GENRE = "";
    private final String FIND_BY_DEVELOPER = "";
    private final String FIND_BY_PUBLISHER = "";
    private final String FIND_BY_PLATFORM = "";
}
