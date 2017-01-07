package org.awesley.samples;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.statemachine.StateContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.data.ActionRepository;
import org.springframework.statemachine.data.StateRepository;
import org.springframework.statemachine.data.TransitionRepository;
import org.springframework.statemachine.data.jpa.JpaRepositoryAction;
import org.springframework.statemachine.data.jpa.JpaRepositoryState;
import org.springframework.statemachine.data.jpa.JpaRepositoryTransition;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StateMachineRepositoryTests {
	
	@Autowired
	ConfigurableApplicationContext context;
	
	@Autowired
	StateRepository<JpaRepositoryState> stateRepository;

	@Autowired
	TransitionRepository<JpaRepositoryTransition> transitionRepository;
	
	@Autowired
	ActionRepository<JpaRepositoryAction> actionRepository;
	
	@Autowired
	StateMachineFactory<String, String> stateMachineRepositoryFactory;
	
	@Before
	public void setUpBeforeClass() throws Exception {
		Map<String, JpaRepositoryState> statesMap;
		
		statesMap = initializeStates("S1", Arrays.asList("S1", "S2"));
		
		Set<JpaRepositoryAction> actionSet = new HashSet<JpaRepositoryAction>();
		JpaRepositoryAction action = new JpaRepositoryAction();
		action.setName("S1ToS2TransitionAction");
		actionSet.add(action);
		actionRepository.save(actionSet);
		
		actionRepository.save(action);
		
		actionSet.add(action);
		transitionRepository.save(new JpaRepositoryTransition("Proposal", statesMap.get("S1"), statesMap.get("S2"), "E1", actionSet));
	}

	private Map<String, JpaRepositoryState> initializeStates(String initialState, List<String> states) {
		Map<String, JpaRepositoryState> statesMap = new HashMap<String, JpaRepositoryState>();
		states.forEach(name -> statesMap.put(name, new JpaRepositoryState("Proposal", name, name == initialState)));
		stateRepository.save(statesMap.values());
		
		return statesMap;
	}

	@After
	public void tearDownAfterClass() throws Exception {
	}

	@Test
	public void createStateMachineFromRepository() {
		StateMachine<String, String> stateMachine = stateMachineRepositoryFactory.getStateMachine("Proposal");
		assertNotNull(stateMachine);
	}
	
	@Test
	public void persistAndRestoreStateMachineWithContext(){
		StateMachine<String, String> stateMachine = stateMachineRepositoryFactory.getStateMachine();
		stateMachine.getExtendedState().getVariables().put("testClass", this);
	}
	
	@org.springframework.boot.test.context.TestConfiguration
	static class TestConfiguration {
		
		@Bean
		@Primary
		public DataSource dataSource(){
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setUrl("jdbc:h2:tcp://localhost/~/test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
			dataSource.setUsername("sa");
			dataSource.setPassword("");
			dataSource.setDriverClassName("org.h2.Driver");
			return dataSource;
		}
	}
	
	private class ThreadIdAction implements Action<String, String> {

		private long actionThreadId = 0;
		
		@Override
		public void execute(StateContext<String, String> context) {
			actionThreadId = Thread.currentThread().getId();
		}

		public long getActionThreadId() {
			return actionThreadId;
		}
	}
}
