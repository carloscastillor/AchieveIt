package com.tfg.AchieveIt.services;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tfg.AchieveIt.domain.Achievement;

import java.net.*;
import java.net.http.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SteamApiClient {
    private static final String BASE_URL = "https://api.steampowered.com/ISteamUserStats/GetSchemaForGame/v2/";
    private static final String API_KEY = "F8B89C1EF575A2150965528BC85DEF2F";

    private Cache<String, String> gameCache;

    public SteamApiClient() {
        this.gameCache = Caffeine.newBuilder()
                .maximumSize(Long.MAX_VALUE)
                .build();
    }

    public JsonArray searchAchievements(String appId) {
        HttpClient httpClient = HttpClient.newHttpClient();
        String url = BASE_URL + "?key=" + API_KEY + "&appid=" + appId;
        URI uri = URI.create(url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonObject game = jsonResponse.getAsJsonObject("game");

            if (game.has("availableGameStats")) {
                JsonObject availableGameStats = game.get("availableGameStats").getAsJsonObject();
                JsonArray achievementsArray = availableGameStats.getAsJsonArray("achievements");
                return achievementsArray;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<VideogameSteam> searchGames() {
        HttpClient httpClient = HttpClient.newHttpClient();

        String url = "https://api.steampowered.com/ISteamApps/GetAppList/v2/";
        URI uri = URI.create(url);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        Set<VideogameSteam> videogameSteamSet = new HashSet<>();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonObject appList = jsonObject.getAsJsonObject("applist");
            JsonArray apps = appList.getAsJsonArray("apps");
            int counter = 0;
            int counterLimit = 0;

            for (JsonElement appElement : apps) {
                JsonObject appObj = appElement.getAsJsonObject();
                String appId = appObj.get("appid").getAsString();
                String appName = appObj.get("name").getAsString();

                String appDetailsResponseBodyCache = gameCache.getIfPresent(appId);

                if (appDetailsResponseBodyCache == null) {
                    String appDetailsUrl = "https://store.steampowered.com/api/appdetails?appids=" + appId;
                    URI appDetailsUri = URI.create(appDetailsUrl);
                    HttpRequest appDetailsRequest = HttpRequest.newBuilder()
                            .uri(appDetailsUri)
                            .GET()
                            .build();

                    System.out.print(counter + ": ");
                    counter++;
                    counterLimit++;

                    HttpResponse<String> appDetailsResponse = httpClient.send(appDetailsRequest, HttpResponse.BodyHandlers.ofString());
                    String appDetailsResponseBody = appDetailsResponse.body();
                    //System.out.println(appDetailsResponseBody);
                    JsonObject appDetailsJson = JsonParser.parseString(appDetailsResponseBody).getAsJsonObject().getAsJsonObject(appId);
                    //System.out.println(appDetailsJson);

                    if (appDetailsJson.get("success").getAsBoolean()) {
                        JsonObject appData = appDetailsJson.getAsJsonObject("data");

                        if (appData.has("type") && appData.get("type").getAsString().equalsIgnoreCase("game")) {
                            VideogameSteam videogameSteam = new VideogameSteam(appId, appName);
                            videogameSteamSet.add(videogameSteam);
                            System.out.println(appName);
                        }
                    } else {
                        System.out.println("No se encontraron datos para la aplicación: " + appId);
                    }

                    gameCache.put(appId, appDetailsResponseBody);
                }

                if(counterLimit == 1000){break;}

                if(counter == 200){
                    Thread.sleep(5 * 60 * 1000);
                    counter = 0;
                }
            }

            List<VideogameSteam> videogameSteamList = new ArrayList<>(videogameSteamSet);
            Collections.sort(videogameSteamList, Comparator.comparing(VideogameSteam::getName));

            return videogameSteamList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public class VideogameSteam {
        private String appId;
        private String name;

        public VideogameSteam(String appId, String name) {
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

