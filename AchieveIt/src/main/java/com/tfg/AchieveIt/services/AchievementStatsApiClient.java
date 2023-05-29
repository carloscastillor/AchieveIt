package com.tfg.AchieveIt.services;

import com.google.gson.*;

import java.net.*;
import java.net.http.*;
import java.util.ArrayList;
import java.util.List;

public class AchievementStatsApiClient {
    private static final String BASE_URL = "https://api.achievementstats.com/games/";
    private static final String API_KEY = "099645fd630414f5491152452";

    public String searchAchievements(String appId) {
        HttpClient httpClient = HttpClient.newHttpClient();

        String url = BASE_URL + appId + "/achievements/?key=" + API_KEY;
        URI uri = URI.create(url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        String responseBody = "Not achievement found";
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            responseBody = response.body();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseBody;
    }

    public List<VideogameAS>  searchGames() {
        HttpClient httpClient = HttpClient.newHttpClient();

        String url = BASE_URL + "/?key=" + API_KEY;
        URI uri = URI.create(url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        List<VideogameAS> videogameAsList = new ArrayList<>();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();
            JsonArray gamesArray = JsonParser.parseString(responseBody).getAsJsonArray();

            for (JsonElement gameElement : gamesArray) {
                JsonObject gameObj = gameElement.getAsJsonObject();
                String gameId = gameObj.get("appid").getAsString();
                String gameName = gameObj.get("name").getAsString();

                VideogameAS videogameAS= new VideogameAS(gameId, gameName);
                videogameAsList.add(videogameAS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return videogameAsList;
    }

    public class VideogameAS {
        private String appId;
        private String name;

        public VideogameAS(String appId, String name) {
            this.appId = appId;
            this.name = name;
        }

        public String getAppId() {
            return appId;
        }

        public String getName() {
            return name;
        }
    }
}
