package org.awesley.samples;

import java.io.IOException;
import java.util.HashMap;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JpaHashMapStringObjectConverterJson implements AttributeConverter<HashMap<String, Object>, String> {

	private final static ObjectMapper objectMapper = new ObjectMapper()
			.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

	@Override
	public String convertToDatabaseColumn(HashMap<String, Object> meta) {
		try {
			return objectMapper.writeValueAsString(meta);
		} catch (JsonProcessingException ex) {
			return null;
			// or throw an error
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readValue(dbData, HashMap.class);
		} catch (IOException ex) {
			// logger.error("Unexpected IOEx decoding json from database: " +
			// dbData);
			return null;
		}
	}
}