package org.awesley.samples.persistance.jpa;

import java.io.IOException;

import javax.persistence.AttributeConverter;

import org.awesley.samples.persistance.PointJsonAnnotationMixin;
import org.springframework.data.geo.Point;
import org.springframework.statemachine.support.DefaultExtendedState;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;

public class JpaExtendedStateConverterJson implements AttributeConverter<DefaultExtendedState, String> {

	private final static ObjectMapper objectMapper = new ObjectMapper()
			.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)
			.addMixIn(Point.class, PointJsonAnnotationMixin.class);

	@Override
	public String convertToDatabaseColumn(DefaultExtendedState meta) {
		try {
			return objectMapper.writeValueAsString(meta);
		} catch (JsonProcessingException ex) {
			return null;
			// or throw an error
		}
	}

	@Override
	public DefaultExtendedState convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readValue(dbData, DefaultExtendedState.class);
		} catch (IOException ex) {
			// logger.error("Unexpected IOEx decoding json from database: " +
			// dbData);
			return null;
		}
	}
}