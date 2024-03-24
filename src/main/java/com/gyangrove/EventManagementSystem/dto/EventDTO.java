package com.gyangrove.EventManagementSystem.dto;

import java.sql.Date;

public class EventDTO {
    public String event_name;
    public String city_name;
    public Date date;
    public String weather;
    public double distance_km;

    public EventDTO(){}

    public String getEvent_name() {
        return event_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public Date getDate() {
        return date;
    }

    public String getWeather() {
        return weather;
    }

    public double getDistance_km() {
        return distance_km;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setDistance_km(double distance_km) {
        this.distance_km = distance_km;
    }
}
