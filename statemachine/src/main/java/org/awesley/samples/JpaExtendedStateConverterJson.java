package org.awesley.samples;

import java.io.IOException;

import javax.persistence.AttributeConverter;

import org.springframework.statemachine.ExtendedState;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JpaExtendedStateConverterJson implements AttributeConverter<ExtendedState, String> {

	private final static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(ExtendedState meta) {
		try {
			return objectMapper.writeValueAsString(meta);
		} catch (JsonProcessingException ex) {
			return null;
			// or throw an error
		}
	}

	@Override
	public ExtendedState convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readValue(dbData, ExtendedState.class);
		} catch (IOException ex) {
			// logger.error("Unexpected IOEx decoding json from database: " +
			// dbData);
			return null;
		}
	}
}