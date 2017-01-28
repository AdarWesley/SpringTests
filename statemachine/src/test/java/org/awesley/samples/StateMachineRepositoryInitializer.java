package org.awesley.samples;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.data.ActionRepository;
import org.springframework.statemachine.data.StateRepository;
import org.springframework.statemachine.data.TransitionRepository;
import org.springframework.statemachine.data.jpa.JpaRepositoryAction;
import org.springframework.statemachine.data.jpa.JpaRepositoryState;
import org.springframework.statemachine.data.jpa.JpaRepositoryTransition;

public class StateMachineRepositoryInitializer {
	
	@Autowired
	StateRepository<JpaRepositoryState> stateRepository;

	@Autowired
	TransitionRepository<JpaRepositoryTransition> transitionRepository;
	
	@Autowired
	ActionRepository<JpaRepositoryAction> actionRepository;
	
	public void initializeProposalStateMachine() {
		Map<String, JpaRepositoryState> statesMap;
		
		statesMap = initializeStates("S1", Arrays.asList("S1", "S2"));
		
		Set<JpaRepositoryAction> actionSet = new HashSet<JpaRepositoryAction>();
		JpaRepositoryAction action1 = new JpaRepositoryAction();
		action1.setName("S1ToS2TransitionAction");
		actionSet.add(action1);
		
		JpaRepositoryAction action2 = new JpaRepositoryAction();
		action2.setName("TestClassInstanceAction");
		actionSet.add(action2);
		
		actionRepository.save(actionSet);
		
		transitionRepository.save(new JpaRepositoryTransition("Proposal", statesMap.get("S1"), statesMap.get("S2"), "E1", actionSet));
	}

	public Map<String, JpaRepositoryState> initializeStates(String initialState, List<String> states) {
		Map<String, JpaRepositoryState> statesMap = new HashMap<String, JpaRepositoryState>();
		states.forEach(name -> statesMap.put(name, new JpaRepositoryState("Proposal", name, name == initialState)));
		stateRepository.save(statesMap.values());
		
		return statesMap;
	}

	public void clearProposalStateMachine() {
		//actionRepository.deleteAll();
		transitionRepository.deleteAll();
		stateRepository.deleteAll();		
	}	
}
