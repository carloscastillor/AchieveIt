package com.tfg.AchieveIt.services;

import java.net.*;
import java.net.http.*;

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
}
