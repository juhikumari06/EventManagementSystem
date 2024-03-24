package com.gyangrove.EventManagementSystem.service;

import com.gyangrove.EventManagementSystem.entities.Event;
import com.gyangrove.EventManagementSystem.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CSVService {

    @Autowired
    EventRepository repository;

    public void save(MultipartFile file) {
        try {
            //convert cvs records to events
            List<Event> events = csvToEvents(file.getInputStream());

            //save events to database
            repository.saveAll(events);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    /** converts scv inputstream to event objects
     *
     * @param is
     * @return
     */
    public List<Event> csvToEvents(InputStream is)
    {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Event> events = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            //Iterate records and get attributes and prepare event object
            for (CSVRecord csvRecord : csvRecords)
            {
                Event event = new Event(
                        csvRecord.get("event_name"),
                        csvRecord.get("city_name"),
                        Date.valueOf(csvRecord.get("date")),
                        csvRecord.get("time"),
                        Double.parseDouble(csvRecord.get("latitude")),
                        Double.parseDouble(csvRecord.get("longitude"))
                );

                //keep adding each event to list
                events.add(event);
            }
            return events;

        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }


}
