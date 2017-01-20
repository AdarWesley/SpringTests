package org.awesley.samples;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class PointJsonAnnotationMixin {
	public PointJsonAnnotationMixin(@JsonProperty("X") double x, @JsonProperty("Y") double y) { }
	
	@JsonProperty("X") abstract double getX(); // rename property
	@JsonProperty("Y") abstract double getY(); // rename property
}
