package com.example.technicalchallenge.eventlogger.service;

import com.example.technicalchallenge.eventlogger.model.Event;
import com.example.technicalchallenge.eventlogger.model.RawEvent;
import com.example.technicalchallenge.eventlogger.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventStorageService {

	private final EventRepository eventRepository;

	@Value("${com.example.technicalchallenge.eventlogger.threshold}")
	private int threshold;

	public Event saveEvent(RawEvent event1, RawEvent event2) {
		log.debug("generate new event from raw event {} and {}", event1, event2);
		var duration = Math.abs(event1.getTimestamp() - event2.getTimestamp());
		return eventRepository.save(
			Event.builder().id(event1.getId()).host(event1.getHost()).type(event1.getType())
				.alert(duration > 4).duration(duration).build());
	}

}
