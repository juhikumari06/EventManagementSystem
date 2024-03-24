package com.gyangrove.EventManagementSystem.controller;

import com.gyangrove.EventManagementSystem.dto.EventDTO;
import com.gyangrove.EventManagementSystem.entities.Event;
import com.gyangrove.EventManagementSystem.service.CSVService;
import com.gyangrove.EventManagementSystem.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

@RestController
public class EventController {

    @Autowired
    EventService eventService;

    @Autowired
    CSVService csvService;


    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String myService(@RequestParam("file") MultipartFile file,
                                          @RequestParam("id") String id) throws Exception {

        if (!file.isEmpty()) {

            csvService.save(file);
        }
        return "some json";

    }

    @PostMapping("/upload")
    public void uploadEvents(@RequestParam("file") MultipartFile file)
    {
        csvService.save(file);
    }

    @PostMapping("/event")
    public void createEvent(@RequestBody Event event)
    {
        eventService.createEvent(event);
    }

    @GetMapping("/events/find")
    public List<EventDTO> findEvents(@RequestParam Date date, @RequestParam double latitude,
                                     @RequestParam double longitude,
                                     @RequestParam int page, @RequestParam int pageSize)
    {
        return eventService.findEvents(date,latitude,longitude, page, pageSize);
    }
}
