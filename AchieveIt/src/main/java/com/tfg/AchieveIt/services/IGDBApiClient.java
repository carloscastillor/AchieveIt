package com.tfg.AchieveIt.services;

import com.api.igdb.apicalypse.APICalypse;
import com.api.igdb.apicalypse.Sort;
import com.api.igdb.exceptions.RequestException;
import com.api.igdb.request.IGDBWrapper;
import com.api.igdb.request.ProtoRequestKt;
import com.api.igdb.request.TwitchAuthenticator;
import com.api.igdb.utils.TwitchToken;
import proto.Game;


import java.util.List;

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

   public List<Game> searchGames(int page) throws RequestException {
        int offset = (page) * limit;
       APICalypse apicalypse = new APICalypse()
               .fields("name, genres.name, platforms.name, involved_companies").limit(limit).offset(offset).sort("name", Sort.ASCENDING);
       List<Game> games = ProtoRequestKt.games(this.igdbWrapper, apicalypse);
       return games;
   }

   public List<proto.Genre> searchGenres(int page) throws RequestException {
       int offset = (page) * limit;
       List<proto.Genre> genres = ProtoRequestKt.genres(this.igdbWrapper, new APICalypse().fields("name").limit(limit).offset(offset));
       return genres;
   }

    public List<proto.Platform> searchPlatforms(int page) throws RequestException {
        int offset = (page) * limit;
        List<proto.Platform> platforms = ProtoRequestKt.platforms(this.igdbWrapper, new APICalypse().fields("name").limit(limit).offset(offset));
        return platforms;
    }

    public List<proto.Company> searchCompanies(int page) throws RequestException{
        int offset = (page)*limit;
        List<proto.Company> companies = ProtoRequestKt.companies(this.igdbWrapper, new APICalypse().fields("*").limit(limit).offset(offset).sort("name", Sort.ASCENDING));
        return companies;
    }
}
