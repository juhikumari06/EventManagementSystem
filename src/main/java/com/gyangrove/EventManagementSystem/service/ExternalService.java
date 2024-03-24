package com.gyangrove.EventManagementSystem.service;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class ExternalService {

    // Template needed to invoke REST API
    RestTemplate restTemplate = new RestTemplate();

    @Value("${weatherURI}")
    String weatherURI;

    @Value("${distanceURI}")
    String distanceURI;

    @Async
    public CompletableFuture<String> getWeather(String city, Date date)
    {
        // complete weather url by adding query params city and date
        String weatherURL = weatherURI + "&city={city}&date={date}";

        // prepare query parameter map by passed value of city and date
        Map<String, String> params = new HashMap<>();
        params.put("city", city);
        params.put("date", String.valueOf(date));

        //invoke rest template by passing url, response return type and query parameter map
        String response = restTemplate.getForObject(weatherURL, String.class, params);

        // prepare json object from response string to get value of weather
        JsonObject responseJsonObject = new Gson().fromJson(response, JsonObject.class);

        // get value for key weather from responseJsonObject as String
        return CompletableFuture.completedFuture(responseJsonObject.get("weather").getAsString());
    }

    @Async
    public CompletableFuture<String> getDistance(double latitude1, double longitude1,double latitude2, double longitude2 )
    {
        String distanceURL = distanceURI + "&latitude1={latitude1}&longitude1={longitude1}&latitude2={latitude2}&longitude2={longitude2}";

        // prepare query parameter map by passed value of longitude and latitude
        Map<String, String> params = new HashMap<>();
        params.put("latitude1", String.valueOf(latitude1));
        params.put("longitude1", String.valueOf(longitude1));
        params.put("latitude2", String.valueOf(latitude2));
        params.put("longitude2", String.valueOf(longitude2));

        //invoke rest template by passing url, response return type and query parameter map
        String response = restTemplate.getForObject(distanceURL, String.class, params);

        // prepare json object from response string to get value of weather
        JsonObject responseJsonObject = new Gson().fromJson(response, JsonObject.class);

        // get value for key weather from responseJsonObject as String
        return CompletableFuture.completedFuture(responseJsonObject.get("distance").getAsString());
    }
}
