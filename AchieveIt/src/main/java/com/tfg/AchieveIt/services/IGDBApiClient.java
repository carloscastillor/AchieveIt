package com.tfg.AchieveIt.services;

import com.api.igdb.apicalypse.APICalypse;
import com.api.igdb.apicalypse.Sort;
import com.api.igdb.exceptions.RequestException;
import com.api.igdb.request.IGDBWrapper;
import com.api.igdb.request.ProtoRequestKt;
import com.api.igdb.request.TwitchAuthenticator;
import com.api.igdb.utils.TwitchToken;
import proto.Game;
import proto.Genre;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IGDBApiClient {

    private static final String CLIENT_ID = "vbhu03ruzegp7ayp3l06u4gkrrt7u1";
    private static final String CLIENT_SECRET = "dqvm03c81xdm5wr72to54ocmn49ia0";
    private static final int API_VERSION = 4;
    private IGDBWrapper igdbWrapper;

    private final int limit = 500;
    public IGDBApiClient() {
        TwitchAuthenticator tAuth = TwitchAuthenticator.INSTANCE;
        TwitchToken token = tAuth.requestTwitchToken(CLIENT_ID, CLIENT_SECRET);
        this.igdbWrapper = IGDBWrapper.INSTANCE;
        this.igdbWrapper.setCredentials(CLIENT_ID, token.getAccess_token());
    }

   public Set<Game> searchGames(int page) throws RequestException {
        int offset = (page) * limit;
       APICalypse apicalypse = new APICalypse()
               .fields("name, genres.name, platforms.name, involved_companies.*, involved_companies.company.name").limit(limit).offset(offset).sort("name", Sort.ASCENDING);
       List<Game> gamesList = ProtoRequestKt.games(this.igdbWrapper, apicalypse);
       Set<Game> games = new HashSet<>(gamesList);
       return games;
   }

   public Set<proto.Genre> searchGenres(int page) throws RequestException {
       int offset = (page) * limit;
       List<proto.Genre> genresList = ProtoRequestKt.genres(this.igdbWrapper, new APICalypse().fields("name").limit(limit).offset(offset));
       Set<proto.Genre> genres = new HashSet<>(genresList);
       return genres;
   }

    public Set<proto.Platform> searchPlatforms(int page) throws RequestException {
        int offset = (page) * limit;
        List<proto.Platform> platformsList = ProtoRequestKt.platforms(this.igdbWrapper, new APICalypse().fields("name").limit(limit).offset(offset));
        Set<proto.Platform> platforms = new HashSet<>(platformsList);
        return platforms;
    }

    public Set<proto.Company> searchCompanies(int page) throws RequestException{
        int offset = (page)*limit;
        List<proto.Company> companiesList = ProtoRequestKt.companies(this.igdbWrapper, new APICalypse().fields("*").limit(limit).offset(offset).sort("name", Sort.ASCENDING));
        Set<proto.Company> companies = new HashSet<>(companiesList);
        return companies;
    }
}
