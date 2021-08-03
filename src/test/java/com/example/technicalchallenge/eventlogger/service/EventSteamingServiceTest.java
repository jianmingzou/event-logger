package com.example.technicalchallenge.eventlogger.service;

import static org.assertj.core.api.Fail.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

import com.example.technicalchallenge.eventlogger.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;


@SpringBootTest
class EventSteamingServiceTest {

	@Autowired
	EventSteamingService eventSteamingService;

	@Autowired
	EventRepository eventRepository;


	@MockBean
	private EventLoggerCommandLineRunner eventLoggerCommandLineRunner;

	@Value("classpath:data/logfile.txt")
	Resource resourceFile;

	@Test
	void processInput() throws Exception {
		eventSteamingService.processInput(resourceFile.getFile());
		var eventScsmbstgra = eventRepository.findById("scsmbstgra");
		eventScsmbstgra.ifPresentOrElse(
			e -> assertThat(e, allOf(
				hasProperty("duration", is(5l)),
				hasProperty("host", is("12345")),
				hasProperty("alert", is(true)),
				hasProperty("type", is("APPLICATION_LOG")))
			),
			() -> fail("scsmbstgra event is in correct")
		);

		var scsmbstgrb = eventRepository.findById("scsmbstgrb");
		scsmbstgrb.ifPresentOrElse(
			e -> assertThat(e, allOf(
				hasProperty("duration", is(3l)),
				hasProperty("alert", is(false)))
			),
			() -> fail("scsmbstgrb event is in correct")
		);

		var scsmbstgrc = eventRepository.findById("scsmbstgrc");
		scsmbstgrc.ifPresentOrElse(
			e -> assertThat(e, allOf(
				hasProperty("duration", is(8l)),
				hasProperty("alert", is(true)))
			),
			() -> fail("scsmbstgrc event is in correct")
		);
	}
}