package com.example.technicalchallenge.eventlogger.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RawEvent {

	private String id;

	private State state;

	private long timestamp;

	private String type;

	private String host;

}
