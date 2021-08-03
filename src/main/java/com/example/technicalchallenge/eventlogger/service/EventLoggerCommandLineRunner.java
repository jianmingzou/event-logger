package com.example.technicalchallenge.eventlogger.service;

import java.io.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventLoggerCommandLineRunner implements CommandLineRunner {


	private final EventProcessService eventProcessService;

	@Override
	public void run(String... args) throws Exception {
		if (args.length != 1) {
			log.error("illegal argument");
			throw new IllegalArgumentException(
				"usage: java -jar event-logger-0.0.1-SNAPSHOT.jar input-file");
		}
		File input = new File(args[0]);

		if (!input.exists() || !input.canRead()) {
			log.error("input file dose not exist or cannot read");
			throw new IllegalAccessException(String.format("input file %s dose not exist or cannot read.", input.getAbsolutePath()));
		}
		eventProcessService.processInput(input);
	}
}
