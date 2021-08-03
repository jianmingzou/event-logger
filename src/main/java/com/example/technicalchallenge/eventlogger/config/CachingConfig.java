package com.example.technicalchallenge.eventlogger.config;

import static org.ehcache.config.builders.CacheConfigurationBuilder.newCacheConfigurationBuilder;
import static org.ehcache.config.builders.CacheManagerBuilder.newCacheManagerBuilder;

import com.example.technicalchallenge.eventlogger.model.RawEvent;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.core.EhcacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@Slf4j
public class CachingConfig {

	@Value("${com.example.technicalchallenge.eventlogger.cache.name}:events")
	private String cacheName;

	@Bean
	public Cache<String, RawEvent> eventCache(EhcacheManager ehcacheManager) {
		return ehcacheManager.getCache(cacheName, String.class, RawEvent.class);
	}

	@Bean
	public EhcacheManager ehCacheManager() {
		CacheConfiguration<String, RawEvent>  config = newCacheConfigurationBuilder(String.class,
			RawEvent.class, ResourcePoolsBuilder.heap(10000)).build();
		return (EhcacheManager) newCacheManagerBuilder().withCache(cacheName, config).build(true);
	}

}
