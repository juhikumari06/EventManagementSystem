package com.gyangrove.EventManagementSystem.service;

import com.gyangrove.EventManagementSystem.dto.EventDTO;
import com.gyangrove.EventManagementSystem.entities.Event;
import com.gyangrove.EventManagementSystem.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class EventService{

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ExternalService externalService;

    public void createEvent(Event event) {
        eventRepository.save(event);

    }

    /**
     * Get events of next 14 days from given date and sort it by date
     *
     * check weather on the basis of location and date and also
     * Calculate the distance between the user's location and the event location.
     *
     * */
    public List<EventDTO> findEvents(Date date, double latitude, double longitude, int page, int pageSize)
    {
        //Adding Date Range in the Query
        Date endDate = Date.valueOf(date.toLocalDate().plusDays(14));

        //Adding Pagination in the Query Response
        Pageable pageable = PageRequest.of(page, pageSize);

        //Calling repository to get all records by passing start date, end date and page request
        List<Event> events = eventRepository.findAllByDateBetweenOrderByDateAsc(date, endDate, pageable);

        // calling method to populate weather , distance and prepare final response
        return populateWeatherDistanceAndPrepareResponse(events,latitude,longitude);
    }

    /**
     * Enrich response by calling external api for weather and distance
     * */
    public List<EventDTO> populateWeatherDistanceAndPrepareResponse(List<Event> events,double latitude, double longitude)
    {
        // List To store final response
        List<EventDTO> eventDTOS = new ArrayList<>();

        // iterate each events
        for(Event event : events)
        {
            // get weather for the city on the event date
            CompletableFuture<String> weather = externalService.getWeather(event.getCity_name(),event.getDate());

            // get distance for the city from user location
            CompletableFuture<String> distance = externalService.getDistance(event.getLatitude(),event.getLongitude(),latitude,longitude);

            // prepare response DTO by populating required fields
            EventDTO eventDTO = new EventDTO();
            eventDTO.setEvent_name(event.getEvent_name());
            eventDTO.setCity_name(event.getCity_name());
            eventDTO.setDate(event.getDate());

            try {
                eventDTO.setWeather(weather.get());
                eventDTO.setDistance_km(Double.parseDouble(distance.get()));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

            // storing the prepared response DTO to response list
            eventDTOS.add(eventDTO);
        }

        // once response prepared for all records , then return final response
        return eventDTOS;
    }
}
