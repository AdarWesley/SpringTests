package org.awesley.samples;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.data.StateRepository;
import org.springframework.statemachine.data.TransitionRepository;
import org.springframework.statemachine.data.jpa.JpaRepositoryState;
import org.springframework.statemachine.data.jpa.JpaRepositoryTransition;

public class StateMachineRepositoryTests {
	@Autowired
	static StateRepository<JpaRepositoryState> stateRepository;

	@Autowired
	static TransitionRepository<JpaRepositoryTransition> transitionRepository;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		stateRepository.save(Arrays.asList("S1", "S2").stream()
				.map(name -> new JpaRepositoryState(name))
				.collect(Collectors.toList()));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
