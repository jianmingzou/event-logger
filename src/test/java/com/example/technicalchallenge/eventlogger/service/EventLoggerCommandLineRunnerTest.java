package com.example.technicalchallenge.eventlogger.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(args = "src/test/resources/data/logfile.txt")
class EventLoggerCommandLineRunnerTest {

	@Autowired
	EventLoggerCommandLineRunner eventLoggerCommandLineRunner;

	@MockBean
	EventProcessService eventProcessService;

	@Test
	void testNoArguments() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> eventLoggerCommandLineRunner.run());
		assertTrue(exception.getMessage().contains("usage"));
	}


	@Test
	void testFileDoesNotExist() {
		Exception exception = assertThrows(IllegalAccessException.class, () -> {
			eventLoggerCommandLineRunner.run("src/test/resources/data/doesNotExist.txt");
		});
		assertTrue(exception.getMessage().contains("dose not exist"));
	}

	@Test
	void testFileExist() throws Exception {
		eventLoggerCommandLineRunner.run("src/test/resources/data/logfile.txt");
	}
}