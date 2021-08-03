package com.example.technicalchallenge.eventlogger;

import com.example.technicalchallenge.eventlogger.service.EventLoggerCommandLineRunner;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class EventLoggerApplicationTests {

	@MockBean
	private EventLoggerCommandLineRunner eventLoggerCommandLineRunner;

	@Test
	void contextLoads() {

	}


}
