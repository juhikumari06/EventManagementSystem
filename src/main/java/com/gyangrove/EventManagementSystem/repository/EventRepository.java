package com.gyangrove.EventManagementSystem.repository;

import com.gyangrove.EventManagementSystem.entities.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

    List<Event> findByDate(Date date);

    List<Event> findAllByDateBetweenOrderByDateAsc(Date startDate, Date endDate, Pageable pageable);

}
