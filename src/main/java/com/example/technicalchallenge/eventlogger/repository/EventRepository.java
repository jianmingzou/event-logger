package com.example.technicalchallenge.eventlogger.repository;

import com.example.technicalchallenge.eventlogger.model.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, String> {
}
