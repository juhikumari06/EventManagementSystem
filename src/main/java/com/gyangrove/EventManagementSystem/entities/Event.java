package com.gyangrove.EventManagementSystem.entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String event_name;
    private String city_name;
    @org.hibernate.annotations.Index(name = "date")
    private Date date;
    private String time;
    private double latitude;
    private double longitude;

    public Event(String event_name, String city_name, Date date, String time, double latitude, double longitude) {
        this.event_name = event_name;
        this.city_name = city_name;
        this.date = date;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public Event()
    {

    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
