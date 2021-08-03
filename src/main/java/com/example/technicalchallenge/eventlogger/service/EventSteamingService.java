package com.example.technicalchallenge.eventlogger.service;


import com.example.technicalchallenge.eventlogger.model.RawEvent;
import com.google.gson.Gson;
import java.io.File;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventSteamingService implements EventProcessService {


	private final Gson gson;

	private final EventStorageService eventStorageService;

	private final Cache<String, RawEvent> cache;

	@Value("${com.example.technicalchallenge.eventlogger.threshold}")
	private int threshold;


	@Override
	public void processInput(File input) throws Exception {

		try (LineIterator it = FileUtils.lineIterator(input, "UTF-8")) {
			while (it.hasNext()) {
				String line = it.nextLine();
				log.debug("process raw event [{}]", line);
				try {
					RawEvent rawEvent = gson.fromJson(line, RawEvent.class);
					var cachedEvent = getPairedEventFromCache(rawEvent);
					cachedEvent.ifPresentOrElse(
						e -> log.info("saved event {}", eventStorageService.saveEvent(e, rawEvent)),
						() -> putIntoCache(rawEvent));
				} catch (Exception e) {
					log.error(
						"An error occurred during parsing the event: {}, continue to process next line",
						line);
				}
			}
		}
	}

	private Optional<RawEvent> getPairedEventFromCache(RawEvent rawEvent) {
		log.debug("getPairedEventFromCache [{}]", rawEvent.getId());
		return Optional.ofNullable(cache.get(rawEvent.getId()));
	}


	private void putIntoCache(RawEvent rawEvent) {
		log.debug("save raw event to cache [{}]", rawEvent.getId());
		cache.put(rawEvent.getId(), rawEvent);
	}

}
