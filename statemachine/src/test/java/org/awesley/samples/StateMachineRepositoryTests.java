package org.awesley.samples;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.data.StateRepository;
import org.springframework.statemachine.data.TransitionRepository;
import org.springframework.statemachine.data.jpa.JpaRepositoryAction;
import org.springframework.statemachine.data.jpa.JpaRepositoryState;
import org.springframework.statemachine.data.jpa.JpaRepositoryTransition;

public class StateMachineRepositoryTests {
	@Autowired
	static StateRepository<JpaRepositoryState> stateRepository;

	@Autowired
	static TransitionRepository<JpaRepositoryTransition> transitionRepository;
	
	@Autowired
	static StateMachineFactory<String, String> stateMachineRepositoryFactory;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Map<String, JpaRepositoryState> statesMap;
		
		statesMap = initializeStates("S1", Arrays.asList("S1", "S2"));
		
		Set<JpaRepositoryAction> actionSet = new HashSet<JpaRepositoryAction>();
		JpaRepositoryAction action = new JpaRepositoryAction();
		action.setName("S1ToS2TransitionAction");
		
		actionSet.add(action);
		transitionRepository.save(new JpaRepositoryTransition(statesMap.get("S1"), statesMap.get("S2"), "E1"));
	}

	private static Map<String, JpaRepositoryState> initializeStates(String initialState, List<String> states) {
		Map<String, JpaRepositoryState> statesMap = new HashMap<String, JpaRepositoryState>();
		states.forEach(name -> statesMap.put(name, new JpaRepositoryState(name, name == initialState)));
		stateRepository.save(statesMap.values());
		
		return statesMap;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		StateMachine<String, String> stateMachine = stateMachineRepositoryFactory.getStateMachine();
		
		assertNotNull(stateMachine);
	}

}
